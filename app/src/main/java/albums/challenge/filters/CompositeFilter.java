package albums.challenge.filters;

import java.util.List;

import albums.challenge.models.Entry;

public class CompositeFilter implements EntryFilter {

    private final List<EntryFilter> allFilters;

    public CompositeFilter(final List<EntryFilter> allFilters) {
        this.allFilters = allFilters;
    }

    @Override
    public List<Entry> filter(List<Entry> entries) {
        List<Entry> filteredEntries = entries;
        for (EntryFilter filter : allFilters) {
            filteredEntries = filter.filter(filteredEntries);
        }
        return filteredEntries;
    }
}
