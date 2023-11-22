package albums.challenge.facecounters;

import albums.challenge.models.Entry;
import albums.challenge.models.Facet;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
Takes multiple facets counters and calculates each repetition by group
 */
public class FacetCounterByGroup {

    private final Map<String, FacetCounter<?>> counterGroups;

    public FacetCounterByGroup(Map<String, FacetCounter<?>> counterGroups) {
        this.counterGroups = counterGroups;
    }

    public void visit(Entry entry) {
        this.counterGroups.values().forEach(c -> c.visit(entry));
    }

    // Note, ideally I would just keep `EntryFilter` and count them instead of converting to Facet
    // they quite interchangeable, but in order to keep general structure the same, I have left Facet almost unchanged
    // Facet as it is could act as UI/JSON layer model and not business layer class
    public Map<String, List<Facet>> getFacets() {
        return counterGroups.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().getFacets()
        ));
    }
}
