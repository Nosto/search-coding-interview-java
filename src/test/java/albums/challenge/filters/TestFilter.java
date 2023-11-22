package albums.challenge.filters;

import albums.challenge.models.Entry;

public record TestFilter(boolean matches) implements EntryFilter<TestFilter> {

        @Override
        public String value() {
            return "";
        }
    
        @Override
        public int count() {
            return 0;
        }

        @Override
        public boolean matches(Entry entry) {
            return matches;
        }

        @Override
        public TestFilter withCount(int count) {
            return this;
        }
    }