package com.maroontress.uncover;

/**
   init���ޥ�ɤǤ���
*/
public final class InitCommand extends DBCommand {
    /** ���ޥ��̾�Ǥ��� */
    public static final String NAME = "init";

    /** ���ޥ�ɤΰ����������Ǥ��� */
    public static final String ARGS = "";

    /** ���ޥ�ɤ������Ǥ��� */
    public static final String DESC = "Initialize a database.";

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

    /** {@inheritDoc} */
    protected void run(final DB db) throws CommandException {
	try {
	    db.initialize();
	} catch (DBException e) {
	    throw new CommandException("can't initialize.", e);
	}
    }
}
