package com.maroontress.coverture;

/**
   �ե����뤬����Ƥ��뤳�Ȥ򼨤��㳰�Ǥ���
*/
public class CorruptedFileException extends Exception {

    /**
       �ե����뤬����Ƥ��뤳�Ȥ򼨤��㳰���������ޤ���
    */
    public CorruptedFileException() {
	super();
    }

    /**
       �ե����뤬����Ƥ��뤳�Ȥ򼨤��㳰���������ޤ���

       @param m �ܺ٥�å�����
    */
    public CorruptedFileException(final String m) {
	super(m);
    }
}
