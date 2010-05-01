package com.maroontress.uncover;

import com.maroontress.uncover.coverture.CovertureParser;
import com.maroontress.uncover.sqlite.SQLiteDB;

/**
   デフォルトのツールキットです。
*/
public final class DefaultToolkit extends Toolkit {
    /**
       インスタンスを生成します。
    */
    public DefaultToolkit() {
    }

    /** {@inheritDoc} */
    public void exit(final int status) {
	System.exit(status);
    }

    /** {@inheritDoc} */
    public Parser createParser(final String file) throws ParsingException {
	return new CovertureParser(file);
    }

    /** {@inheritDoc} */
    public DB createDB(final String subname) throws DBException {
	return new SQLiteDB(subname);
    }

    /** {@inheritDoc} */
    public Function createFunction(final FunctionSource source) {
	return new Function(source);
    }

    /** {@inheritDoc} */
    public Graph createGraph(final GraphSource source) {
	return new Graph(source);
    }

    /** {@inheritDoc} */
    public Block createBlock(final BlockSource source) {
	return new Block(source);
    }

    /** {@inheritDoc} */
    public Arc createArc(final ArcSource source) {
	return new Arc(source);
    }

    /** {@inheritDoc} */
    public Build createBuild(final BuildSource source) {
	return new Build(source);
    }
}
