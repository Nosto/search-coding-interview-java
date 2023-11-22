package albums.challenge.facecounters;

import albums.challenge.filters.year.YearFilter;
import albums.challenge.filters.year.YearFilterFactory;
import albums.challenge.models.Entry;
import albums.challenge.models.Facet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ByYearFacetCounterTest {
    FacetCounter<YearFilter> facetCounter;

    @BeforeEach
    void setUp() {
        facetCounter = new FacetCounter<>(List.of(), new YearFilterFactory(), Comparator.comparing(YearFilter::year).reversed(), (e) -> true);
    }

    @Test
    void getYears_countsFrequency() {
        var entries = List.of(new Entry(
                        "Legend: The Best of Bob Marley and the Wailers (Remastered)",
                        9.99f,
                        "2002-01-01T00:00:00-07:00",
                        "",
                        ""
                ),
                new Entry(
                        "Legend: The Best of Bob Marley and the Wailers (Remastered)",
                        9.99f,
                        "2002-01-01T00:00:00-07:00",
                        "",
                        ""
                ));

        entries.forEach(facetCounter::visit);

        assertThat(facetCounter.getFacets()).contains(
                new Facet("2002", 2)
        );
    }

    @Test
    void getYears_ordersByYear() {
        var entries = List.of(new Entry(
                        "Legend: The Best of Bob Marley and the Wailers (Remastered)",
                        9.99f,
                        "2014-01-01T00:00:00-07:00",
                        "",
                        ""
                ),
                new Entry(
                        "Legend: The Best of Bob Marley and the Wailers (Remastered)",
                        9.99f,
                        "2002-01-01T00:00:00-07:00",
                        "",
                        ""
                ));

        entries.forEach(facetCounter::visit);

        assertThat(facetCounter.getFacets()).containsExactly(
                new Facet("2014", 1),
                new Facet("2002", 1)
        );
    }

}