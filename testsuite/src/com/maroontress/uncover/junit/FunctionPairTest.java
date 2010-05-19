package com.maroontress.uncover.junit;

import com.maroontress.uncover.Function;
import com.maroontress.uncover.FunctionPair;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FunctionPairTest {
    private Function left;
    private Function right;
    private FunctionPair pair;
    private TestFunctionSource leftSrc;
    private TestFunctionSource rightSrc;

    @Before public void init() {
	leftSrc = new TestFunctionSource();
	left = new Function(leftSrc);

	rightSrc = new TestFunctionSource();
	rightSrc.setCheckSum("0x87654321");
	rightSrc.setComplexity(321);
	rightSrc.setBlocks(7, 11);
	rightSrc.setArcs(12, 15);
	right = new Function(rightSrc);

	pair = new FunctionPair(left, right);
    }

    @Test public void getLeft() {
	assertSame(left, pair.getLeft());
    }

    @Test public void getRight() {
	assertSame(right, pair.getRight());
    }

    @Test public void getComplexityDelta() {
	int delta = rightSrc.getComplexity() - leftSrc.getComplexity();
	assertEquals(delta, pair.getComplexityDelta());
    }

    @Test public void getAllBlocksDelta() {
	int delta = rightSrc.getAllBlocks() - leftSrc.getAllBlocks();
	assertEquals(delta, pair.getAllBlocksDelta());
    }

    @Test public void getBlockRate0() {
	int delta
	    = (rightSrc.getAllBlocks() - rightSrc.getExecutedBlocks())
	    - (leftSrc.getAllBlocks() - leftSrc.getExecutedBlocks());
	assertEquals(delta, pair.getUnexecutedBlocksDelta());
    }
}
