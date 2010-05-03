package com.maroontress.uncover;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
   ���ޥ�ɤΥХ󥯤Ǥ���
*/
public final class CommandBank {
    /** ���ޥ��̾�ȥ��ޥ�ɥ��饹�ΥޥåפǤ��� */
    private Map<String, Class<? extends Command>> classMap;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public CommandBank() {
	classMap = new HashMap<String, Class<? extends Command>>();
    }

    /**
       ���ޥ�ɥ��饹���ɲä��ޤ���

       @param clazz ���ޥ�ɥ��饹
    */
    public void addCommandClass(final Class<? extends Command> clazz) {
	try {
	    Field nameField = clazz.getField("NAME");
	    String name = (String) nameField.get(null);
	    classMap.put(name, clazz);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error.", e);
	} catch (NoSuchFieldException e) {
	    throw new RuntimeException("internal error.", e);
	}
    }

    /**
       ���ꤷ��̾���Υ��ޥ�ɤ��������ޤ���

       @param name ���ޥ��̾
       @param props �ץ�ѥƥ�
       @param args ���ޥ�ɤΰ���
       @return ���ޥ��
    */
    public Command createCommand(final String name, final Properties props,
				 final String[] args) {
	Class<? extends Command> clazz = classMap.get(name);
	if (clazz == null) {
	    return null;
	}

	Command command = null;
	try {
	    command = clazz.getConstructor(Properties.class, String[].class)
		.newInstance(props, args);
	} catch (InvocationTargetException e) {
	    Throwable cause = e.getCause();
	    if (cause != null && cause instanceof RuntimeException) {
		throw (RuntimeException) cause;
	    }
	    throw new RuntimeException("internal error.", e);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error.", e);
	} catch (NoSuchMethodException e) {
	    throw new RuntimeException("internal error.", e);
	} catch (InstantiationException e) {
	    throw new RuntimeException("internal error.", e);
	}
	return command;
    }

    /**
       ���ޥ��̾�������������ޤ���

       @return ���ޥ��̾������
    */
    private String[] getCommandNames() {
	String[] names
	    = classMap.keySet().toArray(new String[classMap.size()]);
	Arrays.sort(names);
	return names;
    }

    /**
       ���ޥ�ɤ�������������ޤ���

       @param name ���ޥ��̾
       @return ���ޥ�ɤ�����
    */
    private String getCommandDesc(final String name) {
	String desc = "";
	Class<? extends Command> clazz = classMap.get(name);
	try {
	    Field descField = clazz.getField("DESC");
	    desc = (String) descField.get(null);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error.", e);
	} catch (NoSuchFieldException e) {
	    throw new RuntimeException("internal error.", e);
	}
	return desc;
    }

    /**
       �إ�ץ�å�������������ޤ���

       @param width ����ǥ����
       @return �إ�ץ�å�����
    */
    public String getHelpMessage(final int width) {
	String[] commands = getCommandNames();
	String m = "";
	for (String s : commands) {
	    String desc = getCommandDesc(s);
	    m += String.format("%-" + width + "s%s\n", s, desc);
	}
	return m;
    }
}
