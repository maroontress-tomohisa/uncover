package com.maroontress.uncover;

import java.util.prefs.Preferences;

/**
   config���ޥ�ɤǤ���
*/
public final class ConfigCommand extends Command {
    /** ���ޥ��̾�Ǥ��� */
    public static final String NAME = "config";

    /** ���ޥ�ɤΰ����������Ǥ��� */
    public static final String ARGS = "KEY VALUE";

    /** ���ޥ�ɤ������Ǥ��� */
    public static final String DESC = "Configure global options.";

    /** ���ꤹ����ܤΥ����Ǥ��� */
    private String key;

    /** ���ꤹ����ܤ��ͤǤ��� */
    private String value;

    /**
       config���ޥ�ɤΥ��󥹥��󥹤��������ޤ���

       @param props �ץ�ѥƥ�
       @param av ���ޥ�ɤΰ���������
    */
    public ConfigCommand(final Properties props, final String[] av) {
	super(props);

	String[] args = parseArguments(av);
	if (args.length < 2) {
	    System.err.println("too few arguments.");
	    usage();
	}
	if (args.length > 2) {
	    System.err.println("too many arguments: " + args[2]);
	    usage();
	}

	key = args[0];
	value = args[1];

	if (!ConfigKey.keySet().contains(key)) {
	    System.err.println("unknown key: " + key);
	    usage();
	}
	if (value.length() > Preferences.MAX_VALUE_LENGTH) {
	    System.err.println("too long value: " + value.length());
	    usage();
	}
    }

    /** {@inheritDoc} */
    public void run() {
	Preferences prefs = Preferences.userNodeForPackage(Uncover.class);
	prefs.put(key, value);
    }
}
