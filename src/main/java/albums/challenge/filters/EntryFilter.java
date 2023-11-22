package albums.challenge.filters;

import albums.challenge.models.Entry;
import albums.challenge.models.Facet;

public interface EntryFilter<T extends EntryFilter<T>> {
    String value();

    int count();

    boolean matches(Entry entry);

    T withCount(int count);

    default Facet toFacet() {
        return new Facet(value(), count());
    }

}
