package com.maroontress.cui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
   ���ޥ�ɥ饤�󥪥ץ���������Ǥ���
*/
public final class Options {
    /** �إ�ץ�å������Υ���ǥ�����κǾ��ͤǤ��� */
    private static final int MIN_INDENT_WIDTH = 2;

    /**
       ���ץ����̾�ȥ��ץ����ΥޥåפǤ������ץ����Ϥ��٤Ƥ��Υޥ�
       �פ���Ͽ����ޤ���
    */
    private Map<String, Option> nameMap;

    /**
       ���ץ�����û��̾�ȥ��ץ����ΥޥåפǤ���û��̾���ĥ��ץ���
       ����������Υޥåפ���Ͽ����ޤ���
    */
    private Map<Character, Option> shortNameMap;

    /**
       ���ޥ�ɥ饤�󥪥ץ�����������������ޤ���
    */
    public Options() {
	nameMap = new TreeMap<String, Option>();
	shortNameMap = new TreeMap<Character, Option>();
    }

    /**
       �إ�ץ�å������Υޥåפ�������ޤ����ޥåפΥ����ϥ��ץ����
       ̾���ͤϥإ�ץ�å������ˤʤ�ޤ�����������Υ��ץ����Ǥϡ�
       ���ץ����̾�ϡ�̾��=����̾�פˤʤ�ޤ���

       @return �إ�ץ�å������Υޥå�
    */
    @Deprecated public Map<String, String> getHelpMap() {
	Map<String, String> map = new TreeMap<String, String>();
	Collection<Option> allOptions = nameMap.values();
	for (Option o : allOptions) {
	    map.put(o.getHelpName(), o.getHelpDesc());
	}
	return map;
    }

    /**
       ���ץ������ɲä��ޤ���

       @param option ���ץ����
    */
    public void add(final Option option) {
	String name = option.getName();
	nameMap.put(name, option);

	Character shortName = option.getShortName();
	if (shortName != null) {
	    shortNameMap.put(shortName, option);
	}
    }

    /**
       �����ʤ��Υ��ץ�����������ɲä��ޤ���

       @param name ���ץ����̾
       @param listener ���ץ����ꥹ��
       @param help �إ�ץ�å�����
    */
    public void add(final String name, final OptionListener listener,
		    final String help) {
	add(new Option(name, null, listener, null, help));
    }

    /**
       �����ʤ��Υ��ץ�����������ɲä��ޤ���

       @param name ���ץ����̾
       @param help �إ�ץ�å�����
    */
    public void add(final String name, final String help) {
	add(new Option(name, null, null, null, help));
    }

    /**
       ��������Υ��ץ�����������ɲä��ޤ���

       @param name ���ץ����̾
       @param listener ���ץ����ꥹ��
       @param argName ������̾��
       @param help �إ�ץ�å�����
    */
    public void add(final String name, final OptionListener listener,
		    final String argName, final String help) {
	add(new Option(name, null, listener, argName, help));
    }

    /**
       ��������Υ��ץ�����������ɲä��ޤ���

       @param name ���ץ����̾
       @param argName ������̾��
       @param help �إ�ץ�å�����
    */
    public void add(final String name, final String argName,
		    final String help) {
	add(new Option(name, null, null, argName, help));
    }

    /**
       ��󥰷������ץ�����ѡ������ޤ���

       @param s ���ץ����
       @throws OptionsParsingException ���ץ����Υѡ����˼��Ԥ�����
       ���˥������ޤ���
    */
    private void parseOption(final String s) throws OptionsParsingException {
	String argName;
	String argValue;
	boolean hasArg;
	int n = s.indexOf('=');
	if (n < 0) {
	    argName = s.substring(2);
	    argValue = null;
	    hasArg = false;
	} else {
	    argName = s.substring(2, n);
	    argValue = s.substring(n + 1);
	    hasArg = true;
	}
	Option opt = nameMap.get(argName);
	if (opt == null || opt.hasArgument() != hasArg) {
	    throw new OptionsParsingException("invalid option: " + s);
	}
	opt.setValue(argValue);
    }

    /**
       û��̾�Υ��ץ�����ѡ������ޤ���

       @param c ���ץ����̾
       @param av ���ץ���������
       @param offset ���ץ����Υ���ǥå���
       @return ���ץ����Ȥ��ƾ��񤷤���������ǿ�
       @throws OptionsParsingException ���ץ����Υѡ����˼��Ԥ�����
       ���˥������ޤ���
    */
    private int parseShortOption(final char c, final String[] av,
				 final int offset)
	throws OptionsParsingException {
	String s = av[offset];
	Option opt = shortNameMap.get(c);
	if (opt == null) {
	    throw new OptionsParsingException("invalid option: " + s);
	}
	if (!opt.hasArgument()) {
	    opt.setValue(null);
	    return 1;
	}
	int k = offset + 1;
	if (k >= av.length) {
	    throw new OptionsParsingException("argument not found: " + s);
	}
	opt.setValue(av[k]);
	return 2;
    }

