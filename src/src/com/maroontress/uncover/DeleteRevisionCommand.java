package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;

/**
   delete-revision���ޥ�ɤǤ���
*/
public final class DeleteRevisionCommand extends Command {
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

    /** ��������ӥ����Ǥ��� */
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

    /**
       �ץ�������̾�ȥ�ӥ���󡢤ޤ��ϥӥ��ID����ꤷ�ơ��ӥ�ɤ�
       �������ޤ���

       @param db �ǡ����١���
       @param projectName �ץ�������̾
       @param rev ��ӥ���󡢤ޤ��ϥӥ��ID
       @return �ӥ��
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
       @throws MultipleBuildsException ���ꤷ����ӥ�����ʣ���Υӥ�
       �ɤ�¸�ߤ���Ȥ��˥������ޤ���
    */
    private Build getBuild(final DB db, final String projectName,
			   final String rev)
	throws DBException, MultipleBuildsException {
	// ReportCommand.getBuild()�ȤޤȤ��
	if (rev.startsWith("@")) {
	    return db.getBuild(projectName, rev.substring(1));
	}
	Build[] builds = db.getBuilds(projectName, rev);
	if (builds.length > 1) {
	    String howToFix = String.format(
		"please specify the ID instead of '%s',"
		+ " or add '--all' option.\n", rev);
	    throw new MultipleBuildsException(rev, builds, howToFix);
	}
	return builds[0];
    }

    /**
       {@inheritDoc}
    */
    protected void run(final DB db) throws CommandException {
	try {
	    if (isAll) {
		db.deleteBuilds(projectName, revision);
	    } else {
		Build build = getBuild(db, projectName, revision);
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
