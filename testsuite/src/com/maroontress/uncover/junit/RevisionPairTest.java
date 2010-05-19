package com.maroontress.uncover.junit;

import com.maroontress.uncover.Function;
import com.maroontress.uncover.Revision;
import com.maroontress.uncover.RevisionPair;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RevisionPairTest {
    private static final String GCNO_FILE = "gcnoFile.gcno";
    private List<Function> oldList = new ArrayList<Function>();
    private List<Function> newList = new ArrayList<Function>();

    @Before public void init() {
    }

    private void run() throws Exception {
	Revision oldRev = new Revision(oldList);
	Revision newRev = new Revision(newList);
	RevisionPair pair = new RevisionPair(oldRev, newRev);
	ByteArrayOutputStream buf = new ByteArrayOutputStream();
	PrintStream out = new PrintStream(buf);
	pair.printHTMLReport(out);
	out.close();
	byte[] result = buf.toByteArray();
    }

    @Test public void fromZero() throws Exception {
	Function foo = new Function(new TestFunctionSource("foo", GCNO_FILE));
	newList.add(foo);
	run();
    }

    @Test public void noChange() throws Exception {
	Function foo = new Function(new TestFunctionSource("foo", GCNO_FILE));
	oldList.add(foo);
	newList.add(foo);
	run();
    }

    @Test public void exchange() throws Exception {
	Function foo = new Function(new TestFunctionSource("foo", GCNO_FILE));
	Function bar = new Function(new TestFunctionSource("bar", GCNO_FILE));
	oldList.add(foo);
	newList.add(bar);
	run();
    }

    @Test public void plus() throws Exception {
	Function foo = new Function(new TestFunctionSource("foo", GCNO_FILE));
	Function bar = new Function(new TestFunctionSource("bar", GCNO_FILE));
	oldList.add(foo);
	newList.add(foo);
	newList.add(bar);
	run();
    }

    @Test public void minus() throws Exception {
	Function foo = new Function(new TestFunctionSource("foo", GCNO_FILE));
	Function bar = new Function(new TestFunctionSource("bar", GCNO_FILE));
	oldList.add(foo);
	oldList.add(bar);
	newList.add(foo);
	run();
    }

    @Test public void allBlocksChanges() throws Exception {
	TestFunctionSource src = new TestFunctionSource("foo", GCNO_FILE);
	src.setBlocks(1, 10);
	Function foo1 = new Function(src);
	src.setBlocks(1, 20);
	Function foo2 = new Function(src);

	oldList.add(foo1);
	newList.add(foo2);
	run();
    }

    @Test public void complexityUpChanges() throws Exception {
	TestFunctionSource src = new TestFunctionSource("foo", GCNO_FILE);
	src.setComplexity(10);
	Function foo1 = new Function(src);
	src.setComplexity(20);
	Function foo2 = new Function(src);

	oldList.add(foo1);
	newList.add(foo2);
	run();
    }

    @Test public void complexityDownChanges() throws Exception {
	TestFunctionSource src = new TestFunctionSource("foo", GCNO_FILE);
	src.setComplexity(10);
	Function foo1 = new Function(src);
	src.setComplexity(5);
	Function foo2 = new Function(src);

	oldList.add(foo1);
	newList.add(foo2);
	run();
    }

    @Test public void rankMapExhanges() throws Exception {
	TestFunctionSource fooSrc = new TestFunctionSource("foo", GCNO_FILE);
	TestFunctionSource barSrc = new TestFunctionSource("bar", GCNO_FILE);
	fooSrc.setComplexity(10);
	barSrc.setComplexity(20);
	oldList.add(new Function(fooSrc));
	oldList.add(new Function(barSrc));

	fooSrc.setComplexity(20);
	barSrc.setComplexity(10);
	newList.add(new Function(fooSrc));
	newList.add(new Function(barSrc));
	run();
    }
}
