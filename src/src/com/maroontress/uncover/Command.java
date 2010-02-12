package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import com.maroontress.cui.OptionsParsingException;

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
    */
    public abstract void run();

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
       使用方法を表示して終了します。
    */
    protected final void usage() {
	System.err.printf(getUsage());
        System.exit(1);
    }

    /**
       使用方法を取得します。

       @return 使用方法
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
