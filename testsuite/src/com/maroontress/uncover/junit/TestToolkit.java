package com.maroontress.uncover.junit;

import com.maroontress.uncover.Arc;
import com.maroontress.uncover.ArcSource;
import com.maroontress.uncover.Block;
import com.maroontress.uncover.BlockSource;
import com.maroontress.uncover.Build;
import com.maroontress.uncover.BuildSource;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.DBException;
import com.maroontress.uncover.Function;
import com.maroontress.uncover.FunctionSource;
import com.maroontress.uncover.Graph;
import com.maroontress.uncover.GraphSource;
import com.maroontress.uncover.Parser;
import com.maroontress.uncover.ParsingException;
import com.maroontress.uncover.Toolkit;

public class TestToolkit extends Toolkit {
    public void exit(final int exitStatus) {
    }
    public Parser createParser(final String file) throws ParsingException {
	return new TestParser(file);
    }
    public DB createDB(final String subname) throws DBException {
	return new TestDB();
    }
    public Function createFunction(final FunctionSource source) {
	return null;
    }
    public Build createBuild(final BuildSource source) {
	return null;
    }
    public Block createBlock(final BlockSource source) {
	return null;
    }
    public Arc createArc(final ArcSource source) {
	return null;
    }
    public Graph createGraph(final GraphSource source) {
	return null;
    }
}
