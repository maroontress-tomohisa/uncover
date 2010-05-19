package com.maroontress.uncover.junit;

import com.maroontress.uncover.FunctionGraph;
import com.maroontress.uncover.Parser;
import com.maroontress.uncover.ParsingException;
import java.util.Iterator;

public class TestParser implements Parser {
    public TestParser(final String file) throws ParsingException {
	if (file.equals("notfound")) {
	    throw new ParsingException("not found: " + file);
	}
    }

    public Iterator<FunctionGraph> iterator() {
	return null;
    }
}
