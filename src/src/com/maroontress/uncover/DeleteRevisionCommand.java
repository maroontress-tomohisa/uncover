package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;

/**
   delete-revision���ޥ�ɤǤ���
*/
public final class DeleteRevisionCommand extends DBCommand {
    /** ���ޥ��̾�Ǥ��� */
    public static final String NAME = "delete-revision";

    /** ���ޥ�ɤΰ����������Ǥ��� */
    public static final String ARGS = "REV";

    /** ���ޥ�ɤ������Ǥ��� */
    public static final String DESC = "Delete a revision from the database.";

    /** �ץ�������̾�Ǥ��� */
    private String projectName;

    /** ��ӥ����Ǥ��� */
    private String revision;

    /** ��ӥ����˴ޤޤ�뤹�٤ƤΥӥ�ɤ��оݤȤ��뤫�ɤ����Ǥ��� */
    private boolean isAll;

    /**
       delete-revision���ޥ�ɤΥ��󥹥��󥹤��������ޤ���

       @param props �ץ�ѥƥ�
       @param av ���ޥ�ɤΰ���������
    */
    public DeleteRevisionCommand(final Properties props, final String[] av) {
	super(props);

	Options opt = getOptions();
	opt.add("project", new OptionListener() {
	    public void run(final String name, final String arg) {
		projectName = arg;
	    }
	}, "ARG", "Specify a project name.  Required.");

	opt.add("all", new OptionListener() {
	    public void run(final String name, final String arg) {
		isAll = true;
	    }
	}, "Deletes all REV matches.");

	String[] args = parseArguments(av);
	if (args.length < 1) {
	    System.err.println("too few arguments.");
	    usage();
	}
	if (args.length > 1) {
	    System.err.println("too many arguments: " + args[1]);
	    usage();
	}
	if (projectName == null || projectName.isEmpty()) {
	    System.err.println("--project=ARG must be specified.");
	    usage();
	}
	revision = args[0];
    }

    /** {@inheritDoc} */
    protected void run(final DB db) throws CommandException {
	try {
	    if (isAll) {
		db.deleteBuilds(projectName, revision);
	    } else {
		String howToFix = String.format(
		    "please specify the ID instead of '%s',"
		    + " or add '--all' option.%n", revision);
		Build build = getBuild(db, projectName, revision, howToFix);
		db.deleteBuild(projectName, build.getID());
	    }
	} catch (MultipleBuildsException e) {
	    throw new CommandException(
		"can't delete revision: " + revision, e);
	} catch (DBException e) {
	    throw new CommandException(
		"can't delete revision: " + revision, e);
	}
    }
}
