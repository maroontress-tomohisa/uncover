package com.maroontress.uncover;

import com.maroontress.cui.Options;

/**
   list-projectsコマンドです。
*/
public final class ListProjectsCommand extends DBCommand {
    /** コマンド名です。 */
    public static final String NAME = "list-projects";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "";

    /** コマンドの説明です。 */
    public static final String DESC = "List projects in the database.";

    /**
       list-projectsコマンドのインスタンスを生成します。

       @param props プロパティ
       @param av コマンドの引数の配列
    */
    public ListProjectsCommand(final Properties props, final String[] av) {
	super(props);

	Options opt = getOptions();

	String[] args = parseArguments(av);
	if (args.length > 0) {
	    System.err.println("too many arguments: " + args[0]);
	    usage();
	}
    }

    /** {@inheritDoc} */
    protected void run(final DB db) throws CommandException {
	try {
	    String[] projects = db.getProjectNames();
	    for (String s : projects) {
		System.out.println(s);
	    }
	} catch (DBException e) {
	    throw new CommandException("can't list projects.", e);
	}
    }
}
