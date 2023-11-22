package albums.challenge.filters.year;

import albums.challenge.filters.FilterFactory;
import albums.challenge.models.Entry;

import java.time.ZonedDateTime;

public class YearFilterFactory implements FilterFactory<YearFilter> {
    @Override
    public YearFilter fromValue(String value) {
        return new YearFilter(
                value, Integer.parseInt(value), 0
        );
    }

    @Override
    public YearFilter fromEntry(Entry entry) {
        // we should not do it here, entry should have date type
        int year = ZonedDateTime.parse(entry.release_date()).getYear();
        var value = String.valueOf(year);
        return new YearFilter(value, year, 0);
    }
}
