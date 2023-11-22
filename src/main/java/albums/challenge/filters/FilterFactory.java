package albums.challenge.filters;

import albums.challenge.models.Entry;

import java.util.List;

public interface FilterFactory<T> {
    T fromValue(String value);

    default List<T> fromValues(List<String> values) {
        return values.stream().map(this::fromValue).toList();
    }

    T fromEntry(Entry entry);

}
