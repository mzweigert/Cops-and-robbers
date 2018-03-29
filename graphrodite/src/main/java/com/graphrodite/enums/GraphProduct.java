package com.graphrodite.enums;


import com.graphrodite.shared.GraphProductCondition;

public enum GraphProduct {

    CARTESIAN {
        @Override
        public <A, B> boolean getCondition(GraphProductCondition<A, B> condition) {

            return (condition.isFirstGraphHasEdgeWithFirstIndexes() && condition.isSecondIndexesAreEquals() ||
                    (condition.isSecondGraphHasEdgeWithSecondIndexes() && condition.isFirstIndexesAreEquals()));
        }
    },
    CATEGORICAL {
        @Override
        public <A, B> boolean getCondition(GraphProductCondition<A, B> condition) {
            return condition.isFirstGraphHasEdgeWithFirstIndexes() && condition.isSecondGraphHasEdgeWithSecondIndexes();
        }
    },
    STRONG {
        @Override
        public <A, B> boolean getCondition(GraphProductCondition<A, B> condition) {
            return CARTESIAN.getCondition(condition) || CATEGORICAL.getCondition(condition);
        }
    },
    LEXICOGRAPHICAL {
        @Override
        public <A, B> boolean getCondition(GraphProductCondition<A, B> condition) {
            return condition.isFirstGraphHasEdgeWithFirstIndexes()
                    || ( condition.isFirstIndexesAreEquals() && condition.isSecondGraphHasEdgeWithSecondIndexes());
        }
    };

    public abstract <A, B> boolean getCondition(GraphProductCondition<A, B> condition);

}
