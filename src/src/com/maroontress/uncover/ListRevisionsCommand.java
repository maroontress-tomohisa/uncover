package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;

/**
   list-revisionsコマンドです。
*/
public final class ListRevisionsCommand extends Command {
    /** コマンド名です。 */
    public static final String NAME = "list-revisions";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "";

    /** コマンドの説明です。 */
    public static final String DESC = "List revisions in the database.";

    /** プロジェクト名です。 */
    private String projectName;

    /**
       list-revisionsコマンドのインスタンスを生成します。

       @param props プロパティ
       @param av コマンドの引数の配列
    */
    public ListRevisionsCommand(final Properties props, final String[] av) {
	super(props);

	Options opt = getOptions();
	opt.add("project", new OptionListener() {
	    public void run(final String name, final String arg) {
		projectName = arg;
	    }
	}, "ARG", "Specify a project name.  Required.");

	String[] args = parseArguments(av);
	if (args.length > 0) {
	    System.err.println("too many arguments: " + args[0]);
	    usage();
	}
	if (projectName == null || projectName.isEmpty()) {
	    System.err.println("--project=ARG must be specified.");
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
	    String[] revisions = db.getRevisionNames(projectName);
	    for (String s : revisions) {
		System.out.println(s);
	    }
	} catch (DBException e) {
            e.printStackTrace();
	    System.exit(1);
	}
    }
}
