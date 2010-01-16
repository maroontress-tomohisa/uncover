package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;

/**
   �ե�����ɤ���������뤿��Υ��饹�Ǥ���
*/
public final class FieldArray {
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
    public static String concatNames(final Class clazz,
				     final String separator) {
	return concatNames(clazz.getDeclaredFields(), separator);
    }
}
