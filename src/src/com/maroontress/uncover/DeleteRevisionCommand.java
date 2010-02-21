package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;

/**
   delete-revisionコマンドです。
*/
public final class DeleteRevisionCommand extends Command {
    /** コマンド名です。 */
    public static final String NAME = "delete-revision";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "REV";

    /** コマンドの説明です。 */
    public static final String DESC = "Delete a revision from the database.";

    /** プロジェクト名です。 */
    private String projectName;

    /** リビジョンです。 */
    private String revision;

    /** 新しいリビジョンです。 */
    private boolean isAll;

    /**
       delete-revisionコマンドのインスタンスを生成します。

       @param props プロパティ
       @param av コマンドの引数の配列
    */
    public DeleteRevisionCommand(final Properties props, final String[] av) {
	super(props);

	Options opt = getOptions();
	opt.add("project", new OptionListener() {
	    public void run(final String name, final String arg) {
		projectName = arg;
	    }
	}, "ARG", "Specify a project name.  Required.");

	opt.add("all", new OptionListener() {
	    public void run(final String name, final String arg) {
		isAll = true;
	    }
	}, "Deletes all REV matches.");

	String[] args = parseArguments(av);
	if (args.length < 1) {
	    System.err.println("too few arguments.");
	    usage();
	}
	if (args.length > 1) {
	    System.err.println("too many arguments: " + args[1]);
	    usage();
	}
	if (projectName == null || projectName.isEmpty()) {
	    System.err.println("--project=ARG must be specified.");
	    usage();
	}
	revision = args[0];
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
    private Build getBuild(final DB db, final String projectName,
			   final String rev)
	throws DBException, MultipleBuildsException {
	// ReportCommand.getBuild()とまとめる
	if (rev.startsWith("@")) {
	    return db.getBuild(projectName, rev.substring(1));
	}
	Build[] builds = db.getBuilds(projectName, rev);
	if (builds.length > 1) {
	    String howToFix = String.format(
		"please specify the ID instead of '%s',"
		+ " or add '--all' option.\n", rev);
	    throw new MultipleBuildsException(rev, builds, howToFix);
	}
	return builds[0];
    }

    /**
       {@inheritDoc}
    */
    protected void run(final DB db) throws CommandException {
	try {
	    if (isAll) {
		db.deleteBuilds(projectName, revision);
	    } else {
		Build build = getBuild(db, projectName, revision);
		db.deleteBuild(projectName, build.getID());
	    }
	} catch (MultipleBuildsException e) {
	    throw new CommandException(
		"can't delete revision: " + revision, e);
	} catch (DBException e) {
	    throw new CommandException(
		"can't delete revision: " + revision, e);
	}
    }
}
