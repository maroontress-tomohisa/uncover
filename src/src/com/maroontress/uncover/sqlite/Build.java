package com.maroontress.uncover.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
   �ӥ�ɤ˴ؤ��������ݻ����ޤ���
*/
public final class Build {
    /** �ӥ��ID�Ǥ��� */
    private String id;

    /** ��ӥ����Ǥ��� */
    private String revision;

    /** �����ॹ����פǤ��� */
    private String timestamp;

    /** �ץ�åȥե�����Ǥ��� */
    private String platform;

    /** �ץ�������ID�Ǥ��� */
    private String projectID;

    /**
       ���󥹥��󥹤��������ޤ���

       @param row build�ơ��֥�ι�
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    public Build(final ResultSet row) throws SQLException {
	// ��ե쥯�����ǽ񤭤���
	id = row.getString("id");
	revision = row.getString("revision");
	timestamp = row.getString("timestamp");
	platform = row.getString("platform");
	projectID = row.getString("projectID");
    }

    /**
       �ӥ��ID��������ޤ���

       @return ID
    */
    public String getID() {
	return id;
    }
}
