package com.maroontress.uncover.junit;

import com.maroontress.uncover.Arc;
import com.maroontress.uncover.Block;
import com.maroontress.uncover.Graph;
import org.junit.Before;
import org.junit.Test;
import static com.maroontress.uncover.junit.TestGraphSource.*;
import static org.junit.Assert.*;

public class GraphTest {
    private Arc[] arcs;
    private Block[] blocks;
    private Graph g;

    @Before public void init() {
	blocks = new Block[1];
	blocks[0] = new Block(new TestBlockSource());
	arcs = new Arc[1];
	arcs[0] = new Arc(new TestArcSource());
	g = new Graph(new TestGraphSource(blocks, arcs));
    }

    @Test public void getName() {
	assertEquals(NAME, g.getName());
    }

    @Test public void getGCNOFile() {
	assertEquals(GCNO_FILE, g.getGCNOFile());
    }

    @Test public void getAllBlocks() {
	Block[] b = g.getAllBlocks();
	assertNotSame(blocks, b);
	assertEquals(blocks.length, b.length);
	assertEquals(blocks[0].getNumber(), b[0].getNumber());
	assertEquals(blocks[0].getCount(), b[0].getCount());
	assertSame(blocks[0].getSourceFile(), b[0].getSourceFile());
	assertEquals(blocks[0].getLineNumber(), b[0].getLineNumber());
    }

    @Test public void getAllArcs() {
	Arc[] a = g.getAllArcs();
	assertNotSame(arcs, a);
	assertEquals(arcs.length, a.length);
	assertEquals(arcs[0].getStart(), a[0].getStart());
	assertEquals(arcs[0].getEnd(), a[0].getEnd());
	assertSame(arcs[0].getCount(), a[0].getCount());
	assertEquals(arcs[0].isFake(), a[0].isFake());
    }
}
