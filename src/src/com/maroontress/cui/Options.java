package com.maroontress.cui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
   ���ޥ�ɥ饤�󥪥ץ���������Ǥ���
*/
public final class Options {
    /** �إ�ץ�å������Υ���ǥ�����κǾ��ͤǤ��� */
    private static final int MIN_INDENT_WIDTH = 4;

    /** �����ʤ��Υ��ץ����Υ��åȤǤ��� */
    private Set<String> options;

    /** ��������Υ��ץ����Υ��åȤǤ��� */
    private Set<String> argOptions;

    /**
       ���ޥ�ɥ饤�󥪥ץ�����̾���Ȱ������ͤΥޥåפǤ��������ʤ�
       �Υ��ץ����Ǥϡ��������ͤ�null�ˤʤ�ޤ���
    */
    private Map<String, String> valueMap;

    /** ���ץ��������������Ȥ��˸ƤӽФ��ꥹ�ʤΥޥåפǤ��� */
    private Map<String, OptionListener> listenerMap;

    /** ���ץ����Υإ�ץ�å������ΥޥåפǤ��� */
    private Map<String, String> helpMap;

    /**
       ���ޥ�ɥ饤�󥪥ץ�����������������ޤ���
    */
    public Options() {
	options = new HashSet<String>();
	argOptions = new HashSet<String>();
	valueMap = new HashMap<String, String>();
	listenerMap = new HashMap<String, OptionListener>();
	helpMap = new TreeMap<String, String>();
    }

    /**
       �إ�ץ�å������Υޥåפ�������ޤ����ޥåפΥ����ϥ��ץ����
       ̾���ͤϥإ�ץ�å������ˤʤ�ޤ�����������Υ��ץ����Ǥϡ�
       ���ץ����̾�ϡ�̾��=����̾�פˤʤ�ޤ���

       @return �إ�ץ�å������Υޥå�
    */
    public Map<String, String> getHelpMap() {
	return helpMap;
    }

    /**
       �����ʤ��Υ��ץ�����������ɲä��ޤ���

       @param name ���ץ����̾
       @param listener ���ץ����ꥹ��
       @param help �إ�ץ�å�����
    */
    public void add(final String name,
		    final OptionListener listener,
		    final String help) {
	listenerMap.put(name, listener);
	add(name, help);
    }

    /**
       �����ʤ��Υ��ץ�����������ɲä��ޤ���

       @param name ���ץ����̾
       @param help �إ�ץ�å�����
    */
    public void add(final String name,
		    final String help) {
	options.add(name);
	helpMap.put(name, help);
    }

    /**
       ��������Υ��ץ�����������ɲä��ޤ���

       @param name ���ץ����̾
       @param listener ���ץ����ꥹ��
       @param argName ������̾��
       @param help �إ�ץ�å�����
    */
    public void add(final String name,
		    final OptionListener listener,
		    final String argName,
		    final String help) {
	listenerMap.put(name, listener);
	add(name, argName, help);
    }

    /**
       ��������Υ��ץ�����������ɲä��ޤ���

       @param name ���ץ����̾
       @param argName ������̾��
       @param help �إ�ץ�å�����
    */
    public void add(final String name,
		    final String argName,
		    final String help) {
	argOptions.add(name);
	helpMap.put(name + "=" + argName, help);
    }

    /**
       ʸ���󤬥��ץ���󤫤ɤ�����������ޤ���

       @param s ʸ����
       @return s�����ץ����ξ���true
    */
    private boolean isOption(final String s) {
	return s.startsWith("--");
    }

