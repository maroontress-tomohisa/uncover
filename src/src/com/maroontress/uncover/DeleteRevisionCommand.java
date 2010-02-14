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
       Ʊ����ӥ����Υӥ�ɤ�ɽ�����ޤ���

       @param rev ��ӥ����
       @param builds �ӥ�ɤ�����
    */
    private void printBuilds(final String rev, final Build[] builds) {
	System.err.printf("revision '%s' has %d results:\n\n",
			  rev, builds.length);
	for (Build b : builds) {
	    System.err.printf(""
			      + "ID: @%s\n"
			      + "Platform: %s\n"
			      + "Timestamp: %s\n"
			      + "\n",
			      b.getID(), b.getPlatform(), b.getTimestamp());
	}
	System.err.printf("please specify the ID instead of '%s',"
			  + " or add '--all' option.\n", rev);
    }

    /**
       �ץ�������̾�ȥ�ӥ���󡢤ޤ��ϥӥ��ID����ꤷ�ơ��ӥ�ɤ�
       �������ޤ���

       ��ӥ�������ꤷ�����Υ�ӥ����˳�������ӥ�ɤ�ʣ��¸�ߤ�
       ����ϡ������Υӥ�ɤ򤹤٤�ɽ�����ƽ�λ���ޤ���

       @param db �ǡ����١���
       @param projectName �ץ�������̾
       @param rev ��ӥ���󡢤ޤ��ϥӥ��ID
       @return �ӥ��
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    private Build getBuild(final DB db, final String projectName,
			   final String rev) throws DBException {
	if (rev.startsWith("@")) {
	    return db.getBuild(projectName, rev.substring(1));
	}
	Build[] builds = db.getBuilds(projectName, rev);
	if (builds.length > 1) {
	    // throw new MultipleBuildsException(rev, builds, howToFix);
	    printBuilds(rev, builds);
	    System.exit(1);
	}
	return builds[0];
    }

    /**
       {@inheritDoc}
    */
    public void run() {
	// run()�ΰ�����db���Ϥ��褦�ˤ���
	try {
	    String subname = getProperties().getDBFile();
	    DB db = Toolkit.getInstance().createDB(subname);
	    if (isAll) {
		db.deleteBuilds(projectName, revision);
	    } else {
		Build build = getBuild(db, projectName, revision);
		db.deleteBuild(projectName, build.getID());
	    }
	} catch (DBException e) {
            e.printStackTrace();
	    System.exit(1);
	}
    }
}
