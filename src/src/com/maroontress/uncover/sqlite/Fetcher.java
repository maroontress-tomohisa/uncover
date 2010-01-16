package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   �ơ��֥뤫��Ԥ�������뵡ǽ���󶡤��ޤ���

   @param <T> �ơ��֥�ιԤ�ɽ�����饹
*/
public final class Fetcher<T extends Row> extends Querier<T> {
    /**
       ���󥹥ȥ饯���Ǥ���

       @param ps ����ѥ���Ѥߥ��ơ��ȥ���
    */
    public Fetcher(final PreparedStatement ps) {
	super(ps);
    }

    /**
       �Ԥ�ơ��֥뤫��������ޤ���

       @return java.sql.PreparedStatement#executeQuery()�������
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public ResultSet executeQuery() throws SQLException {
	PreparedStatement ps = getStatement();
	T instance = getRow();
	Field[] allField = instance.getClass().getDeclaredFields();
	int offset = 1;
	try {
	    for (Field field : allField) {
		field.setAccessible(true);
		String name = field.getName();
		Class<?> clazz = field.getType();
		if (clazz == int.class) {
		    ps.setInt(offset, field.getInt(instance));
		} else {
		    ps.setString(offset, (String) field.get(instance));
		}
		++offset;
	    }
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error", e);
	}
	return ps.executeQuery();
    }
}