    /**
       ���ץ�����ѡ������ޤ���

       @param s ���ץ����
       @throws OptionsParsingException ���ץ����Υѡ����˼��Ԥ�����
       ���˥������ޤ���
    */
    private void parseOption(final String s) throws OptionsParsingException {
	String argName;
	String argValue;
	Set<String> set;
	int n = s.indexOf('=');
	if (n < 0) {
	    argName = s.substring(2);
	    argValue = null;
	    set = options;
	} else {
	    argName = s.substring(2, n);
	    argValue = s.substring(n + 1);
	    set = argOptions;
	}
	if (!set.contains(argName)) {
	    throw new OptionsParsingException("invalid option: " + s);
	}
	OptionListener listener = listenerMap.get(argName);
	if (listener != null) {
	    listener.run(argName, argValue);
	}
	valueMap.put(argName, argValue);
    }

    /**
       ���ޥ�ɥ饤��ΰ�����ѡ������ޤ���

       --�ǻϤޤ�����򥪥ץ����Ȥ��Ʋ�ᤷ�ޤ�������ʳ��ΰ�������
       �������ʳ��ǥѡ�����λ���ޤ���

       @param av ���ޥ�ɥ饤��ΰ���������
       @return ���ޥ�ɥ饤��ΰ����Τ������ǽ�˽и������󥪥ץ����
       �ΰ�������Ǹ�ΰ����ޤǤ�����
       @throws OptionsParsingException �����ʥ��ץ����λ���
    */
    public String[] parseFore(final String[] av)
	throws OptionsParsingException {
	String s;
	int k;

	for (k = 0; k < av.length && isOption(s = av[k]); ++k) {
	    parseOption(s);
	}
	return Arrays.copyOfRange(av, k, av.length);
    }

    /**
       ���ޥ�ɥ饤��ΰ�����ѡ������ޤ���

       --�ǻϤޤ�����򥪥ץ����Ȥ��Ʋ�ᤷ�ޤ�������ʳ��ΰ����ϥ�
       �ץ����Ȥ��Ʋ�᤻���������åפ��ޤ���

       @param av ���ޥ�ɥ饤��ΰ���������
       @return ���ץ����ǤϤʤ�����������
       @throws OptionsParsingException �����ʥ��ץ����λ���
    */
    public String[] parse(final String[] av) throws OptionsParsingException {
	ArrayList<String> args = new ArrayList<String>();

	for (String s : av) {
	    if (!isOption(s)) {
		args.add(s);
		continue;
	    }
	    parseOption(s);
	}
	return args.toArray(new String[0]);
    }

    /**
       ���ץ����ΰ������ͤ�������ޤ������ץ�����ѡ���������˸�
       �ӽФ�ɬ�פ�����ޤ���

       ���ץ���󤬻��ꤵ��ʤ����������ʤ��Υ��ץ����ξ��ϡ�null
       ���֤��ޤ���

       @param name ���ץ�����̾��
       @return ���ץ������͡��ޤ���null
    */
    public String getValue(final String name) {
	return valueMap.get(name);
    }

    /**
       ���ץ����λ����̵ͭ��������ޤ������ץ�����ѡ����������
       �ƤӽФ�ɬ�פ�����ޤ���

       @param name ���ץ�����̾��
       @return ���ץ���󤬻��ꤵ��Ƥ����true�������Ǥʤ����false
    */
    public boolean specified(final String name) {
	return valueMap.containsKey(name);
    }

    /**
       ���ץ�����������������ޤ���

       @param indentWidth ���ץ����������Υ���ǥ����
       @return ����
    */
    public String getHelpMessage(final int indentWidth) {
	int width = Math.max(MIN_INDENT_WIDTH, indentWidth);
	String helpIndent = "\n";
	for (int k = 0; k < width; ++k) {
	    helpIndent += " ";
	}
	Set<Map.Entry<String, String>> set = helpMap.entrySet();
	String format = "--%-" + (width - MIN_INDENT_WIDTH) + "s  %s\n";
	String m = "";
	for (Map.Entry<String, String> e : set) {
	    String desc = e.getValue().replace("\n", helpIndent);
	    m += String.format(format, e.getKey(), desc);
	}
	return m;
    }
}
