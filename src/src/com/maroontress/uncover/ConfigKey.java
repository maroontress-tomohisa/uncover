package com.maroontress.uncover;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.TreeSet;

/**
   ����Υ����Ǥ���
*/
public final class ConfigKey {
    /** �ǥե���ȤΥǡ����١����ե�����Υ����Ǥ��� */
    public static final String DB_DEFAULT = "db.default";

    /** ���󥹥ȥ饯���ϻ��ѤǤ��ޤ��� */
    private ConfigKey() {
    }

    /**
       ���ꥭ���Υ��åȤ�������ޤ���

       @return ���ꥭ���Υ��å�
    */
    public static Set<String> keySet() {
	try {
	    Set<String> set = new TreeSet<String>();
	    Field[] allFields = ConfigKey.class.getDeclaredFields();
	    for (Field field : allFields) {
		String val = field.getName();
		set.add((String) field.get(null));
	    }
	    return set;
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error.", e);
	}
    }
}
