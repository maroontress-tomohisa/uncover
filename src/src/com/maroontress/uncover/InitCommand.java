package com.maroontress.uncover;

/**
   initコマンドです。
*/
public final class InitCommand extends DBCommand {
    /** コマンド名です。 */
    public static final String NAME = "init";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "";

    /** コマンドの説明です。 */
    public static final String DESC = "Initialize a database.";

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

    /** {@inheritDoc} */
    protected void run(final DB db) throws CommandException {
	try {
	    db.initialize();
	} catch (DBException e) {
	    throw new CommandException("can't initialize.", e);
	}
    }
}
