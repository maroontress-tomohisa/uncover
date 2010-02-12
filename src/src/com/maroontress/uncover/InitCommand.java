package com.maroontress.uncover;

/**
   init���ޥ�ɤǤ���
*/
public final class InitCommand extends Command {
    /** ���ޥ��̾�Ǥ��� */
    public static final String NAME = "init";

    /** ���ޥ�ɤΰ����������Ǥ��� */
    public static final String ARGS = "";

    /** ���ޥ�ɤ������Ǥ��� */
    public static final String DESC = "Initializes a data base.";

    /**
       init���ޥ�ɤΥ��󥹥��󥹤��������ޤ���

       @param props �ץ�ѥƥ�
       @param av ���ޥ�ɤΰ���������
    */
    public InitCommand(final Properties props, final String[] av) {
	super(props);

	String[] args = parseArguments(av);
	if (args.length > 0) {
	    System.err.println("too many arguments: " + args[0]);
	    usage();
	}
    }

    /**
       {@inheritDoc}
    */
    public void run() {
	try {
	    String subname = getProperties().getDBFile();
	    DB db = Toolkit.getInstance().createDB(subname);
	    db.initialize();
	    db.close();
	} catch (DBException e) {
            e.printStackTrace();
	    System.exit(1);
	}
    }
}
