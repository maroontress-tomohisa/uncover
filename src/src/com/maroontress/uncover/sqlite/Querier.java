package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
   �ơ��֥�˹�ñ�̤����뵡ǽ���󶡤��ޤ���

   @param <T> �ơ��֥�ιԤ�ɽ�����饹
*/
public abstract class Querier<T extends Row> {
    /** ����ѥ���Ѥߥ��ơ��ȥ��ȤǤ��� */
    private PreparedStatement ps;

    /** �ԤΥ��󥹥��󥹤Ǥ��� */
    private T instance;

    /** �ԤΥ��󥹥��󥹤Υե�����ɤǤ��� */
    private Field[] allFields;

    /**
       ���󥹥ȥ饯���Ǥ���

       @param ps ����ѥ���Ѥߥ��ơ��ȥ���
    */
    protected Querier(final PreparedStatement ps) {
	this.ps = ps;
    }

    /**
       �Ԥ�ɽ�����󥹥��󥹤����ꤷ�ޤ���

       @param instance �Ԥ�ɽ�����󥹥���
    */
    public final void setRow(final T instance) {
	this.instance = instance;
	allFields = instance.getClass().getDeclaredFields();
	for (Field field : allFields) {
	    field.setAccessible(true);
	}
    }

    /**
       �Ԥ�ɽ�����󥹥��󥹤�������ޤ���

       @return �Ԥ�ɽ�����󥹥���
    */
    public final T getRow() {
	return instance;
    }

    /**
       ����ѥ���Ѥߥ��ơ��ȥ��Ȥ�������ޤ���

       @return ����ѥ���Ѥߥ��ơ��ȥ���
    */
    protected final PreparedStatement getStatement() {
	return ps;
    }

    /**
       ����ѥ���Ѥߥ��ơ��ȥ��Ȥˡ��Ԥ˴ޤޤ���ͤ�ѥ�᡼���Ȥ�
       �����ꤷ�ޤ���

       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    protected final void setParameters() throws SQLException {
	int offset = 1;
	try {
	    for (Field field : allFields) {
		Class<?> clazz = field.getType();
		if (clazz == int.class) {
		    ps.setInt(offset, field.getInt(instance));
		} else if (clazz == long.class) {
		    ps.setLong(offset, field.getLong(instance));
		} else if (clazz == String.class) {
		    ps.setString(offset, (String) field.get(instance));
		} else {
		    throw new RuntimeException(
			"internal error: unexpected type.");
		}
		++offset;
	    }
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error", e);
	}
    }
}
