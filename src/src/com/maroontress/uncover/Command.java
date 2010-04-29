package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import com.maroontress.cui.OptionsParsingException;
import java.io.PrintStream;

/**
   ���ޥ�ɤǤ���
*/
public abstract class Command {
    /** �إ�ץ�å������Υ���ǥ�����Ǥ��� */
    private static final int INDENT_WIDTH = 20;

    /** �ץ�ѥƥ��Ǥ��� */
    private Properties props;

    /** ���ޥ����ͭ�Υ��ץ����Ǥ��� */
    private Options options;

    /**
       ���ޥ�ɤ��������ޤ���

       @param props �ץ�ѥƥ�
    */
    protected Command(final Properties props) {
	this.props = props;

	options = new Options();
	options.add("help", new OptionListener() {
	    public void run(final String name, final String arg) {
		usage();
	    }
	}, "Show this message and exit.");
    }

    /**
       �ץ�ѥƥ���������ޤ���

       @return �ץ�ѥƥ�
    */
    protected final Properties getProperties() {
	return props;
    }

    /**
       ���ץ�����������ޤ���

       @return ���ץ����
    */
    protected final Options getOptions() {
	return options;
    }

    /**
       ���ޥ�ɤ�¹Ԥ��ޤ���

       @param db �ǡ����١���
       @throws CommandException ���ޥ�ɤμ¹Ԥ˴ؤ��륨�顼��ȯ������
       �Ȥ��˥������ޤ���
    */
    protected abstract void run(DB db) throws CommandException;

    /**
       ���ޥ�ɤ�¹Ԥ��ޤ���

       @throws CommandException ���ޥ�ɤμ¹Ԥ˴ؤ��륨�顼��ȯ������
       �Ȥ��˥������ޤ���
    */
    private void runCommand() throws CommandException {
	String subname = getProperties().getDBFile();
	DB db;
	try {
	    db = Toolkit.getInstance().createDB(subname);
	} catch (DBException e) {
	    throw new CommandException("can't open database.", e);
	}
	run(db);
	try {
	    db.close();
	} catch (DBException e) {
	    throw new CommandException("can't close database.", e);
	}
    }

    /**
       ���ޥ�ɤμ¹Ԥ�ȯ�������㳰��������ޤ���

       @param e ���ޥ�ɤμ¹Ԥ˴ؤ����㳰
    */
    private void handleException(final CommandException e) {
	String subname = getProperties().getDBFile();
	try {
	    throw e.getCause();
	} catch (DBException cause) {
	    System.err.println(subname + ": " + e.getMessage());
	    cause.printStackTrace();
	} catch (MultipleBuildsException cause) {
	    System.err.println(subname + ": " + e.getMessage());
	    cause.printDescription(System.err);
	} catch (Throwable cause) {
	    cause.printStackTrace();
	}
    }

    /**
       ���ޥ�ɤ�¹Ԥ��ޤ���
    */
    public final void run() {
	try {
	    runCommand();
	} catch (CommandException e) {
	    if (e.getCause() == null) {
		e.printStackTrace();
	    } else {
		handleException(e);
	    }
	    throw new ExitException(1);
	}
    }

    /**
       ���ץ�����ѡ������ơ����ޥ�ɤΰ�����������ޤ���

       @param av ���ץ����
       @return ���ޥ�ɤΰ���
    */
    protected final String[] parseArguments(final String[] av) {
	String[] args = null;
	try {
	    args = options.parse(av);
	} catch (OptionsParsingException e) {
	    System.err.println(e.getMessage());
	    usage();
	}
	return args;
    }

    /**
       ������ˡ��ɽ�����ޤ���

       @param out ���ϥ��ȥ꡼��
    */
    private void printUsage(final PrintStream out) {
	try {
	    String name = (String) getClass().getField("NAME").get(null);
	    String args = (String) getClass().getField("ARGS").get(null);
	    String desc = (String) getClass().getField("DESC").get(null);

	    out.printf("Usage: uncover %s [Options] %s%n"
		       + "Options are:%n",
		       name, args);
	    String[] help = options.getHelpMessage(INDENT_WIDTH).split("\n");
	    for (String s : help) {
		out.printf("  %s%n", s);
	    }
	    out.printf("Description:%n"
		       + "  %s%n",
		       desc);
	} catch (NoSuchFieldException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	}
    }

    /**
       ������ˡ��ɽ�����ƽ�λ���ޤ���
    */
    protected final void usage() {
	printUsage(System.err);
        throw new ExitException(1);
    }

    /**
       �ץ�������̾�ȥ�ӥ���󡢤ޤ��ϥӥ��ID����ꤷ�ơ��ӥ�ɤ�
       �������ޤ���

       @param db �ǡ����١���
       @param projectName �ץ�������̾
       @param rev ��ӥ���󡢤ޤ��ϥӥ��ID
       @param howToFix ���ꤷ����ӥ�����ʣ���Υӥ�ɤ�¸�ߤ���Ȥ���
       �ɤΤ褦�ˤ���Хӥ�ɤ�����Ǥ��뤫����������ʸ����
       @return �ӥ��
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
       @throws MultipleBuildsException ���ꤷ����ӥ�����ʣ���Υӥ�
       �ɤ�¸�ߤ���Ȥ��˥������ޤ���
    */
    protected static Build getBuild(final DB db, final String projectName,
				    final String rev, final String howToFix)
        throws DBException, MultipleBuildsException {
        if (rev.startsWith("@")) {
            return db.getBuild(projectName, rev.substring(1));
        }
        Build[] builds = db.getBuilds(projectName, rev);
        if (builds.length > 1) {
            throw new MultipleBuildsException(rev, builds, howToFix);
        }
        return builds[0];
    }
}
