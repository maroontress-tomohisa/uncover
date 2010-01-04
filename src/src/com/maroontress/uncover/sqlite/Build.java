package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
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
	Field[] allField = this.getClass().getDeclaredFields();
	try {
	    for (Field field : allField) {
		String name = field.getName();
		field.set(this, row.getString(name));
	    }
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error", e);
	}
    }

    /**
       �ӥ��ID��������ޤ���

       @return ID
    */
    public String getID() {
	return id;
    }
}
