package com.maroontress.cui;

/**
   ���ޥ�ɥ饤�󥪥ץ����򸡽Ф����Ȥ��˸ƤӽФ��ꥹ�ʤǤ���
*/
public interface OptionListener {

    /**
       ���ץ����򸡽Ф����Ȥ��˸ƤӽФ���ޤ���

       �����ʤ��Υ��ץ����ξ�硢arg��null�ˤʤ�ޤ���

       @param name ���ץ�����̾��
       @param arg ���ץ����ΰ������ޤ���null
       @throws OptionsParsingException ���ץ������ͤ�����
    */
    void run(final String name,
	     final String arg) throws OptionsParsingException;
}
