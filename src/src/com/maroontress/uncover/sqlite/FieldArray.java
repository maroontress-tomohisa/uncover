package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;

/**
   �ե�����ɤ���������뤿��Υ��饹�Ǥ���
*/
public final class FieldArray {
    /** ���饹����SQL����ʸ������Ѵ�����ޥåפǤ��� */
    private static Map<Class<?>, String> typeMap;

    static {
	typeMap = new HashMap<Class<?>, String>();
	typeMap.put(int.class, "INTEGER");
	typeMap.put(long.class, "INTEGER");
	typeMap.put(String.class, "TEXT");
    }

    /**
       ���󥹥ȥ饯���ϻ��ѤǤ��ޤ���
    */
    private FieldArray() {
    }

    /**
       �ե������̾�˶��ڤ�ʸ�����Ϥ����Ϣ�뤷��ʸ�����������ޤ���

       @param allFields �ե�����ɤ�����
       @param separator �ե������̾�ζ��ڤ�ʸ����
       @return �ե������̾����ڤ�ʸ�����Ϥ����Ϣ�뤷��ʸ����
    */
    public static String concatNames(final Field[] allFields,
				     final String separator) {
	String s = "";
	String prefix = "";
	for (Field field : allFields) {
	    s += prefix + field.getName();
	    prefix = separator;
	}
	return s;
    }

    /**
       ���饹�Υե������̾�˶��ڤ�ʸ�����Ϥ����Ϣ�뤷��ʸ������
       �����ޤ���

       @param clazz ���饹
       @param separator �ե������̾�ζ��ڤ�ʸ����
       @return �ե������̾����ڤ�ʸ�����Ϥ����Ϣ�뤷��ʸ����
    */
    public static String concatNames(final Class<? extends Row> clazz,
				     final String separator) {
	return concatNames(clazz.getDeclaredFields(), separator);
    }

    /**
       �ե������̾�˶��ڤ�ʸ�����Ϥ����Ϣ�뤷��ʸ�����������ޤ���

       @param allFields �ե�����ɤ�����
       @param separator �ե������̾�ζ��ڤ�ʸ����
       @return �ե������̾����ڤ�ʸ�����Ϥ����Ϣ�뤷��ʸ����
    */
    public static String concatNameTypes(final Field[] allFields,
					 final String separator) {
	String s = "";
	String prefix = "";
	for (Field field : allFields) {
	    String sqlType = typeMap.get(field.getType());
	    if (sqlType == null) {
		throw new RuntimeException("internal error: unexpected type.");
	    }
	    s += prefix + field.getName() + " " + sqlType;
	    prefix = separator;
	}
	return s;
    }

    /**
       ���饹�Υե������̾�ȷ�̾������Ϣ�뤷����Τ򡢤���˶��ڤ�
       ʸ�����Ϥ����Ϣ�뤷��ʸ�����������ޤ���

       @param clazz ���饹
       @param separator �ե������̾�ζ��ڤ�ʸ����
       @return �ե������̾�ȷ�̾������Ϣ�뤷����Τ���ڤ�ʸ�����
       �Ϥ����Ϣ�뤷��ʸ����
    */
    public static String concatNameTypes(final Class<? extends Row> clazz,
					 final String separator) {
	return concatNameTypes(clazz.getDeclaredFields(), separator);
    }
}
