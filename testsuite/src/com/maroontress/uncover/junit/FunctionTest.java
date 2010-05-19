package com.maroontress.uncover.junit;

import com.maroontress.uncover.Function;
import com.maroontress.uncover.FunctionSource;
import org.junit.Before;
import org.junit.Test;
import static com.maroontress.uncover.junit.TestFunctionSource.*;
import static org.junit.Assert.*;

public class FunctionTest {
    private static final double DELTA = 1.0e-10;
    private Function function;

    @Before public void init() {
	function = new Function(new TestFunctionSource());
    }

    @Test public void getKey() {
	assertEquals(NAME + "@" + GCNO_FILE, function.getKey());
    }

    @Test public void getLocation() {
	assertEquals(SOURCE_FILE + ":" + LINE_NUMBER, function.getLocation());
    }

    @Test public void getUnexecutedBlocks() {
	assertEquals(ALL_BLOCKS - EXECUTED_BLOCKS,
		     function.getUnexecutedBlocks());
    }

    @Test public void getBlockRate() {
	assertEquals((float) EXECUTED_BLOCKS / ALL_BLOCKS,
		     function.getBlockRate(), DELTA);
    }

    @Test public void getBlockRate0() {
	Function function0 = new Function(new FunctionSource() {
	    public String getCheckSum() {
		return CHECK_SUM;
	    }
	    public int getComplexity() {
		return COMPLEXITY;
	    }
	    public String getName() {
		return NAME;
	    }
 	    public String getGCNOFile() {
		return GCNO_FILE;
	    }
 	    public String getSourceFile() {
		return SOURCE_FILE;
	    }
 	    public int getLineNumber() {
		return LINE_NUMBER;
	    }
 	    public int getExecutedBlocks() {
		return EXECUTED_BLOCKS;
	    }
 	    public int getAllBlocks() {
		return 0;
	    }
 	    public int getExecutedArcs() {
		return EXECUTED_ARCS;
	    }
 	    public int getAllArcs() {
		return ALL_ARCS;
	    }
	});
	assertEquals(0, function0.getBlockRate(), DELTA);
    }

    @Test public void getCheckSum() {
	assertEquals(CHECK_SUM, function.getCheckSum());
    }

    @Test public void getComplexity() {
	assertEquals(COMPLEXITY, function.getComplexity());
    }

    @Test public void getName() {
	assertEquals(NAME, function.getName());
    }

    @Test public void getGCNOFile() {
	assertEquals(GCNO_FILE, function.getGCNOFile());
    }

    @Test public void getSourceFile() {
	assertEquals(SOURCE_FILE, function.getSourceFile());
    }

    @Test public void getLineNumber() {
	assertEquals(LINE_NUMBER, function.getLineNumber());
    }

    @Test public void getExecutedBlocks() {
	assertEquals(EXECUTED_BLOCKS, function.getExecutedBlocks());
    }

    @Test public void getAllBlocks() {
	assertEquals(ALL_BLOCKS, function.getAllBlocks());
    }

    @Test public void getExecutedArcs() {
	assertEquals(EXECUTED_ARCS, function.getExecutedArcs());
    }

    @Test public void getAllArcs() {
	assertEquals(ALL_ARCS, function.getAllArcs());
    }
}
