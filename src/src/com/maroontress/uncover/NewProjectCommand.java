package com.maroontress.uncover;

/**
   new-project���ޥ�ɤǤ���
*/
public final class NewProjectCommand extends DBCommand {
    /** ���ޥ��̾�Ǥ��� */
    public static final String NAME = "new-project";

    /** ���ޥ�ɤΰ����������Ǥ��� */
    public static final String ARGS = "ARG";

    /** ���ޥ�ɤ������Ǥ��� */
    public static final String DESC = "Commit a new project to the database.";

    /** �ץ�������̾�Ǥ��� */
    private String projectName;

    /**
       new-project���ޥ�ɤΥ��󥹥��󥹤��������ޤ���

       @param props �ץ�ѥƥ�
       @param av ���ޥ�ɤΰ���������
    */
    public NewProjectCommand(final Properties props, final String[] av) {
	super(props);

	String[] args = parseArguments(av);
	if (args.length > 1) {
	    System.err.println("too many arguments: " + args[1]);
	    usage();
	}
	projectName = args[0];
    }

    /** {@inheritDoc} */
    protected void run(final DB db) throws CommandException {
	try {
	    db.newProject(projectName);
	} catch (DBException e) {
	    throw new CommandException("failed to create a new project.", e);
	}
    }
}
