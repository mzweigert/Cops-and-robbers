package com.graphrodite.internal.enums;


import com.graphrodite.internal.wrapper.ProductConditionWrapper;

public enum GraphProduct {

    CARTESIAN {
        @Override
        public <A, B> boolean getCondition(ProductConditionWrapper<A, B> condition) {
            return (condition.isFirstGraphHasEdgeWithFirstIndexes() && condition.areSecondIndexesAreEquals() ||
                    (condition.isSecondGraphHasEdgeWithSecondIndexes() && condition.areFirstIndexesAreEquals()));
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
                    || (condition.areFirstIndexesAreEquals() && condition.isSecondGraphHasEdgeWithSecondIndexes());
        }
    };

    public abstract <A, B> boolean getCondition(ProductConditionWrapper<A, B> condition);

}
