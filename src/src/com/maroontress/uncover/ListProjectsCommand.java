package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import com.maroontress.cui.OptionsParsingException;

/**
   list-projectsコマンドです。
*/
public final class ListProjectsCommand extends Command {
    /** コマンド名です。 */
    public static final String NAME = "list-projects";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "";

    /** コマンドの説明です。 */
    public static final String DESC = "Lists projects in the database.";

    /**
       list-projectsコマンドのインスタンスを生成します。

       @param props プロパティ
       @param av コマンドの引数の配列
    */
    public ListProjectsCommand(final Properties props, final String[] av) {
	super(props);
	final Options opt = new Options();

	opt.add("help", new OptionListener() {
	    public void run(final String name, final String arg) {
		usage(opt);
	    }
	}, "Show this message and exit.");

	String[] args = null;
	try {
	    args = opt.parse(av);
	} catch (OptionsParsingException e) {
	    System.err.println(e.getMessage());
	    usage(opt);
	}
	if (args.length > 0) {
	    System.err.println("too many arguments: " + args[0]);
	    usage(opt);
	}
    }

    /**
       {@inheritDoc}
    */
    public void run() {
	try {
	    String subname = getProperties().getDBFile();
	    DB db = Toolkit.getInstance().createDB(subname);
	    String[] projects = db.getProjects();
	    for (String s : projects) {
		System.out.println(s);
	    }
	} catch (DBException e) {
            e.printStackTrace();
	    System.exit(1);
	}
    }
}
