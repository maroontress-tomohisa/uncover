package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.BuildSource;

/**
   �������̤���ӥ�ɤ��������뤿��Υӥ�ɥ������Ǥ���
*/
public final class ResultSetBuildSource extends Row implements BuildSource {
    /** �ӥ��ID�Ǥ��� */
    private long id;

    /** ��ӥ����Ǥ��� */
    private String revision;

    /** �����ॹ����פǤ��� */
    private String timestamp;

    /** �ץ�åȥե�����Ǥ��� */
    private String platform;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public ResultSetBuildSource() {
    }

    /** {@inheritDoc} */
    public String getID() {
	return Long.toString(id);
    }

    /** {@inheritDoc} */
    public String getRevision() {
	return revision;
    }

    /** {@inheritDoc} */
    public String getTimestamp() {
	return timestamp;
    }

    /** {@inheritDoc} */
    public String getPlatform() {
	return platform;
    }
}
