package com.maroontress.uncover.sqlite;

/**
   ����ơ��֥�ιԤ˴ؤ��������ݻ����ޤ���
*/
public final class ConfigRow extends Row {
    /** �ǡ����١����ΥС������Ǥ��� */
    private String version;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public ConfigRow() {
    }

    /**
       �ե�����ɤ��ͤ����ꤷ�ޤ���

       @param version �ǡ����١����ΥС������
    */
    public void set(final String version) {
	this.version = version;
    }
}
