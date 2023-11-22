package albums.challenge.filters.price;

import albums.challenge.filters.FilterFactory;
import albums.challenge.models.Entry;

public class PriceRangeFilterFactory implements FilterFactory<PriceRangeFilter> {

    private static final String RANGE_SEPARATOR = " - ";
    private static final int RANGE_INTERVAL = 5;

    @Override
    public PriceRangeFilter fromValue(String value) {
        var rangeString = value.split(RANGE_SEPARATOR);
        if (rangeString.length != 2) {
            throw new IllegalArgumentException("Incorrect range format");
        }
        return new PriceRangeFilter(Integer.parseInt(rangeString[0]), Integer.parseInt(rangeString[1]), value, 0);
    }

    @Override
    public PriceRangeFilter fromEntry(Entry entry) {
        var to = (int) Math.ceil(entry.price() / RANGE_INTERVAL) * RANGE_INTERVAL;
        var from = to - RANGE_INTERVAL;
        var rangeAsText = from + RANGE_SEPARATOR + to;
        return new PriceRangeFilter(from, to, rangeAsText, 0);
    }
}
