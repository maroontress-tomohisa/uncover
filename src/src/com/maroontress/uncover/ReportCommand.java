package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;

/**
   reportコマンドです。
*/
public final class ReportCommand extends DBCommand {
    /** コマンド名です。 */
    public static final String NAME = "report";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "OLD_REV NEW_REV";

    /** コマンドの説明です。 */
    public static final String DESC = "Output a report.";

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

	Options opt = getOptions();
	opt.add("project", new OptionListener() {
	    public void run(final String name, final String arg) {
		projectName = arg;
	    }
	}, "ARG", "Specify a project name.  Required.");

	String[] args = parseArguments(av);
	if (args.length < 2) {
	    System.err.println("too few arguments.");
	    usage();
	}
	if (args.length > 2) {
	    System.err.println("too many arguments: " + args[2]);
	    usage();
	}
	if (projectName == null || projectName.isEmpty()) {
	    System.err.println("--project=ARG must be specified.");
	    usage();
	}
	oldRevision = args[0];
	newRevision = args[1];
    }

    /**
       プロジェクト名とリビジョン、またはビルドIDを指定して、ビルドを
       取得します。

       @param db データベース
       @param projectName プロジェクト名
       @param rev リビジョン、またはビルドID
       @return ビルド
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
       @throws MultipleBuildsException 指定したリビジョンに複数のビル
       ドが存在するときにスローします。
    */
    private static Build getBuild(final DB db, final String projectName,
				  final String rev)
        throws DBException, MultipleBuildsException {
	String howToFix = String.format(
	    "please specify the ID instead of '%s'.%n", rev);
	return getBuild(db, projectName, rev, howToFix);
    }

    /** {@inheritDoc} */
    protected void run(final DB db) throws CommandException {
	try {
	    Build oldBuild = getBuild(db, projectName, oldRevision);
	    Build newBuild = getBuild(db, projectName, newRevision);
	    if (!oldBuild.getPlatform().equals(newBuild.getPlatform())) {
		System.err.printf(
		    "warning: the platforms of '%s' and '%s' are different.%n",
		    oldRevision, newRevision);
	    }
	    RevisionPair pair
		= new RevisionPair(db.getRevision(oldBuild.getID()),
				   db.getRevision(newBuild.getID()));
	    pair.printHTMLReport(System.out);
	} catch (MultipleBuildsException e) {
	    throw new CommandException("can't output a report.", e);
	} catch (DBException e) {
	    throw new CommandException("can't output a report.", e);
	}
    }
}
