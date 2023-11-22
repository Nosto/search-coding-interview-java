package albums.challenge.filters;

import java.util.ArrayList;
import java.util.List;

import albums.challenge.models.Entry;

public class PriceFilter implements EntryFilter {

    private final List<String> prices;

    public PriceFilter(final List<String> prices) {
        this.prices = prices != null ? prices : List.of();
    }

    @Override
    public List<Entry> filter(final List<Entry> entries) {
        if (prices.isEmpty()) {
            return entries;
        }
        var filteredEntries = new ArrayList<Entry>();
        for (String price : prices) {
            var split = price.split(" - ");
            var min = Double.parseDouble(split[0]);
            var max = Double.parseDouble(split[1]);
            filteredEntries.addAll(entries.stream().filter(entry -> {
                var entryPrice = entry.price();
                return entryPrice >= min && entryPrice < max;
            }).toList());
        }
        return filteredEntries;
    }
}
