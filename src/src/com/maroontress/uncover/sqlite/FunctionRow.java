package com.maroontress.uncover.sqlite;

/**
   �ؿ��ơ��֥�ιԤ˴ؤ��������ݻ����ޤ���
*/
public final class FunctionRow extends Row {
    /** �ؿ�̾�Ǥ��� */
    private String name;

    /** gcno�ե�����Υե�����̾�Ǥ��� */
    private String gcnoFile;

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
       @param gcnoFile gcno�ե�����Υե�����̾
       @param projectID �ץ�������ID
    */
    public void set(final String name, final String gcnoFile,
		    final long projectID) {
	this.name = name;
	this.gcnoFile = gcnoFile;
	this.projectID = projectID;
    }
}
