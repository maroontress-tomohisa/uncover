package com.maroontress.uncover.sqlite;

/**
   �ؿ��ơ��֥�ιԤ˴ؤ��������ݻ����ޤ���
*/
public final class FunctionRow extends Row {
    /** �ؿ�̾�Ǥ��� */
    private String name;

    /** gcno�ե������ID�Ǥ��� */
    private long gcnoFileID;

    /** �ץ�������ID�Ǥ��� */
    private long projectID;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public FunctionRow() {
    }

    /**
       �ե�����ɤ��ͤ����ꤷ�ޤ���

       @param name �ؿ�̾
       @param gcnoFileID gcno�ե������ID
       @param projectID �ץ�������ID
    */
    public void set(final String name, final long gcnoFileID,
		    final long projectID) {
	this.name = name;
	this.gcnoFileID = gcnoFileID;
	this.projectID = projectID;
    }
}
