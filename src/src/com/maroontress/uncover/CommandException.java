package com.maroontress.uncover;

/**
   ���ޥ�ɤμ¹Ԥ˴ؤ����㳰�Ǥ���
*/
public class CommandException extends Exception {
    /**
       �㳰���������ޤ���

       @param s ��å�����
       @param t ����
    */
    public CommandException(final String s, final Throwable t) {
	super(s, t);
    }
}
