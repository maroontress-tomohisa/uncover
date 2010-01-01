package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import com.maroontress.cui.OptionsParsingException;

/**
   reportコマンドです。
*/
public final class ReportCommand extends Command {
    /** コマンド名です。 */
    public static final String NAME = "report";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "OLD_REV NEW_REV";

    /** コマンドの説明です。 */
    public static final String DESC = "Outputs a report.";

    /** 古いリビジョンです。 */
    private String oldRevision;

    /** 新しいリビジョンです。 */
    private String newRevision;

    /** プロジェクト名です。 */
    private String projectName;

    /**
       reportコマンドのインスタンスを生成します。

       @param props プロパティ
       @param av コマンドの引数の配列
    */
    public ReportCommand(final Properties props, final String[] av) {
	super(props);
	final Options opt = new Options();

	// このコードは抽象クラスに移す
	opt.add("help", new OptionListener() {
	    public void run(final String name, final String arg) {
		usage(opt);
	    }
	}, "Show this message and exit.");

	opt.add("project", new OptionListener() {
	    public void run(final String name, final String arg) {
		projectName = arg;
	    }
	}, "ARG", "Specify a project name.  Required.");


	String[] args = null;
	try {
	    args = opt.parse(av);
	} catch (OptionsParsingException e) {
	    System.err.println(e.getMessage());
	    usage(opt);
	}
	if (args.length < 2) {
	    System.err.println("too few arguments.");
	    usage(opt);
	}
	if (args.length > 2) {
	    System.err.println("too many arguments: " + args[2]);
	    usage(opt);
	}
	if (projectName == null || projectName.isEmpty()) {
	    System.err.println("--project=ARG must be specified.");
	    usage(opt);
	}
	oldRevision = args[0];
	newRevision = args[1];
    }

    /**
       {@inheritDoc}
    */
    public void run() {
	try {
	    String subname = getProperties().getDBFile();
	    DB db = Toolkit.getInstance().createDB(subname);
	    Revision rev1 = db.getRevision(projectName, oldRevision);
	    Revision rev2 = db.getRevision(projectName, newRevision);
	    RevisionPair pair = new RevisionPair(rev1, rev2);
	    pair.printHTMLReport(System.out);
	    db.close();
	} catch (DBException e) {
            e.printStackTrace();
	    System.exit(1);
	}
    }
}