    /**
       ���ץ�����ѡ������ޤ���

       @param av ���ץ���������
       @param offset ���ץ����Υ���ǥå���
       @return ���ץ����Ȥ��ƾ��񤷤���������ǿ����󥪥ץ����Ǥ�0
       @throws OptionsParsingException ���ץ����Υѡ����˼��Ԥ�����
       ���˥������ޤ���
    */
    private int parseOption(final String[] av,
			    final int offset) throws OptionsParsingException {
	String s = av[offset];
	if (s.startsWith("--")) {
	    parseOption(s);
	    return 1;
	}
	if (!s.startsWith("-")) {
	    return 0;
	}
	if (s.length() != 2) {
	    throw new OptionsParsingException("invalid option: " + s);
	}
	return parseShortOption(s.charAt(1), av, offset);
    }

    /**
       ���ޥ�ɥ饤��ΰ�����ѡ������ޤ���

       -�ޤ���--�ǻϤޤ�����򥪥ץ����Ȥ��Ʋ�ᤷ�ޤ�������ʳ��ΰ�
       �����и������ʳ��ǥѡ�����λ���ޤ���

       @param av ���ޥ�ɥ饤��ΰ���������
       @return ���ޥ�ɥ饤��ΰ����Τ������ǽ�˽и������󥪥ץ����
       �ΰ�������Ǹ�ΰ����ޤǤ�����
       @throws OptionsParsingException �����ʥ��ץ����λ���
    */
    public String[] parseFore(final String[] av)
	throws OptionsParsingException {
	int k;
	int d;

	for (k = 0; k < av.length && (d = parseOption(av, k)) > 0; k += d) {
	    continue;
	}
	return Arrays.copyOfRange(av, k, av.length);
    }

    /**
       ���ޥ�ɥ饤��ΰ�����ѡ������ޤ���

       -�ޤ���--�ǻϤޤ�����򥪥ץ����Ȥ��Ʋ�ᤷ�ޤ�������ʳ��ΰ�
       ���ϥ��ץ����Ȥ��Ʋ�᤻���������åפ��ޤ���

       @param av ���ޥ�ɥ饤��ΰ���������
       @return ���ץ����ǤϤʤ�����������
       @throws OptionsParsingException �����ʥ��ץ����λ���
    */
    public String[] parse(final String[] av) throws OptionsParsingException {
	ArrayList<String> args = new ArrayList<String>();
	int k;
	int d;

	for (k = 0; k < av.length && (d = parseOption(av, k)) >= 0; k += d) {
	    if (d == 0) {
		args.add(av[k]);
		++d;
	    }
	}
	return args.toArray(new String[args.size()]);
    }

    /**
       ���ꤷ��̾���Υ��ץ�����������ޤ���

       @param name ���ץ����̾
       @return ���ץ����
    */
    private Option getOption(final String name) {
	Option option = nameMap.get(name);
	if (option == null) {
	    throw new IllegalArgumentException("not found: " + name);
	}
	return option;
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
	return getOption(name).getValue();
    }

    /**
       ���ץ����λ����̵ͭ��������ޤ������ץ�����ѡ����������
       �ƤӽФ�ɬ�פ�����ޤ���

       @param name ���ץ�����̾��
       @return ���ץ���󤬻��ꤵ��Ƥ����true�������Ǥʤ����false
    */
    public boolean specified(final String name) {
	return getOption(name).isSpecified();
    }

    /**
       ���ץ�����������������ޤ���

       @param indentWidth ���ץ����������Υ���ǥ����
       @return ����
    */
    public String getHelpMessage(final int indentWidth) {
	int width = Math.max(MIN_INDENT_WIDTH, indentWidth);
	StringBuilder b = new StringBuilder("\n");
	for (int k = 0; k < width; ++k) {
	    b.append(" ");
	}
	String helpIndent = b.toString();
	String format = "%-" + (width - MIN_INDENT_WIDTH) + "s  %s\n";
	b = new StringBuilder();

	Set<String> nameSet = nameMap.keySet();
	for (String name : nameSet) {
	    Option opt = nameMap.get(name);
	    String key = opt.getHelpName();
	    String desc = opt.getHelpDesc().replace("\n", helpIndent);
	    b.append(String.format(format, key, desc));
	}
	return b.toString();
    }
}
