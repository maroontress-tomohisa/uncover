package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;

/**
   report���ޥ�ɤǤ���
*/
public final class ReportCommand extends DBCommand {
    /** ���ޥ��̾�Ǥ��� */
    public static final String NAME = "report";

    /** ���ޥ�ɤΰ����������Ǥ��� */
    public static final String ARGS = "OLD_REV NEW_REV";

    /** ���ޥ�ɤ������Ǥ��� */
    public static final String DESC = "Output a report.";

    /** �Ť���ӥ����Ǥ��� */
    private String oldRevision;

    /** ��������ӥ����Ǥ��� */
    private String newRevision;

    /** �ץ�������̾�Ǥ��� */
    private String projectName;

    /**
       report���ޥ�ɤΥ��󥹥��󥹤��������ޤ���

       @param props �ץ�ѥƥ�
       @param av ���ޥ�ɤΰ���������
    */
    public ReportCommand(final Properties props, final String[] av) {
	super(props);

	Options opt = getOptions();
	opt.add("project", new OptionListener() {
	    public void run(final String name, final String arg) {
		projectName = arg;
	    }
	}, "ARG", "Specify a project name.  Required.");

	String[] args = parseArguments(av);
	if (args.length < 2) {
	    System.err.println("too few arguments.");
	    usage();
	}
	if (args.length > 2) {
	    System.err.println("too many arguments: " + args[2]);
	    usage();
	}
	if (projectName == null || projectName.isEmpty()) {
	    System.err.println("--project=ARG must be specified.");
	    usage();
	}
	oldRevision = args[0];
	newRevision = args[1];
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
    private static Build getBuild(final DB db, final String projectName,
				  final String rev)
        throws DBException, MultipleBuildsException {
	String howToFix = String.format(
	    "please specify the ID instead of '%s'.%n", rev);
	return getBuild(db, projectName, rev, howToFix);
    }

    /** {@inheritDoc} */
    protected void run(final DB db) throws CommandException {
	try {
	    Build oldBuild = getBuild(db, projectName, oldRevision);
	    Build newBuild = getBuild(db, projectName, newRevision);
	    if (!oldBuild.getPlatform().equals(newBuild.getPlatform())) {
		System.err.printf(
		    "warning: the platforms of '%s' and '%s' are different.%n",
		    oldRevision, newRevision);
	    }
	    RevisionPair pair
		= new RevisionPair(db.getRevision(oldBuild.getID()),
				   db.getRevision(newBuild.getID()));
	    pair.printHTMLReport(System.out);
	} catch (MultipleBuildsException e) {
	    throw new CommandException("can't output a report.", e);
	} catch (DBException e) {
	    throw new CommandException("can't output a report.", e);
	}
    }
}
