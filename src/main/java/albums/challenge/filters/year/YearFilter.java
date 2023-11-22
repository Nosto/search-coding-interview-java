package albums.challenge.filters.year;

import albums.challenge.filters.EntryFilter;
import albums.challenge.models.Entry;

public record YearFilter(String value, int year, int count) implements EntryFilter<YearFilter> {

    public boolean matches(Entry entry) {
        // ideally we would get proper type of release date, not string but LocalDateTime
        return entry.release_date().startsWith(String.valueOf(year));
    }

    @Override
    public YearFilter withCount(int count) {
        return new YearFilter(this.value, this.year, count);
    }

}
