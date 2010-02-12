package com.maroontress.uncover;

/**
   initコマンドです。
*/
public final class InitCommand extends Command {
    /** コマンド名です。 */
    public static final String NAME = "init";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "";

    /** コマンドの説明です。 */
    public static final String DESC = "Initializes a data base.";

    /**
       initコマンドのインスタンスを生成します。

       @param props プロパティ
       @param av コマンドの引数の配列
    */
    public InitCommand(final Properties props, final String[] av) {
	super(props);

	String[] args = parseArguments(av);
	if (args.length > 0) {
	    System.err.println("too many arguments: " + args[0]);
	    usage();
	}
    }

    /**
       {@inheritDoc}
    */
    public void run() {
	try {
	    String subname = getProperties().getDBFile();
	    DB db = Toolkit.getInstance().createDB(subname);
	    db.initialize();
	    db.close();
	} catch (DBException e) {
            e.printStackTrace();
	    System.exit(1);
	}
    }
}
