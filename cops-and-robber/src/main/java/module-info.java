module copsandrobber {

    requires com.graphrodite;

    exports com.copsandrobber.algorithm.one_cop_enough;
    exports com.copsandrobber.algorithm.one_cop_enough.strategy;

    exports com.copsandrobber.algorithm.two_cops_enough;

    exports com.copsandrobber.algorithm.k_cops_enough;
    exports com.copsandrobber.algorithm.k_cops_enough.strategy;
    exports com.copsandrobber.algorithm.k_cops_enough.helper;

}