package albums.challenge.facecounters;

import albums.challenge.filters.price.PriceRangeFilter;
import albums.challenge.filters.price.PriceRangeFilterFactory;
import albums.challenge.models.Entry;
import albums.challenge.models.Facet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ByPriceFacetCounterTest {
    FacetCounter<PriceRangeFilter> facetCounter;

    @BeforeEach
    void setUp() {
        facetCounter = new FacetCounter<>(List.of(), new PriceRangeFilterFactory(), Comparator.comparing(PriceRangeFilter::from), (e) -> true);
    }

    @Test
    void returnsMultipleRanges() {
        var entries = List.of(
                elementWithPrice(9.99f),
                elementWithPrice(9.99f),
                elementWithPrice(1.99f),
                elementWithPrice(14.99f)
        );

        entries.forEach(facetCounter::visit);

        assertThat(facetCounter.getFacets()).containsExactly(
                new Facet("0 - 5", 1),
                new Facet("5 - 10", 2),
                new Facet("10 - 15", 1)
        );
    }

    private static Entry elementWithPrice(float price) {
        return new Entry(
                "Legend: The Best of Bob Marley and the Wailers (Remastered)",
                price,
                "2002-01-01T00:00:00-07:00",
                "",
                ""
        );
    }
}