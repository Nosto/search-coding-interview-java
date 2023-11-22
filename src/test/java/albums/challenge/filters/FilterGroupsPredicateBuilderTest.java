package albums.challenge.filters;

import albums.challenge.models.Entry;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FilterGroupsPredicateBuilderTest {
    private static final Entry ENTRY = new Entry("", 1f, "", "", "");

    @Test
    void allGroupsMatch_returnsFalseIfAtLeastOneGroupIsFalse() {
        var builder = new FilterGroupsPredicateBuilder(
                Map.of(
                        "groupA", List.of(new TestFilter(true)),
                        "groupB", List.of(new TestFilter(false)
                        )
                )
        );

        assertThat(builder.allGroupsMatch().test(ENTRY)).isFalse();
    }

    @Test
    void allGroupsMatch_returnsTrueWhenAllGroupsAreTrue() {
        var builder = new FilterGroupsPredicateBuilder(
                Map.of(
                        "groupA", List.of(new TestFilter(true)),
                        "groupB", List.of(new TestFilter(true)
                        )
                )
        );

        assertThat(builder.allGroupsMatch().test(ENTRY)).isTrue();
    }

    @Test
    void allGroupsMatch_onlyOneItemFromGroupShouldMatch() {
        var builder = new FilterGroupsPredicateBuilder(
                Map.of(
                        "groupA", List.of(new TestFilter(true), new TestFilter(false)),
                        "groupB", List.of(new TestFilter(true)
                        )
                )
        );

        assertThat(builder.allGroupsMatch().test(ENTRY)).isTrue();
    }

    @Test
    void allGroupsMatch_allowsToSkipProvidedGroup() {
        var builder = new FilterGroupsPredicateBuilder(
                Map.of(
                        "groupA", List.of(new TestFilter(true)),
                        "groupB", List.of(new TestFilter(false)),
                        "groupC", List.of(new TestFilter(true)
                        )
                )
        );

        assertThat(builder.allGroupsExceptMatches("groupB").test(ENTRY)).isTrue();
    }

    @Test
    void allGroupsMatch_stillReturnsFalseIfAtLestOneNonSkippedGroupIsFalse() {
        var builder = new FilterGroupsPredicateBuilder(
                Map.of(
                        "groupA", List.of(new TestFilter(true)),
                        "groupB", List.of(new TestFilter(false)),
                        "groupC", List.of(new TestFilter(false)
                        )
                )
        );

        assertThat(builder.allGroupsExceptMatches("groupB").test(ENTRY)).isFalse();
    }

}