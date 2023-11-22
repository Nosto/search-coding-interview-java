package albums.challenge.aggregations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import albums.challenge.models.Entry;
import albums.challenge.models.Facet;

public class Aggregator {

    private final static int PRICE_STEP = 5;

    public static List<Facet> getPriceAggregations(final List<Entry> entries) {
        var prices = entries.stream().map(Entry::price).sorted().toList();
        var max = prices.get(prices.size() - 1);
        var facets = new ArrayList<Facet>();
        for (int i = 0; i < max; i += PRICE_STEP) {
            final long startPrice = i;
            var count = prices.stream().filter(price -> price >= startPrice && price < startPrice + PRICE_STEP).count();
            if (count > 0) {
                facets.add(new Facet(i + " - " + (i + PRICE_STEP), (int) count));
            }
        }
        return facets;
    }

    public static List<Facet> getYearAggregations(final List<Entry> entries) {
        var years = entries.stream()
                           .map((entry -> entry.release_date().split("-")[0]))
                           .toList();

        return years.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> new Facet(String.valueOf(entry.getKey()), entry.getValue().intValue()))
                    .sorted(Comparator.comparing(Facet::value).reversed())
                    .toList();
    }

}
