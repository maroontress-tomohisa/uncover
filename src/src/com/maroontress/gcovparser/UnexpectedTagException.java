package com.maroontress.gcovparser;

/**
   ͽ�����ʤ������������������Ȥ򼨤��㳰�Ǥ���
*/
public final class UnexpectedTagException extends CorruptedFileException {

    /**
       ͽ�����ʤ������������������Ȥ򼨤��㳰���������ޤ���

       @param m �ܺ٥�å�����
    */
    public UnexpectedTagException(final String m) {
	super(m);
    }
}
