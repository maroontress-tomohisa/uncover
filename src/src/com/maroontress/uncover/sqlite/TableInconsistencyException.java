package com.maroontress.uncover.sqlite;

import java.sql.SQLException;

/**
   �ơ��֥뤬���곰�ξ��֤Ǥ��뤳�Ȥ�ɽ���㳰�Ǥ���
*/
public class TableInconsistencyException extends SQLException {
    /**
       �㳰���������ޤ���

       @param s ��å�����
    */
    public TableInconsistencyException(final String s) {
        super(s);
    }

    /**
       �㳰���������ޤ���

       @param s ��å�����
       @param t ����
    */
    public TableInconsistencyException(final String s, final Throwable t) {
	super(s, t);
    }
}
