package albums.challenge;

import static albums.challenge.aggregations.Aggregator.getPriceAggregations;
import static albums.challenge.aggregations.Aggregator.getYearAggregations;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import albums.challenge.filters.CompositeFilter;
import albums.challenge.filters.PriceFilter;
import albums.challenge.filters.YearFilter;
import albums.challenge.models.Entry;
import albums.challenge.models.Results;

@Service
public class SearchService {

    Results search(List<Entry> entries, String query) {
        return search(entries, query, List.of(), List.of());
    }

    Results search(List<Entry> entries, String query, List<String> year, List<String> price) {
        var filteredEntries = query.isBlank() ? entries : filterByQuery(entries, query);
        var resultList = new CompositeFilter(
                List.of(new YearFilter(year), new PriceFilter(price)))
                .filter(filteredEntries);

        return new Results(
                resultList,
                Map.ofEntries(
                        Map.entry("year", getYearAggregations(new PriceFilter(price).filter(filteredEntries))),
                        Map.entry("price", getPriceAggregations(new YearFilter(year).filter(filteredEntries)))
                ),
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
}
