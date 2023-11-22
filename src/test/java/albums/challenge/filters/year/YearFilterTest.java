package albums.challenge.filters.year;

import albums.challenge.models.Entry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class YearFilterTest {

    @Test
    void matches_returnsTrueIfYearIsSame() {
        assertThat(new YearFilter("2023", 2023, 0).matches(
                new Entry("", 5f, "2023-01-29T00:00:00-07:00", "", "")
        )).isTrue();
    }

    @Test
    void matches_returnsTrueIfYearIsDifferent() {
        assertThat(new YearFilter("2024", 2024, 0).matches(
                new Entry("", 5f, "2023-01-29T00:00:00-07:00", "", "")
        )).isFalse();
    }

}