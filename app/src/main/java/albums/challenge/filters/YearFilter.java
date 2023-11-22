package albums.challenge.filters;

import java.util.ArrayList;
import java.util.List;

import albums.challenge.models.Entry;

public class YearFilter implements EntryFilter {

    private final List<String> years;

    public YearFilter(final List<String> years) {
        this.years = years != null ? years : List.of();
    }

    @Override
    public List<Entry> filter(final List<Entry> entries) {
        if (years.isEmpty()) {
            return entries;
        }
        var filteredEntries = new ArrayList<Entry>();
        for (String year : years) {
            filteredEntries.addAll(entries.stream().filter(entry -> entry.release_date().contains(year)).toList());
        }
        return filteredEntries;
    }
}
