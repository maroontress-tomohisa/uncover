package com.maroontress.uncover.junit;

import com.maroontress.uncover.Function;
import com.maroontress.uncover.FunctionPair;
import com.maroontress.uncover.Revision;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RevisionTest {
    private static final String GCNO_FILE = "gcnoFile.gcno";
    private Function foo;
    private Function baz;
    private Function bar;
    private Revision oldRev;
    private Revision newRev;

    @Before public void init() {
	foo = new Function(new TestFunctionSource("foo", GCNO_FILE));
	bar = new Function(new TestFunctionSource("bar", GCNO_FILE));
	baz = new Function(new TestFunctionSource("baz", GCNO_FILE));

	List<Function> oldList = new ArrayList<Function>();
	oldList.add(foo);
	oldList.add(bar);
	oldRev = new Revision(oldList);

	List<Function> newList = new ArrayList<Function>();
	newList.add(foo);
	newList.add(baz);
	newRev = new Revision(newList);
    }

    @Test public void getSize() {
	assertEquals(2, oldRev.getSize());
    }

    @Test public void createFunctionArray() {
	Function[] funcs = oldRev.createFunctionArray();
	assertEquals(2, funcs.length);
	assertSame(foo, funcs[0]);
	assertSame(bar, funcs[1]);
    }

    @Test public void createOuterFunctions() {

	List<Function> list = oldRev.createOuterFunctions(newRev);
	assertEquals(1, list.size());
	assertSame(bar, list.get(0));
    }

    @Test public void createInnerFunctions() {
	List<FunctionPair> list = oldRev.createInnerFunctionPairs(newRev);
	assertEquals(1, list.size());
	assertEquals(foo.getKey(), list.get(0).getLeft().getKey());
    }

    @Test public void getFunction() {
	Function func = oldRev.getFunction("foo@" + GCNO_FILE);
	assertSame(foo, func);
    }

    @Test public void getFunctionNotFound() {
	Function func = oldRev.getFunction("baz@" + GCNO_FILE);
	assertNull(func);
    }

    @Test public void getComplexityRank() {
	int rank = oldRev.getComplexityRank("foo@" + GCNO_FILE);
	assertEquals(1, rank);
    }

    @Test public void getComplexityRankNotFound() {
	int rank = oldRev.getComplexityRank("baz@" + GCNO_FILE);
	assertEquals(0, rank);
    }
}
