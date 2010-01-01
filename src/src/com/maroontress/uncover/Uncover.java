package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import com.maroontress.cui.OptionsParsingException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
   Uncoverの起動クラスです。
*/
public final class Uncover {
    /** ヘルプメッセージのインデント幅です。 */
    private static final int INDENT_WIDTH = 12;

    /** バッファのサイズです。 */
    private static final int BUFFER_SIZE = 4096;

    /** コマンドラインで指定されたコマンドです。 */
    private Command command;

    /**
       起動クラスのインスタンスを生成します。

       @param av コマンドラインオプションの配列
    */
    private Uncover(final String[] av) {
	final Options opt = new Options();
	final Properties props = new Properties();

	opt.add("help", new OptionListener() {
	    public void run(final String name, final String arg) {
		usage(opt);
	    }
	}, "Show this message and exit.");

	opt.add("version", new OptionListener() {
	    public void run(final String name, final String arg) {
		version();
	    }
	}, "Show version and exit.");

	opt.add("db", new OptionListener() {
	    public void run(final String name, final String arg) {
 		props.setDBFile(arg);
	    }
	}, "FILE", "Specify an SQLite database file.  Required.");

	String[] args = null;
	try {
	    args = opt.parseFore(av);
	} catch (OptionsParsingException e) {
	    System.err.println(e.getMessage());
	    usage(opt);
	}
	if (args.length == 0) {
	    System.err.println("command not specified.");
	    usage(opt);
	}
	if (props.getDBFile() == null) {
	    System.err.println("database file not specified.");
	    usage(opt);
	}

	String name = args[0];
	args = Arrays.copyOfRange(args, 1, args.length);
	// CommandBankクラスを用意する
	Map<String, Class<? extends Command>> classMap
	    = new HashMap<String, Class<? extends Command>>();
	classMap.put(InitCommand.NAME, InitCommand.class);
	classMap.put(CommitCommand.NAME, CommitCommand.class);
	classMap.put(ReportCommand.NAME, ReportCommand.class);
	Class<? extends Command> clazz = classMap.get(name);
	if (clazz == null) {
	    System.err.println("unknown command: " + name);
	    usage(opt);
	}
	try {
	    command = clazz.getConstructor(Properties.class, String[].class)
		.newInstance(props, args);
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	    System.exit(1);
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	    System.exit(1);
	} catch (NoSuchMethodException e) {
	    e.printStackTrace();
	    System.exit(1);
	} catch (InstantiationException e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }

    /**
       コマンドを実行します。
    */
    private void run() {
	command.run();
	System.exit(0);
    }

    /**
       使用方法を表示して終了します。

       @param opt コマンドラインオプションの定義
    */
    private static void usage(final Options opt) {
        System.err.print("Usage: uncover [Options] Command [Arguments]\n"
			 + "Options are:\n");
	String[] help = opt.getHelpMessage(INDENT_WIDTH).split("\n");
	for (String s : help) {
	    System.err.printf("  %s\n", s);
	}
        System.exit(1);
    }

    /**
       バージョンを出力して終了します。
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
       Uncoverを実行します。

       @param av コマンドラインオプション
    */
    public static void main(final String[] av) {
	Uncover uncover = new Uncover(av);
	uncover.run();
        System.exit(0);
    }
}
