package com.maroontress.uncover;

import com.maroontress.cui.OptionListener;
import com.maroontress.cui.Options;
import java.io.PrintStream;

/**
   graphコマンドです。
*/
public final class GraphCommand extends DBCommand {
    /** コマンド名です。 */
    public static final String NAME = "graph";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "FUNCTION@FILE";

    /** コマンドの説明です。 */
    public static final String DESC = "Output a function graph.";

    /** プロジェクト名です。 */
    private String projectName;

    /** リビジョンです。 */
    private String revision;

    /** 関数名です。 */
    private String function;

    /** gcnoファイルの名前です。 */
    private String gcnoFile;

    /**
       graphコマンドのインスタンスを生成します。

       @param props プロパティ
       @param av コマンドの引数の配列
    */
    public GraphCommand(final Properties props, final String[] av) {
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

	String[] args = parseArguments(av);
	if (args.length < 1) {
	    System.err.println(ARGS + " must be specified.");
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
	String key = args[0];
	int i = key.indexOf('@');
	if (i < 0) {
	    System.err.println("invalid argument: " + key);
	    usage();
	}
	function = key.substring(0, i);
	gcnoFile = key.substring(i + 1);
    }

    /**
       グラフを出力します。

       @param out 出力ストリーム
       @param graph グラフ
    */
    private void printGraph(final PrintStream out, final Graph graph) {
	Block[] allBlocks = graph.getAllBlocks();
	Arc[] allArcs = graph.getAllArcs();

	String key = function + "@" + gcnoFile;
	out.printf("digraph \"%s\" {\n"
	           + "rankdir=LR;\n",
		   key);

	for (Block b : allBlocks) {
	    int num = b.getNumber();
	    String label = b.getSourceFile();
	    if (label != null) {
		int line = b.getLineNumber();
		if (line > 0) {
		    label += "\\nL" + line + "~";
		}
		out.printf("L%d -> %d [style=\"dotted\","
			   + "dir=\"none\",color=\"#808080\"];\n",
			   num, num);
		out.printf("L%d [label=\"%s\",shape=\"note\","
			   + "fontsize=\"8\",fontcolor=\"#808080\","
			   + "color=\"#808080\"];\n",
			   num, label);
		out.printf("{rank=same; %d L%d}\n",
			   num, num);
	    }
	    if (b.getCount() > 0) {
                out.printf("%d [style=\"filled\",fillcolor=\"#8080ff\"];\n",
			   num);
	    }
	}

	for (Arc a : allArcs) {
	    String extraStyle = "";
	    if (a.getCount() > 0) {
		extraStyle = ",color=\"#8080ff\"";
	    }
	    int start = a.getStart();
	    int end = a.getEnd();
	    if (a.isFake()) {
		out.printf("%d -> \"%d_%d\" [style=\"dashed\"%s];\n",
			   start, start, end, extraStyle);
		out.printf("\"%d_%d\" [label=\"%d\",style=\"dashed\"];\n",
			   start, end, end);
	    } else {
		out.printf("%d -> %d [style=\"solid\"%s];\n",
			   start, end, extraStyle);
	    }
	}

	out.printf("}\n");
    }

    /**
       {@inheritDoc}
    */
    protected void run(final DB db) throws CommandException {
	try {
            String howToFix = String.format(
                "please specify the ID instead of '%s'.", revision);
	    Build build = getBuild(db, projectName, revision, howToFix);
	    Graph graph = db.getGraph(projectName, build.getID(),
				      function, gcnoFile);
	    printGraph(System.out, graph);
        } catch (MultipleBuildsException e) {
	    throw new CommandException("failed to output a graph.", e);
	} catch (DBException e) {
	    throw new CommandException("failed to output a graph.", e);
	}
    }
}
