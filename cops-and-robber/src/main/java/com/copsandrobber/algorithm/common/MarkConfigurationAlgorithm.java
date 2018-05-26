package com.copsandrobber.algorithm.common;

import java.util.Iterator;

public abstract class MarkConfigurationAlgorithm {

    public static <CONFIGURATION_TYPE> boolean calculate(ConfigurationsWrapper<CONFIGURATION_TYPE, ?> configurations) {

        int prevUnmarkedCount;

        do {
            prevUnmarkedCount = configurations.getUnmarkedSize();
            Iterator<CONFIGURATION_TYPE> unmarkedIterator = configurations.getUnmarkedIterator();

            while (unmarkedIterator.hasNext()) {

                CONFIGURATION_TYPE configuration = unmarkedIterator.next();

                if (configurations.canMarkConfiguration(configuration)) {
                    unmarkedIterator.remove();
                    configurations.addToMarked(configuration);
                }
            }
        } while (prevUnmarkedCount > configurations.getUnmarkedSize() && configurations.isUnmarkedNotEmpty());

        return configurations.isUnmarkedEmpty();
    }

}
