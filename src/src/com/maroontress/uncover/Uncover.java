package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import com.maroontress.cui.OptionsParsingException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;

/**
   Uncoverの起動クラスです。
*/
public final class Uncover {
    /** ヘルプメッセージのインデント幅です。 */
    private static final int INDENT_WIDTH = 18;

    /** コマンドラインで指定されたコマンドです。 */
    private Command command;

    /** コマンドラインオプションの定義です。 */
    private Options options;

    /** コマンドのバンクです。 */
    private CommandBank bank;

    /**
       起動クラスのインスタンスを生成します。

       @param av コマンドラインオプションの配列
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

	String name = args[0];
	args = Arrays.copyOfRange(args, 1, args.length);
	command = bank.createCommand(name, props, args);
	if (command == null) {
	    System.err.println("unknown command: " + name);
	    usage();
	}

	if (props.getDBFile() == null) {
	    System.err.println("database file not specified.");
	    usage();
	}
    }

    /**
       コマンドを実行します。
    */
    private void run() {
	command.run();
	throw new ExitException(0);
    }

    /**
       使用方法を表示します。

       @param out 出力ストリーム
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
       使用方法を表示して終了します。
    */
    private void usage() {
	printUsage(System.err);
        throw new ExitException(1);
    }

    /**
       バージョンを出力して終了します。
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
        throw new ExitException(1);
    }

    /**
       Uncoverを実行します。

       @param av コマンドラインオプション
    */
    public static void main(final String[] av) {
	try {
	    Uncover uncover = new Uncover(av);
	    uncover.run();
	} catch (ExitException e) {
	    Toolkit.getInstance().exit(e.getExitStatus());
	}
    }
}
