package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import java.util.Calendar;

/**
   commitコマンドです。
*/
public final class CommitCommand extends DBCommand {
    /** コマンド名です。 */
    public static final String NAME = "commit";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "FILE";

    /** コマンドの説明です。 */
    public static final String DESC = "Commit FILE to the database.";

    /** プロジェクト名です。 */
    private String projectName;

    /** リビジョンです。 */
    private String revision;

    /** タイムスタンプです。 */
    private String timestamp;

    /** プラットフォームです。 */
    private String platform;

    /** Covertureが出力したファイルのパスです。 */
    private String xmlFile;

    /**
       commitコマンドのインスタンスを生成します。

       @param props プロパティ
       @param av コマンドの引数の配列
    */
    public CommitCommand(final Properties props, final String[] av) {
	super(props);

	Options opt = getOptions();
	opt.add("project", new OptionListener() {
	    public void run(final String name, final String arg) {
		projectName = arg;
	    }
	}, "ARG", "Specify a project name.  Required.");

	opt.add("revision", new OptionListener() {
	    public void run(final String name, final String arg) {
		revision = arg;
	    }
	}, "ARG", "Specify a revision.  Required.");

	opt.add("timestamp", new OptionListener() {
	    public void run(final String name, final String arg) {
		timestamp = arg;
	    }
	}, "ARG", "Specify a date.  ARG must be formatted as\n"
		+ "'YYYY-MM-DD hh:mm:ss'.");

	opt.add("platform", new OptionListener() {
	    public void run(final String name, final String arg) {
		platform = arg;
	    }
	}, "ARG", "Specify a platform.");

	String[] args = parseArguments(av);
	if (args.length < 1) {
	    System.err.println("FILE must be specified.");
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
	if (revision == null || revision.isEmpty()) {
	    System.err.println("--revision=ARG must be specified.");
	    usage();
	}
	if (revision.startsWith("@")) {
	    System.err.println("revision can't start with '@': " + revision);
	    usage();
	}
	if (timestamp == null) {
	    Calendar cal = Calendar.getInstance();
	    timestamp = String.format("%04d-%02d-%02d %02d:%02d:%02d",
				      cal.get(Calendar.YEAR),
				      cal.get(Calendar.MONTH) + 1,
				      cal.get(Calendar.DAY_OF_MONTH),
				      cal.get(Calendar.HOUR_OF_DAY),
				      cal.get(Calendar.MINUTE),
				      cal.get(Calendar.SECOND));
	}
	if (platform == null) {
	    platform = String.format("%s %s %s",
				     System.getProperty("os.name"),
				     System.getProperty("os.arch"),
				     System.getProperty("os.version"));
	}
	xmlFile = args[0];
    }

    /** {@inheritDoc} */
    protected void run(final DB db) throws CommandException {
	try {
	    final Parser parser = Toolkit.getInstance().createParser(xmlFile);
	    db.commit(new CommitSource() {
		public String getProjectName() {
		return projectName;
		}
		public String getRevision() {
		    return revision;
		}
		public String getTimestamp() {
		    return timestamp;
		}
		public String getPlatform() {
		    return platform;
		}
		public Iterable<FunctionGraph> getAllFunctionGraphs() {
		    return parser;
		}
	    });
	} catch (ParsingException e) {
	    throw new CommandException("can't import: " + xmlFile, e);
	} catch (DBException e) {
	    throw new CommandException("failed to commit.", e);
	}
    }
}
