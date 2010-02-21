package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import com.maroontress.cui.OptionsParsingException;

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
	    System.exit(1);
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
       ������ˡ��ɽ�����ƽ�λ���ޤ���
    */
    protected final void usage() {
	System.err.printf(getUsage());
        System.exit(1);
    }

    /**
       ������ˡ��������ޤ���

       @return ������ˡ
    */
    private String getUsage() {
	String name;
	String args;
	String desc;
	try {
	    name = (String) getClass().getField("NAME").get(null);
	    args = (String) getClass().getField("ARGS").get(null);
	    desc = (String) getClass().getField("DESC").get(null);
	} catch (NoSuchFieldException e) {
	    return null;
	} catch (IllegalAccessException e) {
	    return null;
	}

        String m = String.format("Usage: uncover %s [Options] %s\n"
				 + "Options are:\n", name, args);
	String[] help = options.getHelpMessage(INDENT_WIDTH).split("\n");
	for (String s : help) {
	    m += String.format("  %s\n", s);
	}
	m += "Description:\n  " + desc + "\n";
        return m;
    }
}
