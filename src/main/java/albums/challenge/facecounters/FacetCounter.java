package albums.challenge.facecounters;

import albums.challenge.filters.EntryFilter;
import albums.challenge.filters.FilterFactory;
import albums.challenge.models.Entry;
import albums.challenge.models.Facet;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Counts facets based on provided filter implementation
 *
 * @param <T> type of Entry filter
 */
public class FacetCounter<T extends EntryFilter<T>> {
    private final Map<String, T> byValue;
    private final FilterFactory<T> filterFactory;
    private final Comparator<T> sortFilters;

    private final Predicate<Entry> filterPredicate;

    /**
     *
     * @param initialValues appends initial filters to counter. In case no items are found, but we still want to return facet as some-value(0)
     * @param filterFactory class that is used to take entry and build Filter out of it
     * @param sortFilters how to sort filters when returning count
     * @param filterPredicate only items that match this predicate are counted as +1, else it is not added to list
     */
    public FacetCounter(List<T> initialValues, FilterFactory<T> filterFactory, Comparator<T> sortFilters, Predicate<Entry> filterPredicate) {
        this.filterFactory = filterFactory;
        this.sortFilters = sortFilters;
        this.filterPredicate = filterPredicate;
        this.byValue = new HashMap<>();
        initialValues.forEach(filter -> byValue.put(filter.value(), filter));
    }

    public void visit(Entry entry) {
        if (!filterPredicate.test(entry)) {
            return;
        }
        var filter = filterFactory.fromEntry(entry);
        var value = filter.value();
        if (byValue.containsKey(value)) {
            var existingFilter = byValue.get(value);
            byValue.put(value, existingFilter.withCount(existingFilter.count() + 1));
        } else {
            byValue.put(value, filter.withCount(1));
        }

    }

    public List<Facet> getFacets() {
        return byValue.values()
                .stream()
                .sorted(this.sortFilters)
                .map(EntryFilter::toFacet)
                .toList();
    }
}

