package com.graphrodite.internal.enums;


import com.graphrodite.internal.wrapper.ProductConditionWrapper;

public enum GraphProduct {

    CARTESIAN {
        @Override
        public <A, B> boolean getCondition(ProductConditionWrapper<A, B> condition) {

            return (condition.isFirstGraphHasEdgeWithFirstIndexes() && condition.isSecondIndexesAreEquals() ||
                    (condition.isSecondGraphHasEdgeWithSecondIndexes() && condition.isFirstIndexesAreEquals()));
        }
    },
    CATEGORICAL {
        @Override
        public <A, B> boolean getCondition(ProductConditionWrapper<A, B> condition) {
            return condition.isFirstGraphHasEdgeWithFirstIndexes() && condition.isSecondGraphHasEdgeWithSecondIndexes();
        }
    },
    STRONG {
        @Override
        public <A, B> boolean getCondition(ProductConditionWrapper<A, B> condition) {
            return CARTESIAN.getCondition(condition) || CATEGORICAL.getCondition(condition);
        }
    },
    LEXICOGRAPHICAL {
        @Override
        public <A, B> boolean getCondition(ProductConditionWrapper<A, B> condition) {
            return condition.isFirstGraphHasEdgeWithFirstIndexes()
                    || ( condition.isFirstIndexesAreEquals() && condition.isSecondGraphHasEdgeWithSecondIndexes());
        }
    };

    public abstract <A, B> boolean getCondition(ProductConditionWrapper<A, B> condition);

}
