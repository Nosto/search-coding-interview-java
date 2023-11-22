package albums.challenge.filters;

import java.util.List;

import albums.challenge.models.Entry;

public interface EntryFilter {

    List<Entry> filter(List<Entry> entries);
}
