package com.maroontress.uncover;

import com.maroontress.cui.Options;

/**
   コマンドです。
*/
public abstract class Command {
    /** ヘルプメッセージのインデント幅です。 */
    private static final int INDENT_WIDTH = 20;

    /** プロパティです。 */
    private Properties props;

    /**
       コマンドを生成します。

       @param props プロパティ
    */
    protected Command(final Properties props) {
	this.props = props;
    }

    /**
       プロパティを取得します。

       @return プロパティ
    */
    protected final Properties getProperties() {
	return props;
    }

    /**
       コマンドを実行します。
    */
    public abstract void run();

    /**
       使用方法を表示して終了します。

       @param opt コマンドラインオプションの定義
    */
    protected final void usage(final Options opt) {
	System.err.printf(getUsage(opt));
        System.exit(1);
    }

    /**
       使用方法を取得します。

       @param opt コマンドラインオプションの定義
       @return 使用方法
    */
    private String getUsage(final Options opt) {
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
	String[] help = opt.getHelpMessage(INDENT_WIDTH).split("\n");
	for (String s : help) {
	    m += String.format("  %s\n", s);
	}
	m += "Description:\n  " + desc + "\n";
        return m;
    }
}
