package com.maroontress.uncover;

/**
   new-projectコマンドです。
*/
public final class NewProjectCommand extends DBCommand {
    /** コマンド名です。 */
    public static final String NAME = "new-project";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "ARG";

    /** コマンドの説明です。 */
    public static final String DESC = "Commit a new project to the database.";

    /** プロジェクト名です。 */
    private String projectName;

    /**
       new-projectコマンドのインスタンスを生成します。

       @param props プロパティ
       @param av コマンドの引数の配列
    */
    public NewProjectCommand(final Properties props, final String[] av) {
	super(props);

	String[] args = parseArguments(av);
	if (args.length > 1) {
	    System.err.println("too many arguments: " + args[1]);
	    usage();
	}
	projectName = args[0];
    }

    /** {@inheritDoc} */
    protected void run(final DB db) throws CommandException {
	try {
	    db.newProject(projectName);
	} catch (DBException e) {
	    throw new CommandException("failed to create a new project.", e);
	}
    }
}
