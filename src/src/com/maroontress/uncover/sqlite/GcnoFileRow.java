package com.maroontress.uncover.sqlite;

/**
   gcno�ե�����ơ��֥�ιԤ˴ؤ��������ݻ����ޤ���
*/
public final class GcnoFileRow extends Row {
    /** gcno�ե�����Υѥ��Ǥ��� */
    private String gcnoFile;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public GcnoFileRow() {
    }

    /**
       �ե�����ɤ��ͤ����ꤷ�ޤ���

       @param gcnoFile gcno�ե�����Υѥ�
    */
    public void set(final String gcnoFile) {
	this.gcnoFile = gcnoFile;
    }
}
