package albums.challenge.filters.price;

import albums.challenge.models.Entry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceRangeFilterTest {

    @Test
    void matches_doesNotIncludeFromRange() {
        var filter1 = new PriceRangeFilter(0, 5, "0-5", 0);
        var filter2 = new PriceRangeFilter(5, 10, "5-10", 0);
        var entry = new Entry("", 5f, "", "", "");
        assertThat(filter1.matches(entry)).isTrue();
        assertThat(filter2.matches(entry)).isFalse();
    }


}