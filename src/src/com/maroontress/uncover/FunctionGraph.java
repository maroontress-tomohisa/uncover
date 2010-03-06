package com.maroontress.uncover;

/**
*/
public final class FunctionGraph {
    /** */
    private Function function;

    /** */
    private Graph graph;

    /**
       @param function
       @param graph
    */
    public FunctionGraph(final Function function, final Graph graph) {
	this.function = function;
	this.graph = graph;
    }

    /**
    */
    public Function getFunction() {
	return function;
    }

    /**
    */
    public Graph getGraph() {
	return graph;
    }
}
