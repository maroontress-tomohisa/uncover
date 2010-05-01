package com.maroontress.uncover;

import com.maroontress.cui.Options;

/**
   list-projects���ޥ�ɤǤ���
*/
public final class ListProjectsCommand extends DBCommand {
    /** ���ޥ��̾�Ǥ��� */
    public static final String NAME = "list-projects";

    /** ���ޥ�ɤΰ����������Ǥ��� */
    public static final String ARGS = "";

    /** ���ޥ�ɤ������Ǥ��� */
    public static final String DESC = "List projects in the database.";

    /**
       list-projects���ޥ�ɤΥ��󥹥��󥹤��������ޤ���

       @param props �ץ�ѥƥ�
       @param av ���ޥ�ɤΰ���������
    */
    public ListProjectsCommand(final Properties props, final String[] av) {
	super(props);

	Options opt = getOptions();

	String[] args = parseArguments(av);
	if (args.length > 0) {
	    System.err.println("too many arguments: " + args[0]);
	    usage();
	}
    }

    /** {@inheritDoc} */
    protected void run(final DB db) throws CommandException {
	try {
	    String[] projects = db.getProjectNames();
	    for (String s : projects) {
		System.out.println(s);
	    }
	} catch (DBException e) {
	    throw new CommandException("can't list projects.", e);
	}
    }
}
