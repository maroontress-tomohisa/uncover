package com.maroontress.cui;

/**
   ���ץ����Ǥ���
*/
public final class Option {
    /** ���ץ����̾�Ǥ��� */
    private final String name;

    /** ���ץ�����û��̾�Ǥ��� */
    private final Character shortName;

    /** ���ץ����ꥹ�ʤǤ��� */
    private final OptionListener listener;

    /** ���ץ����ΰ���̾�Ǥ��� */
    private final String argName;

    /** �إ�פ�ɽ�����������Ǥ��� */
    private final String helpDesc;

    /** �إ�פ�̾���Ǥ��� */
    private final String helpName;

    /** ���ץ����������ͤǤ��� */
    private String value;

    /** ���ץ�������ꤵ��Ƥ��뤫�ɤ����Ǥ��� */
    private boolean specified;

    /**
       @param name ���ץ����̾
       @param shortName ���ץ�����û��̾
       @param listener ���ץ����ꥹ��
       @param argName ���ץ����ΰ���̾
       @param helpDesc �إ�פ�ɽ����������
    */
    public Option(final String name, final Character shortName,
		  final OptionListener listener, final String argName,
		  final String helpDesc) {
	this.name = name;
	this.shortName = shortName;
	this.listener = listener;
	this.argName = argName;
	this.helpDesc = helpDesc;

	value = null;
	specified = false;
	if (argName == null && shortName == null) {
	    helpName = String.format("--%s", name);
	} else if (argName == null) {
	    helpName = String.format("--%s, -%c", name, shortName);
	} else if (shortName == null) {
	    helpName = String.format("--%s=%s", name, argName);
	} else {
	    helpName = String.format("--%s=%s, -%c %s",
				     name, argName, shortName, argName);
	}
    }

    /**
       ���ץ����̾��������ޤ���

       @return ���ץ����̾
    */
    public String getName() {
	return name;
    }

    /**
       ���ץ�����û��̾��������ޤ���

       @return ���ץ�����û��̾
    */
    public Character getShortName() {
	return shortName;
    }

    /**
       ���������ꤷ�����ץ���������Ѥߤξ��֤ˤ��ޤ���

       @param value ���ץ����������͡��ޤ���null
       @throws OptionsParsingException ���ץ������ͤ�����
    */
    public void setValue(final String value) throws OptionsParsingException {
	this.value = value;
	specified = true;
	if (listener == null) {
	    return;
	}
	listener.run(name, value);
    }

    /**
       ���ץ��������ꤵ��Ƥ��������������ޤ���

       ���ץ�������ꤵ��Ƥ��ʤ��������ץ���󤬰�����⤿�ʤ����
       ��null���֤��ޤ���

       @return value ���ץ����������͡��ޤ���null
    */
    public String getValue() {
	return value;
    }

    /**
       ���ץ�������ꤵ��Ƥ��뤫�ɤ�����������ޤ���

       @return ���ץ�������ꤵ��Ƥ������true
    */
    public boolean isSpecified() {
	return specified;
    }

    /**
       ���ץ���󤬰�����Ȥ뤫�ɤ�����������ޤ���

       @return ������Ȥ����true
    */
    public boolean hasArgument() {
	return argName != null;
    }

    /**
       �إ�פ�ɽ������̾����������ޤ���

       @return �إ�פ�̾��
    */
    public String getHelpName() {
	return helpName;
    }

    /**
       �إ�פ�ɽ������������������ޤ���

       @return �إ�פ�����
    */
    public String getHelpDesc() {
	return helpDesc;
    }
}
