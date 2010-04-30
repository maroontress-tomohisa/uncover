package com.maroontress.uncover;

/**
   delete-projectコマンドです。
*/
public final class DeleteProjectCommand extends DBCommand {
    /** コマンド名です。 */
    public static final String NAME = "delete-project";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "ARG";

    /** コマンドの説明です。 */
    public static final String DESC = "Delete a project from the database.";

    /** プロジェクト名です。 */
    private String projectName;

    /**
       delete-projectコマンドのインスタンスを生成します。

       @param props プロパティ
       @param av コマンドの引数の配列
    */
    public DeleteProjectCommand(final Properties props, final String[] av) {
	super(props);

	String[] args = parseArguments(av);
	if (args.length < 1) {
	    System.err.println("too few arguments.");
	    usage();
	}
	if (args.length > 1) {
	    System.err.println("too many arguments: " + args[1]);
	    usage();
	}
	projectName = args[0];
    }

    /**
       {@inheritDoc}
    */
    protected void run(final DB db) throws CommandException {
	try {
	    db.deleteProject(projectName);
	} catch (DBException e) {
	    throw new CommandException(
		"can't delete project: " + projectName, e);
	}
    }
}
