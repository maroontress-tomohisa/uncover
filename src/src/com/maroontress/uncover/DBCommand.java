package com.maroontress.uncover;

/**
   データベースにアクセスするコマンドです。
*/
public abstract class DBCommand extends Command {
    /**
       コマンドを生成します。

       @param props プロパティ
    */
    protected DBCommand(final Properties props) {
	super(props);
    }

    /**
       コマンドを実行します。

       @param db データベース
       @throws CommandException コマンドの実行に関するエラーが発生した
       ときにスローします。
    */
    protected abstract void run(DB db) throws CommandException;

    /**
       コマンドを実行します。

       @param subname データベースファイルのパス
       @throws CommandException コマンドの実行に関するエラーが発生した
       ときにスローします。
    */
    private void runCommand(final String subname) throws CommandException {
	DB db;
	try {
	    db = Toolkit.getInstance().createDB(subname);
	} catch (DBException e) {
	    throw new CommandException("can't open database.", e);
	}
	run(db);
	try {
	    db.close();
	} catch (DBException e) {
	    throw new CommandException("can't close database.", e);
	}
    }

    /**
       コマンドの実行で発生した例外を処理します。

       @param e コマンドの実行に関する例外
    */
    private void handleException(final CommandException e) {
	String subname = getProperties().getDBFile();
	try {
	    throw e.getCause();
	} catch (DBException cause) {
	    System.err.println(subname + ": " + e.getMessage());
	    cause.printStackTrace();
	} catch (MultipleBuildsException cause) {
	    System.err.println(subname + ": " + e.getMessage());
	    cause.printDescription(System.err);
	} catch (Throwable cause) {
	    cause.printStackTrace();
	}
    }

    /**
       コマンドを実行します。
    */
    public final void run() {
	String subname = getProperties().getDBFile();
	if (subname == null) {
	    System.err.println("database file not specified.");
	    usage();
	}
	try {
	    runCommand(subname);
	} catch (CommandException e) {
	    if (e.getCause() == null) {
		e.printStackTrace();
	    } else {
		handleException(e);
	    }
	    throw new ExitException(1);
	}
    }

    /**
       プロジェクト名とリビジョン、またはビルドIDを指定して、ビルドを
       取得します。

       @param db データベース
       @param projectName プロジェクト名
       @param rev リビジョン、またはビルドID
       @param howToFix 指定したリビジョンに複数のビルドが存在するとき、
       どのようにすればビルドを特定できるかを説明する文字列
       @return ビルド
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
       @throws MultipleBuildsException 指定したリビジョンに複数のビル
       ドが存在するときにスローします。
    */
    protected static Build getBuild(final DB db, final String projectName,
				    final String rev, final String howToFix)
        throws DBException, MultipleBuildsException {
        if (rev.startsWith("@")) {
            return db.getBuild(projectName, rev.substring(1));
        }
        Build[] builds = db.getBuilds(projectName, rev);
        if (builds.length > 1) {
            throw new MultipleBuildsException(rev, builds, howToFix);
        }
        return builds[0];
    }
}
