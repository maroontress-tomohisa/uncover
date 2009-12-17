package com.maroontress.uncover;

import com.maroontress.uncover.sqlite.SQLiteDB;

/**
   デフォルトのツールキットです。
*/
public class DefaultToolkit extends Toolkit {
    /**
       {@inheritDoc}
    */
    public final DB createDB(final String subname) throws DBException {
	return new SQLiteDB(subname);
    }
}
