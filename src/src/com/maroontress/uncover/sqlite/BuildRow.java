package com.maroontress.uncover.sqlite;

/**
   �ӥ�ɥơ��֥�ιԤ˴ؤ��������ݻ����ޤ���
*/
public final class BuildRow extends Row {
    /** ��ӥ����Ǥ��� */
    private String revision;

    /** �����ॹ����פǤ��� */
    private String timestamp;

    /** �ץ�åȥե�����Ǥ��� */
    private String platform;

    /** �ץ�������ID�Ǥ��� */
    private long projectID;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public BuildRow() {
    }

    /**
       �ե�����ɤ��ͤ����ꤷ�ޤ���

       @param revision ��ӥ����
       @param timestamp �����ॹ�����
       @param platform �ץ�åȥե�����
       @param projectID �ץ�������ID
    */
    public void set(final String revision, final String timestamp,
		    final String platform, final long projectID) {
	this.revision = revision;
	this.timestamp = timestamp;
	this.platform = platform;
	this.projectID = projectID;
    }
}
