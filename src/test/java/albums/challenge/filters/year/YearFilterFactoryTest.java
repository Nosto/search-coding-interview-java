package albums.challenge.filters.year;

import albums.challenge.models.Entry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class YearFilterFactoryTest {
    YearFilterFactory factory = new YearFilterFactory();

    @Test
    void fromValue_parsesFromValueCorrectly() {
        assertThat(factory.fromValue("2001").year()).isEqualTo(2001);
    }

    @Test
    void fromEntry_parsesDate() {
        YearFilter filter = factory.fromEntry(new Entry("", 5f, "2023-01-29T00:00:00-07:00", "", ""));
        assertThat(filter.year()).isEqualTo(2023);
    }

    @Test
    void fromEntry_throwsExceptionWhenCannotParseDate() {
        assertThatThrownBy(() -> factory.fromEntry(new Entry("", 5f, "2023:22", "", "")));
    }
}