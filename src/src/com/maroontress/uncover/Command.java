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
    */
    public abstract void run();

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
	    throw new RuntimeException("internal error.", e);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error.", e);
	}
    }

    /**
       ������ˡ��ɽ�����ƽ�λ���ޤ���
    */
    protected final void usage() {
	printUsage(System.err);
        throw new ExitException(1);
    }
}
