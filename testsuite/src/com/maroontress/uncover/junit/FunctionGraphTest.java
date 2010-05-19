package com.maroontress.uncover.junit;

import com.maroontress.uncover.Arc;
import com.maroontress.uncover.Block;
import com.maroontress.uncover.Function;
import com.maroontress.uncover.FunctionGraph;
import com.maroontress.uncover.Graph;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FunctionGraphTest {
    private Function function;
    private Graph graph;
    private FunctionGraph fg;

    @Before public void init() {
	function = new Function(new TestFunctionSource());
	Block[] blocks = new Block[] {new Block(new TestBlockSource())};
	Arc[] arcs = new Arc[] {new Arc(new TestArcSource())};
	graph = new Graph(new TestGraphSource(blocks, arcs));
	fg = new FunctionGraph(function, graph);
    }

    @Test public void getFunction() {
	assertSame(function, fg.getFunction());
    }

    @Test public void getGraph() {
	assertSame(graph, fg.getGraph());
    }
}
