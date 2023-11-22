package albums.challenge.models;

import java.util.Objects;

public record Facet(
        String value,
        Integer count

) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facet facet = (Facet) o;
        return Objects.equals(value, facet.value) && Objects.equals(count, facet.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, count);
    }
}
