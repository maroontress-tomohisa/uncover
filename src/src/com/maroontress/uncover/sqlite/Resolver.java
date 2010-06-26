package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   �ơ��֥�ιԤ�ID��������뤿�����ݥ��饹�Ǥ���

   @param <T> �ơ��֥�ιԤȤʤ륯�饹
*/
public abstract class Resolver<T extends Row> {
    /** �ؿ��ơ��֥�ιԤ��������ե��å���Ǥ��� */
    private Fetcher<T> fetcher;

    /**
       ���󥹥��󥹤��������ޤ���

       @param con JDBC��³
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public Resolver(final Connection con) throws SQLException {
	T row = createRow();
	fetcher = new QuerierFactory<T>(con, getTableName(),
					getRowClass()).createFetcher("id");
	fetcher.setRow(row);
    }

    /**
       �ơ��֥�ιԤΥ��饹��������ޤ���

       @return �ơ��֥�ιԤΥ��饹��
    */
    protected abstract Class<T> getRowClass();

    /**
       �ơ��֥�ιԤΥ��󥹥��󥹤��������ޤ���

       @return �ơ��֥�ι�
    */
    protected abstract T createRow();

    /**
       �ơ��֥��̾����������ޤ���

       @return �ơ��֥�̾
    */
    protected abstract String getTableName();

    /**
       �ơ��֥�ιԤΥ��󥹥��󥹤�������ޤ���

       @return �ơ��֥�ι�
    */
    public final T getRow() {
	return fetcher.getRow();
    }

    /**
       �Ԥ�ID��������ޤ���

       @return �Ԥ�ID
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    protected final long getID() throws SQLException {
	ResultSet rs = fetcher.executeQuery();
	long id = -1;
	int k;
	for (k = 0; rs.next(); ++k) {
	    id = rs.getLong("id");
	}
	if (k > 1) {
	    String s = "too many rows found: " + dumpRow();
	    throw new TableInconsistencyException(s);
	}
	return id;
    }

    /**
       �Ԥ����Ƥ�ɽ��ʸ�����������ޤ���

       @return �Ԥγƥե�����ɤ�̾������
    */
    private String dumpRow() {
	StringBuilder b = new StringBuilder();
	String prefix = "";
	Object instance = getRow();
	Field[] allFields = instance.getClass().getDeclaredFields();
	try {
	    b.append("(");
	    for (Field field : allFields) {
		field.setAccessible(true);
		String name = field.getName();
		Object value = field.get(instance);
		b.append(prefix);
		b.append(name);
		b.append("=");
		b.append(value.toString());
		prefix = ", ";
	    }
	    b.append(")");
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error.", e);
	}
	return b.toString();
    }
}
