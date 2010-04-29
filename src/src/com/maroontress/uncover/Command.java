package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import com.maroontress.cui.OptionsParsingException;
import java.io.PrintStream;

/**
   コマンドです。
*/
public abstract class Command {
    /** ヘルプメッセージのインデント幅です。 */
    private static final int INDENT_WIDTH = 20;

    /** プロパティです。 */
    private Properties props;

    /** コマンド特有のオプションです。 */
    private Options options;

    /**
       コマンドを生成します。

       @param props プロパティ
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
       プロパティを取得します。

       @return プロパティ
    */
    protected final Properties getProperties() {
	return props;
    }

    /**
       オプションを取得します。

       @return オプション
    */
    protected final Options getOptions() {
	return options;
    }

    /**
       コマンドを実行します。

       @param db データベース
       @throws CommandException コマンドの実行に関するエラーが発生した
       ときにスローします。
    */
    protected abstract void run(DB db) throws CommandException;

    /**
       コマンドを実行します。

       @throws CommandException コマンドの実行に関するエラーが発生した
       ときにスローします。
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
       コマンドの実行で発生した例外を処理します。

       @param e コマンドの実行に関する例外
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
       コマンドを実行します。
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
       オプションをパースして、コマンドの引数を取得します。

       @param av オプション
       @return コマンドの引数
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
       使用方法を表示します。

       @param out 出力ストリーム
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
       使用方法を表示して終了します。
    */
    protected final void usage() {
	printUsage(System.err);
        throw new ExitException(1);
    }

    /**
       プロジェクト名とリビジョン、またはビルドIDを指定して、ビルドを
       取得します。

       @param db データベース
       @param projectName プロジェクト名
       @param rev リビジョン、またはビルドID
       @param howToFix 指定したリビジョンに複数のビルドが存在するとき、
       どのようにすればビルドを特定できるかを説明する文字列
       @return ビルド
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
       @throws MultipleBuildsException 指定したリビジョンに複数のビル
       ドが存在するときにスローします。
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
