package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import com.maroontress.cui.OptionsParsingException;

/**
   report���ޥ�ɤǤ���
*/
public final class ReportCommand extends Command {
    /** ���ޥ��̾�Ǥ��� */
    public static final String NAME = "report";

    /** ���ޥ�ɤΰ����������Ǥ��� */
    public static final String ARGS = "OLD_REV NEW_REV";

    /** ���ޥ�ɤ������Ǥ��� */
    public static final String DESC = "Outputs a report.";

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
	final Options opt = new Options();

	// ���Υ����ɤ���ݥ��饹�˰ܤ�
	opt.add("help", new OptionListener() {
	    public void run(final String name, final String arg) {
		usage(opt);
	    }
	}, "Show this message and exit.");

	opt.add("project", new OptionListener() {
	    public void run(final String name, final String arg) {
		projectName = arg;
	    }
	}, "ARG", "Specify a project name.  Required.");


	String[] args = null;
	try {
	    args = opt.parse(av);
	} catch (OptionsParsingException e) {
	    System.err.println(e.getMessage());
	    usage(opt);
	}
	if (args.length < 2) {
	    System.err.println("too few arguments.");
	    usage(opt);
	}
	if (args.length > 2) {
	    System.err.println("too many arguments: " + args[2]);
	    usage(opt);
	}
	if (projectName == null || projectName.isEmpty()) {
	    System.err.println("--project=ARG must be specified.");
	    usage(opt);
	}
	oldRevision = args[0];
	newRevision = args[1];
    }

    /**
       {@inheritDoc}
    */
    public void run() {
	try {
	    String subname = getProperties().getDBFile();
	    DB db = Toolkit.getInstance().createDB(subname);
	    Revision rev1 = db.getRevision(projectName, oldRevision);
	    Revision rev2 = db.getRevision(projectName, newRevision);
	    RevisionPair pair = new RevisionPair(rev1, rev2);
	    pair.printHTMLReport(System.out);
	    db.close();
	} catch (DBException e) {
            e.printStackTrace();
	    System.exit(1);
	}
    }
}
