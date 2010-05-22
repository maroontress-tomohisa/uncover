package com.maroontress.uncover;

import java.util.Set;
import java.util.prefs.Preferences;

/**
   show-config���ޥ�ɤǤ���
*/
public final class ShowConfigCommand extends Command {
    /** ���ޥ��̾�Ǥ��� */
    public static final String NAME = "show-config";

    /** ���ޥ�ɤΰ����������Ǥ��� */
    public static final String ARGS = "";

    /** ���ޥ�ɤ������Ǥ��� */
    public static final String DESC = "Show configuration.";

    /**
       config���ޥ�ɤΥ��󥹥��󥹤��������ޤ���

       @param props �ץ�ѥƥ�
       @param av ���ޥ�ɤΰ���������
    */
    public ShowConfigCommand(final Properties props, final String[] av) {
	super(props);

	String[] args = parseArguments(av);
	if (args.length > 0) {
	    System.err.println("too many arguments: " + args[0]);
	    usage();
	}
    }

    /** {@inheritDoc} */
    public void run() {
	Preferences prefs = Preferences.userNodeForPackage(Uncover.class);
	Set<String> keySet = ConfigKey.keySet();
	for (String key : keySet) {
	    String value = prefs.get(key, null);
	    if (value == null) {
		System.out.println(key);
	    } else {
		System.out.printf("%s %s%n", key, value);
	    }
	}
    }
}
