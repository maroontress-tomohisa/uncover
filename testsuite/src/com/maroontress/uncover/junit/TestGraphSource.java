package com.maroontress.uncover.junit;

import com.maroontress.uncover.Arc;
import com.maroontress.uncover.Block;
import com.maroontress.uncover.GraphSource;

public class TestGraphSource implements GraphSource {
    public static final String NAME = "foo";
    public static final String GCNO_FILE = "foo.gcno";

    private Block[] blocks;
    private Arc[] arcs;

    public TestGraphSource(final Block[] blocks, final Arc[] arcs) {
	this.blocks = blocks;
	this.arcs = arcs;
    }

    public String getName() {
	return NAME;
    }
    public String getGCNOFile() {
	return GCNO_FILE;
    }
    public Block[] getAllBlocks() {
	return blocks;
    }
    public Arc[] getAllArcs() {
	return arcs;
    }
}
