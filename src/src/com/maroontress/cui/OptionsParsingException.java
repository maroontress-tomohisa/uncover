package com.maroontress.cui;

/**
   ���ޥ�ɥ饤�󥪥ץ����Υѡ����˴ؤ����㳰�Ǥ���
*/
public class OptionsParsingException extends Exception {

    /**
       ���ޥ�ɥ饤�󥪥ץ����Υѡ����˴ؤ����㳰���������ޤ���
    */
    public OptionsParsingException() {
	super();
    }

    /**
       ���ޥ�ɥ饤�󥪥ץ����Υѡ����˴ؤ����㳰���������ޤ���

       @param m �ܺ٥�å�����
    */
    public OptionsParsingException(final String m) {
	super(m);
    }
}
