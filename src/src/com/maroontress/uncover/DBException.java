package com.maroontress.uncover;

/**
   �ǡ����١��������˴ؤ����㳰�Ǥ���
*/
public class DBException extends Exception {
    /**
       �㳰���������ޤ���

       @param s ��å�����
    */
    public DBException(final String s) {
        super(s);
    }

    /**
       �㳰���������ޤ���

       @param s ��å�����
       @param t ����
    */
    public DBException(final String s, final Throwable t) {
	super(s, t);
    }
}
