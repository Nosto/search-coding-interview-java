package albums.challenge.filters.price;

import albums.challenge.filters.EntryFilter;
import albums.challenge.models.Entry;

public record PriceRangeFilter(int from, int to, String value, int count) implements EntryFilter<PriceRangeFilter> {

    @Override
    public boolean matches(Entry entry) {
        return entry.price() > from && entry.price() <= to;
    }

    @Override
    public PriceRangeFilter withCount(int count) {
        return new PriceRangeFilter(from, to, value, count);
    }
}
