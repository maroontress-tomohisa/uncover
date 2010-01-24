package com.maroontress.uncover;

import com.maroontress.uncover.sqlite.SQLiteDB;

/**
   デフォルトのツールキットです。
*/
public final class DefaultToolkit extends Toolkit {
    /**
       {@inheritDoc}
    */
    public DB createDB(final String subname) throws DBException {
	return new SQLiteDB(subname);
    }

    /**
       {@inheritDoc}
    */
    public Function createFunction(final FunctionSource source) {
	return new Function(source);
    }

    /**
       {@inheritDoc}
    */
    public Build createBuild(final BuildSource source) {
	return new Build(source);
    }
}
