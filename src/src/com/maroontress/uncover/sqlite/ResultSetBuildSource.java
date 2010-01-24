package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.BuildSource;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   �������̤���ӥ�ɤ��������뤿��Υӥ�ɥ������Ǥ���
*/
public final class ResultSetBuildSource implements BuildSource {
    /** �ӥ��ID�Ǥ��� */
    private String id;

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

    /**
       �������̤����ꤷ�ޤ���

       @param row �ӥ�ɤι�
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    public void setResultSet(final ResultSet row) throws SQLException {
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

    /** {@inheritDoc} */
    public String getID() {
	return id;
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
