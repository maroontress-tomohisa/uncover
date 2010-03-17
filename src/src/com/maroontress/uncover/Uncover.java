package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import com.maroontress.cui.OptionsParsingException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

/**
   Uncover�ε�ư���饹�Ǥ���
*/
public final class Uncover {
    /** �إ�ץ�å������Υ���ǥ�����Ǥ��� */
    private static final int INDENT_WIDTH = 18;

    /** �Хåե��Υ������Ǥ��� */
    private static final int BUFFER_SIZE = 4096;

    /** ���ޥ�ɥ饤��ǻ��ꤵ�줿���ޥ�ɤǤ��� */
    private Command command;

    /** ���ޥ�ɥ饤�󥪥ץ���������Ǥ��� */
    private Options options;

    /** ���ޥ�ɤΥХ󥯤Ǥ��� */
    private CommandBank bank;

    /**
       ��ư���饹�Υ��󥹥��󥹤��������ޤ���

       @param av ���ޥ�ɥ饤�󥪥ץ���������
    */
    private Uncover(final String[] av) {
	options = new Options();
	bank = new CommandBank();
	final Properties props = new Properties();

	bank.addCommandClass(InitCommand.class);
	bank.addCommandClass(CommitCommand.class);
	bank.addCommandClass(ReportCommand.class);
	bank.addCommandClass(ListProjectsCommand.class);
	bank.addCommandClass(ListRevisionsCommand.class);
	bank.addCommandClass(DeleteRevisionCommand.class);
	bank.addCommandClass(DeleteProjectCommand.class);
	bank.addCommandClass(GraphCommand.class);

	options.add("help", new OptionListener() {
	    public void run(final String name, final String arg) {
		usage();
	    }
	}, "Show this message and exit.");

	options.add("version", new OptionListener() {
	    public void run(final String name, final String arg) {
		version();
	    }
	}, "Show version and exit.");

	options.add("db", new OptionListener() {
	    public void run(final String name, final String arg) {
 		props.setDBFile(arg);
	    }
	}, "FILE", "Specify an SQLite database file.  Required.");

	String[] args = null;
	try {
	    args = options.parseFore(av);
	} catch (OptionsParsingException e) {
	    System.err.println(e.getMessage());
	    usage();
	}
	if (args.length == 0) {
	    System.err.println("command not specified.");
	    usage();
	}
	if (props.getDBFile() == null) {
	    System.err.println("database file not specified.");
	    usage();
	}

	String name = args[0];
	args = Arrays.copyOfRange(args, 1, args.length);

	command = bank.createCommand(name, props, args);

	if (command == null) {
	    System.err.println("unknown command: " + name);
	    usage();
	}
    }

    /**
       ���ޥ�ɤ�¹Ԥ��ޤ���
    */
    private void run() {
	command.run();
	System.exit(0);
    }

    /**
       ������ˡ��ɽ�����ޤ���

       @param out ���ϥ��ȥ꡼��
    */
    private void printUsage(final PrintStream out) {
        out.printf("Usage: uncover [Options] Command [Arguments]%n"
		   + "Options are:%n");
	String[] help = options.getHelpMessage(INDENT_WIDTH).split("\n");
	for (String s : help) {
	    out.printf("  %s%n", s);
	}
	out.printf("Commands are:%n");
	String[] commands = bank.getHelpMessage(INDENT_WIDTH).split("\n");
	for (String s : commands) {
	    out.printf("  %s%n", s);
	}
    }

    /**
       ������ˡ��ɽ�����ƽ�λ���ޤ���
    */
    private void usage() {
	printUsage(System.err);
        System.exit(1);
    }

    /**
       �С���������Ϥ��ƽ�λ���ޤ���
    */
    private static void version() {
        InputStream in = Uncover.class.getResourceAsStream("version");
        byte[] data = new byte[BUFFER_SIZE];
        int size;
        try {
            while ((size = in.read(data)) > 0) {
                System.out.write(data, 0, size);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    /**
       Uncover��¹Ԥ��ޤ���

       @param av ���ޥ�ɥ饤�󥪥ץ����
    */
    public static void main(final String[] av) {
	Uncover uncover = new Uncover(av);
	uncover.run();
        System.exit(0);
    }
}
