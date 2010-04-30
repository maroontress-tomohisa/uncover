package com.maroontress.uncover;

/**
   delete-project���ޥ�ɤǤ���
*/
public final class DeleteProjectCommand extends DBCommand {
    /** ���ޥ��̾�Ǥ��� */
    public static final String NAME = "delete-project";

    /** ���ޥ�ɤΰ����������Ǥ��� */
    public static final String ARGS = "ARG";

    /** ���ޥ�ɤ������Ǥ��� */
    public static final String DESC = "Delete a project from the database.";

    /** �ץ�������̾�Ǥ��� */
    private String projectName;

    /**
       delete-project���ޥ�ɤΥ��󥹥��󥹤��������ޤ���

       @param props �ץ�ѥƥ�
       @param av ���ޥ�ɤΰ���������
    */
    public DeleteProjectCommand(final Properties props, final String[] av) {
	super(props);

	String[] args = parseArguments(av);
	if (args.length < 1) {
	    System.err.println("too few arguments.");
	    usage();
	}
	if (args.length > 1) {
	    System.err.println("too many arguments: " + args[1]);
	    usage();
	}
	projectName = args[0];
    }

    /**
       {@inheritDoc}
    */
    protected void run(final DB db) throws CommandException {
	try {
	    db.deleteProject(projectName);
	} catch (DBException e) {
	    throw new CommandException(
		"can't delete project: " + projectName, e);
	}
    }
}
