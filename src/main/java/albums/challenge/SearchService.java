package albums.challenge;

import albums.challenge.facecounters.FacetCounter;
import albums.challenge.facecounters.FacetCounterByGroup;
import albums.challenge.filters.FilterGroupsPredicateBuilder;
import albums.challenge.filters.price.PriceRangeFilter;
import albums.challenge.filters.price.PriceRangeFilterFactory;
import albums.challenge.filters.year.YearFilter;
import albums.challenge.filters.year.YearFilterFactory;
import albums.challenge.models.Entry;
import albums.challenge.models.Results;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SearchService {
    public static final String YEAR_FILTER_GROUP = "year";
    public static final String PRICE_FILTER_GROUP = "price";
    private final YearFilterFactory yearFilterFactory;
    private final PriceRangeFilterFactory priceFilterFactory;

    public SearchService() {
        this.yearFilterFactory = new YearFilterFactory();
        this.priceFilterFactory = new PriceRangeFilterFactory();
    }

    Results search(List<Entry> entries, String query) {
        return search(entries, query, List.of(), List.of());
    }

    Results search(List<Entry> entries, String query, List<String> year, List<String> price) {
        List<Entry> filteredItems = query.isBlank() ? entries : filterByQuery(entries, query);

        // converting string representation of filters to actual filter instances then could be applied to entities
        // ideally we should not get string representations in this service there should be another class that is responsible
        // to take UI/API layer request and convert it to actual filter instances
        var yearFilters = yearFilterFactory.fromValues(year);
        var priceRangeFilters = priceFilterFactory.fromValues(price);

        // this handles first part of the challenge,
        // we need to take existing filter definitions filter out entries
        var predicateBuilder = new FilterGroupsPredicateBuilder(
                Map.of(PRICE_FILTER_GROUP, priceRangeFilters, YEAR_FILTER_GROUP, yearFilters)
        );

        // seconds part of challenge is to take all entries and convert them to facets
        // this counter goes through each entry and groups accordingly
        var facetCounter = initFacetCounters(predicateBuilder, yearFilters, priceRangeFilters);

        filteredItems = filteredItems.stream()
                .peek(facetCounter::visit)
                .filter(predicateBuilder.allGroupsMatch())
                .toList();

        // We should also not return filteredItems directly, as this is coupled to
        // iTunes internal structure. If it changes, we can break UI
        return new Results(
                filteredItems,
                facetCounter.getFacets(),
                query
        );
    }

    Set<String> tokenizeToWords(String query) {
        return Set.copyOf(List.of(query.toLowerCase().split("\\W+")));
    }

    List<Entry> filterByQuery(List<Entry> entries, String query) {
        var words = tokenizeToWords(query);
        return entries.stream().filter(entry -> {
            var tokens = tokenizeToWords(entry.title());
            return tokens.containsAll(words);
        }).toList();
    }

    private FacetCounterByGroup initFacetCounters(FilterGroupsPredicateBuilder predicateBulder, List<YearFilter> yearFilters, List<PriceRangeFilter> priceRangeFilters) {
        return new FacetCounterByGroup(
                Map.of(
                        PRICE_FILTER_GROUP,
                        new FacetCounter<>(
                                priceRangeFilters,
                                priceFilterFactory,
                                Comparator.comparing(PriceRangeFilter::from),
                                predicateBulder.allGroupsExceptMatches(PRICE_FILTER_GROUP)),
                        YEAR_FILTER_GROUP,
                        new FacetCounter<>(
                                yearFilters,
                                yearFilterFactory,
                                Comparator.comparing(YearFilter::year).reversed(),
                                predicateBulder.allGroupsExceptMatches(YEAR_FILTER_GROUP)
                        )
                ));
    }
}
