package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
   commit-noteコマンドです。
*/
public final class CommitNoteCommand extends DBCommand {
    /** コマンド名です。 */
    public static final String NAME = "commit-note";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "[FILE...]";

    /** コマンドの説明です。 */
    public static final String DESC = "Commit gcno files to the database.";

    /** プロジェクト名です。 */
    private String projectName;

    /** リビジョンです。 */
    private String revision;

    /** タイムスタンプです。 */
    private String timestamp;

    /** プラットフォームです。 */
    private String platform;

    /** コマンドラインで指定されたgcnoファイルのパスの配列です。 */
    private String[] files;

    /** gcnoファイルのリストファイルのパスです。 */
    private String inputFile;

    /**
       commit-noteコマンドのインスタンスを生成します。

       @param props プロパティ
       @param av コマンドの引数の配列
    */
    public CommitNoteCommand(final Properties props, final String[] av) {
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

	opt.add("input-file", new OptionListener() {
	    public void run(final String name, final String arg) {
		inputFile = arg;
	    }
	}, "FILE", "Read the list of files from FILE:\n"
		+ "FILE can be - for standard input.");

	files = parseArguments(av);
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
	if (files.length == 0 && inputFile == null) {
	    System.err.println("no gcno file specified.");
	    usage();
	}
    }

    /**
       入力ファイルのパスリストを生成します。

       @throws CommandException コマンドの実行に関するエラーが発生した
       ときにスローします。
       @return 入力ファイルのパスリスト
    */
    private List<String> createFileList() throws CommandException {
	List<String> fileList = new ArrayList<String>();
	for (String file : files) {
	    fileList.add(file);
	}
	if (inputFile == null) {
	    return fileList;
	}
	InputStreamReader in;
        try {
            if (inputFile.equals("-")) {
                in = new InputStreamReader(System.in);
            } else {
                in = new FileReader(inputFile);
            }
        } catch (FileNotFoundException e) {
	    throw new CommandException("not found: " + inputFile, e);
	}
	try {
            BufferedReader rd = new BufferedReader(in);
            String file;
            while ((file = rd.readLine()) != null) {
		fileList.add(file);
            }
	} catch (IOException e) {
	    throw new CommandException("failed to read: " + inputFile, e);
        } finally {
	    try {
		in.close();
	    } catch (IOException e) {
		throw new RuntimeException("internal error.", e);
	    }
	}
	return fileList;
    }

    /** {@inheritDoc} */
    protected void run(final DB db) throws CommandException {
	try {
	    List<String> fileList = createFileList();
	    final Parser parser
		= Toolkit.getInstance().createNoteParser(fileList);
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
	    throw new CommandException("failed to parse.", e);
	} catch (DBException e) {
	    throw new CommandException("failed to commit.", e);
	}
    }
}
