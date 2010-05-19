package com.maroontress.uncover.junit;

import com.maroontress.uncover.Function;
import com.maroontress.uncover.FunctionPair;
import com.maroontress.uncover.FunctionPairPrinter;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class FunctionPairPrinterTest {
    private FunctionPairPrinter printer;

    private Function createFunction(final int complexity) {
        return new Function(new TestFunctionSource() {
            @Override public int getComplexity() {
                return complexity;
            }
        });
    }

    @Test public void printChangesNone() {
	FunctionPairPrinter nonePrinter = new FunctionPairPrinter(
	    new ArrayList<FunctionPair>(),
	    "complexity changes",
	    FunctionPair.COMPLEXITY_DELTA_COMPARATOR,
	    "<tr><th>complexity</th></tr>") {
	    public String getRow(final FunctionPair pair) {
		return null;
	    }
	    public boolean isFiltered(final FunctionPair pair) {
		return false;
	    }
	};

	String s = ""
	    + "<h3>complexity changes</h3>\n"
	    + "<p>None</p>\n";
	ByteArrayOutputStream pool = new ByteArrayOutputStream();
	PrintStream out = new PrintStream(pool);
	nonePrinter.printChanges(out);
	out.close();
	assertEquals(s, pool.toString());
    }

    @Test public void printChanges() {
	List<FunctionPair> list = new ArrayList<FunctionPair>();
        Function func1old = createFunction(1);
        Function func1new = createFunction(2);
        Function func2old = createFunction(34);
        Function func2new = createFunction(56);
        Function func3old = createFunction(100);
        Function func3new = createFunction(100);
	list.add(new FunctionPair(func1old, func1new));
	list.add(new FunctionPair(func2old, func2new));
	list.add(new FunctionPair(func3old, func3new));
	printer = new FunctionPairPrinter(
	    list,
	    "complexity changes",
	    FunctionPair.COMPLEXITY_DELTA_COMPARATOR,
	    "<tr><th>complexity</th></tr>") {
	    public String getRow(final FunctionPair pair) {
		return String.format("<tr><td>%d -> %d (%d)</td></tr>",
				     pair.getLeft().getComplexity(),
				     pair.getRight().getComplexity(),
				     pair.getComplexityDelta());
	    }
	    public boolean isFiltered(final FunctionPair pair) {
		return (pair.getComplexityDelta() == 0);
	    }
	};

	String s = ""
	    + "<h3>complexity changes</h3>\n"
	    + "<p>2/3 function(s) found.</p>\n"
	    + "<table border=\"1\">\n"
	    + "<tbody>\n"
	    + "<tr><th>complexity</th></tr>\n"
	    + "<tr><td>34 -> 56 (22)</td></tr>\n"
	    + "<tr><td>1 -> 2 (1)</td></tr>\n"
	    + "</tbody>\n"
	    + "</table>\n";
	ByteArrayOutputStream pool = new ByteArrayOutputStream();
	PrintStream out = new PrintStream(pool);
	printer.printChanges(out);
	out.close();
	assertEquals(s, pool.toString());
    }
}
