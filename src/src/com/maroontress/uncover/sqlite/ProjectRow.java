package com.maroontress.uncover.sqlite;

/**
   �ץ������ȥơ��֥�ιԤ˴ؤ��������ݻ����ޤ���
*/
public final class ProjectRow extends Row {
    /** �ץ�������̾�Ǥ��� */
    private String name;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public ProjectRow() {
    }

    /**
       �ե�����ɤ��ͤ����ꤷ�ޤ���

       @param name �ץ�������̾
    */
    public void set(final String name) {
	this.name = name;
    }
}
