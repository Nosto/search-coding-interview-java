package albums.challenge.filters.price;

import albums.challenge.models.Entry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceRangeFilterFactoryTest {

    PriceRangeFilterFactory factory = new PriceRangeFilterFactory();

    @Test
    void fromEntry_valueEqualToIntervalGoesToLowerRange() {
        var filter = factory.fromEntry(new Entry("", 5f, "", "", ""));
        assertThat(filter.value()).isEqualTo("0 - 5");
    }

    @Test
    void fromEntry_largeRange() {
        var filter = factory.fromEntry(new Entry("", 51f, "", "", ""));
        assertThat(filter.value()).isEqualTo("50 - 55");
    }

    @Test
    void fromValue_parsesRangeFromText() {
        var filter = factory.fromValue("0 - 5");
        assertThat(filter.value()).isEqualTo("0 - 5");
        assertThat(filter.from()).isEqualTo(0);
        assertThat(filter.to()).isEqualTo(5);

    }

    @Test
    void fromValue_throwsWhenCannotParse() {
       assertThatThrownBy(() -> factory.fromValue("2 -")).isInstanceOf(IllegalArgumentException.class);
    }
}