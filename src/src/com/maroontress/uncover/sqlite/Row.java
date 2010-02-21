package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   �ơ��֥�ȥ������̤ιԤ˴ؤ��������ݻ����ޤ���
*/
public abstract class Row {
    /**
       ���󥹥��󥹤��������ޤ���
    */
    protected Row() {
    }

    /**
       �������̤����ꤷ�ޤ���

       @param row �ؿ��ι�
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    public final void setResultSet(final ResultSet row) throws SQLException {
	Field[] allFields = this.getClass().getDeclaredFields();
	try {
	    for (Field field : allFields) {
		field.setAccessible(true);

		String name = field.getName();
		Class<?> clazz = field.getType();
		if (clazz == int.class) {
		    field.setInt(this, row.getInt(name));
		} else {
		    field.set(this, row.getString(name));
		}
	    }
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error", e);
	}
    }
}
