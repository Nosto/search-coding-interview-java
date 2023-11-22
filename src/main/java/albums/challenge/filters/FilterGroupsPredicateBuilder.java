package albums.challenge.filters;

import albums.challenge.models.Entry;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * <p>
 * Utility class that allows grouping filters by groups, where all groups have to match,
 * but inside the group at least one filter is enough to match. It matches like this
 * </p>
 * <p>
 * {
 *     GROUP1 AND GROUP2 AND GROUP3[filter1 OR filter2 OR filter3]
 * }
 * </p>
 */
public class FilterGroupsPredicateBuilder {
    private final Map<String, List<? extends EntryFilter<?>>> orGroups;

    public FilterGroupsPredicateBuilder(Map<String, List<? extends EntryFilter<?>>> orGroups) {
        this.orGroups = orGroups;
    }

    public Predicate<Entry> allGroupsExceptMatches(String groupToSkip) {
        return entry -> orGroups.entrySet()
                .stream().filter(f -> !f.getKey().equals(groupToSkip))
                .allMatch(filter -> anyMatch(filter.getValue(), entry));
    }

    public Predicate<Entry> allGroupsMatch() {
        return entry -> orGroups.values().stream().allMatch(filter -> anyMatch(filter, entry));
    }

    private static boolean anyMatch(List<? extends EntryFilter<?>> filters, Entry entry) {
        if (filters.isEmpty()) {
            return true;
        }
        return filters.stream().anyMatch(f -> f.matches(entry));
    }
}
