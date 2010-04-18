package com.maroontress.uncover.gxx;

/**
   ����ƥ����������㳰�Ǥ���
*/
public class ContextException extends RuntimeException {
    /**
       �㳰���������ޤ���

       @param s ��å�����
    */
    public ContextException(final String s) {
        super(s);
    }

    /**
       �㳰���������ޤ���

       @param s ��å�����
       @param t ����
    */
    public ContextException(final String s, final Throwable t) {
	super(s, t);
    }

    /**
       �㳰���������ޤ���

       @param c ����ƥ�����
    */
    public ContextException(final Context c) {
	super("can't demangle: " + c.toString());
    }

    /**
       �㳰���������ޤ���

       @param c ����ƥ�����
       @param s ��å�����
    */
    public ContextException(final Context c, final String s) {
	super("can't demangle: " + c.toString() + ":" + s);
    }
}
