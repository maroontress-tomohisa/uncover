package com.maroontress.uncover.coverture;

/**
   Coverture����������XML�Υѡ����˴ؤ����㳰�Ǥ���
*/
public class ParsingException extends Exception {
    /**
       �㳰���������ޤ���

       @param s ��å�����
    */
    public ParsingException(final String s) {
        super(s);
    }

    /**
       �㳰���������ޤ���

       @param s ��å�����
       @param t ����
    */
    public ParsingException(final String s, final Throwable t) {
	super(s, t);
    }
}
