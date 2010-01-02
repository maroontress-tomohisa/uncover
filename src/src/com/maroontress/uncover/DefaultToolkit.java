package com.maroontress.uncover;

import com.maroontress.uncover.sqlite.SQLiteDB;

/**
   �ǥե���ȤΥġ��륭�åȤǤ���
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
}
