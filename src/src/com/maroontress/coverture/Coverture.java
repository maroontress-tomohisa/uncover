package com.maroontress.coverture;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import com.maroontress.cui.OptionsParsingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;

/**
   Coverture�ε�ư���饹�Ǥ���
*/
public final class Coverture {

    /** �إ�ץ�å������Υ���ǥ�����Ǥ��� */
    private static final int INDENT_WIDTH = 32;

    /** �ǥե���ȤΥ���åɤθĿ��� */
    private static final int DEFAULT_THREADS = 4;

    /** �����Ȥ��ƽ��Ϥ��뤫�ɤ����Υե饰�Ǥ��� */
    private boolean sortsOutput;

    /** gcno�ե�����Υꥹ�ȥե�����Υѥ��Ǥ��� */
    private String inputFile;

    /** �����ϥץ�ѥƥ��Ǥ��� */
    private IOProperties props;

    /** ���ޥ�ɥ饤��ǻ��ꤵ�줿gcno�ե�����Υѥ�������Ǥ��� */
    private String[] files;

    /** gcno�ե������ѡ������륹��åɤθĿ��Ǥ��� */
    private int threads;

    /** ���ޥ�ɥ饤�󥪥ץ���������Ǥ��� */
    private Options options;

    /** gcno�ե�����Υץ��å��Ǥ��� */
    private Processor processor;

    /**
       ��ư���饹�Υ��󥹥��󥹤��������ޤ���

       @param av ���ޥ�ɥ饤�󥪥ץ���������
    */
    private Coverture(final String[] av) {
	threads = DEFAULT_THREADS;
	sortsOutput = true;
	props = new IOProperties();

	options = new Options();
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

	options.add("output-dir", new OptionListener() {
	    public void run(final String name, final String arg) {
		props.setOutputDir(new File(arg));
	    }
	}, "DIR", "Specify where to place generated files.");

	options.add("input-file", new OptionListener() {
	    public void run(final String name, final String arg) {
		inputFile = arg;
	    }
	}, "FILE", "Read the list of files from FILE:\n"
		    + "FILE can be - for standard input.");

	options.add("source-file-charset", new OptionListener() {
	    public void run(final String name, final String arg)
		throws OptionsParsingException {
		props.setSourceFileCharset(getCharset(arg));
	    }
	}, "CHARSET", "Specify the charset of source files.");

	options.add("gcov", new OptionListener() {
	    public void run(final String name, final String arg) {
		props.setGcovEnabled(true);
	    }
	}, "Output .gcov files compatible with gcov.");

	options.add("gcov-file-charset", new OptionListener() {
	    public void run(final String name, final String arg)
		throws OptionsParsingException {
		props.setGcovFileCharset(getCharset(arg));
	    }
	}, "CHARSET", "Specify the charset of .gcov files.");

	options.add("threads", new OptionListener() {
	    public void run(final String name, final String arg)
		throws OptionsParsingException {
		String m = "invalid value: " + arg;
		int num;
		try {
		    num = Integer.valueOf(arg);
		} catch (NumberFormatException e) {
		    throw new OptionsParsingException(m);
		}
		if (num <= 0) {
		    throw new OptionsParsingException(m);
		}
		threads = num;
	    }
	}, "NUM", "Specify the number of parser threads:\n"
		    + "NUM > 0; 4 is the default.");

	options.add("no-sort", new OptionListener() {
	    public void run(final String name, final String arg) {
		sortsOutput = false;
	    }
	}, "Disable sorting and threading.");

	options.add("verbose", new OptionListener() {
	    public void run(final String name, final String arg) {
		props.setVerbose(true);
	    }
	}, "Be extra verbose.");

	try {
	    files = options.parse(av);
	} catch (OptionsParsingException e) {
	    System.err.println(e.getMessage());
	    usage();
	}
	if (files.length == 0 && inputFile == null) {
	    usage();
	}

	if (sortsOutput) {
	    processor = new DeliveryProcessor(props, threads);
	} else {
	    processor = new SimpleProcessor(props);
	}
    }

    /**
       ʸ�������������ޤ���

       csn��null�ξ��ϥǥե���Ȥ�ʸ��������֤��ޤ���

       @param csn ʸ������̾���ޤ���null
       @return ʸ������
       @throws OptionsParsingException �����ʸ������̾����ѤǤ��ʤ�
    */
    private Charset getCharset(final String csn)
	throws OptionsParsingException {
	if (csn == null) {
	    return Charset.defaultCharset();
	}
	try {
	    return Charset.forName(csn);
	} catch (IllegalArgumentException e) {
	    throw new OptionsParsingException("Unsupported charset: " + csn);
	}
    }

    /**
       ���ꤵ�줿�ե�����������Ϥ�¹Ԥ��ޤ���
    */
    private void run() {
	try {
	    processor.run(files, inputFile);
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
	System.exit(0);
    }

    /**
       ������ˡ��ɽ�����ޤ���

       @param out ���ϥ��ȥ꡼��
    */
    private void printUsage(final PrintStream out) {
        out.printf("Usage: coverture [Options] [FILE...]%n"
                   + "Options are:%n");
        String[] help = options.getHelpMessage(INDENT_WIDTH).split("\n");
        for (String s : help) {
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
    private void version() {
	BufferedReader in = new BufferedReader(
	    new InputStreamReader(getClass().getResourceAsStream("version")));
        try {
	    String s;
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    /**
       Coverture��¹Ԥ��ޤ���

       @param av ���ޥ�ɥ饤�󥪥ץ����
    */
    public static void main(final String[] av) {
	Coverture cov = new Coverture(av);
	cov.run();
        System.exit(0);
    }
}
