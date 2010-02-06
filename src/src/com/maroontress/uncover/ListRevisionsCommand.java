package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import com.maroontress.cui.OptionsParsingException;

/**
   list-revisions���ޥ�ɤǤ���
*/
public final class ListRevisionsCommand extends Command {
    /** ���ޥ��̾�Ǥ��� */
    public static final String NAME = "list-revisions";

    /** ���ޥ�ɤΰ����������Ǥ��� */
    public static final String ARGS = "";

    /** ���ޥ�ɤ������Ǥ��� */
    public static final String DESC = "Lists revisions in the database.";

    /** �ץ�������̾�Ǥ��� */
    private String projectName;

    /**
       list-revisions���ޥ�ɤΥ��󥹥��󥹤��������ޤ���

       @param props �ץ�ѥƥ�
       @param av ���ޥ�ɤΰ���������
    */
    public ListRevisionsCommand(final Properties props, final String[] av) {
	super(props);
	final Options opt = new Options();

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
	if (args.length > 0) {
	    System.err.println("too many arguments: " + args[0]);
	    usage(opt);
	}
	if (projectName == null || projectName.isEmpty()) {
	    System.err.println("--project=ARG must be specified.");
	    usage(opt);
	}
    }

    /**
       {@inheritDoc}
    */
    public void run() {
	try {
	    String subname = getProperties().getDBFile();
	    DB db = Toolkit.getInstance().createDB(subname);
	    String[] revisions = db.getRevisionNames(projectName);
	    for (String s : revisions) {
		System.out.println(s);
	    }
	} catch (DBException e) {
            e.printStackTrace();
	    System.exit(1);
	}
    }
}
