package com.maroontress.uncover;

import com.maroontress.uncover.sqlite.SQLiteDB;

/**
   �ǥե���ȤΥġ��륭�åȤǤ���
*/
public class DefaultToolkit extends Toolkit {
    /**
       {@inheritDoc}
    */
    public final DB createDB(final String subname) throws DBException {
	return new SQLiteDB(subname);
    }
}
