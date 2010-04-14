package com.maroontress.uncover;

import com.maroontress.uncover.gxx.GxxV3Demangler;
import com.maroontress.uncover.html.Arrow;
import com.maroontress.uncover.html.Table;
import com.maroontress.uncover.html.Text;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

/**
   比較する2つのリビジョンです。
*/
public final class RevisionPair {
    /** 率をパーセントに変換するための係数です。 */
    private static final int CENT = 100;

    /** 比較するリビジョンの古い方です。 */
    private Revision old;

    /** 比較するリビジョンの新しい方です。 */
    private Revision rev;

    /** 2つのリビジョン共通に存在する関数ペアのリストです。 */
    private List<FunctionPair> pairList;

    /** 2つの値から傾向表す文字列を作成します。 */
    private Arrow trend;

    /** 2つの値から傾向表す文字列（矢印逆向き）を作成します。 */
    private Arrow invertTrend;

    /** 2つの値から差分を表す文字列を作成します。 */
    private Arrow delta;

    /**
       インスタンスを生成します。

       @param old 古いリビジョン
       @param rev 新しいリビジョン
    */
    public RevisionPair(final Revision old, final Revision rev) {
	this.old = old;
	this.rev = rev;
	pairList = old.createInnerFunctionPairs(rev);
	trend = new Arrow() {
	    protected String get(final int v1, final int v2) {
		if (v1 == v2) {
		    return "&#x2192;";
		}
		return "(" + v1 + ") " + ((v1 < v2) ? "&#x2197;" : "&#x2198;");
	    }
	};
	invertTrend = new Arrow() {
	    protected String get(final int v1, final int v2) {
		if (v1 == v2) {
		    return "&#x2192;";
		}
		return "(" + v1 + ") " + ((v1 > v2) ? "&#x2197;" : "&#x2198;");
	    }
	};
	delta = new Arrow() {
	    protected String get(final int v1, final int v2) {
		int diff = v2 - v1;
		if (diff == 0) {
		    return "&nbsp;";
		}
		return (diff > 0) ? "&#x2191;" + diff : "&#x2193;" + (-diff);
	    }
	};
    }

    /**
       HTMLレポートの要約を出力します。

       @param out 出力ストリーム
    */
    private void printSummary(final PrintStream out) {
        out.print("<h3>Summary</h3>\n"
		  + "<table border=\"1\">\n"
		  + "<tbody>\n");
        String s;
        int prevNum = old.getSize();
        int num = rev.getSize();
        String cols;
        String deltaNum;
        if (prevNum == 0 || num == prevNum) {
            cols = "2";
            deltaNum = "";
        } else {
            float rate = (float) (num - prevNum) / prevNum * CENT;
            cols = "3";
            deltaNum = Table.dataR(""
                                   + ((rate > 0) ? "&#x2191;" : "&#x2193;")
                                   + String.format("%.1f", rate)
                                   + " %");
        }
        s = "<th colspan=\"" + cols + "\">Number of Functions</th>";
        out.println(Table.row(s));
        s = Table.dataR("(" + prevNum + ")" + Text.trendArrow(prevNum, num))
            + Table.dataR("" + num)
            + deltaNum;
        out.println(Table.row(s));
        out.print("</tbody>\n"
		  + "</table>\n");
    }

    /**
       関数のリストを出力します。

       @param out 出力ストリーム
       @param deltaList 関数のリスト
    */
    private void printFunctionList(final PrintStream out,
				   final List<Function> deltaList) {
        int n = deltaList.size();
        if (n == 0) {
            out.println("<p>None</p>");
            return;
        }
        out.println("<p>" + n + " function(s) found.</p>");
        Function[] array = deltaList.toArray(new Function[n]);
        Arrays.sort(array, Function.COMPLEXITY_COMPARATOR);
        out.print("<table border=\"1\">\n"
		  + "<tbody>\n");
        String h = ""
            + "<th>Function</th>"
            + "<th>File:Line</th>"
            + "<th>CC</th>"
            + "<th>R %</th>"
            + "<th>EB/AB</th>";
        out.println(Table.row(h));
        for (Function function : array) {
            String s = ""
                + Table.dataL(demangle(function.getName()))
                + Table.dataL(function.getLocation())
                + Table.dataR("" + function.getComplexity())
                + Table.dataR(String.format("%.1f",
					    CENT * function.getBlockRate()))
                + Table.dataR("" + function.getExecutedBlocks()
                              + "/" + function.getAllBlocks());
            out.println(Table.row(s));
        }
        out.print("</tbody>\n"
		  + "</table>\n");
    }

    /**
       HTMLレポートの「追加された関数」を出力します。

       @param out 出力ストリーム
    */
    private void printAddedFunctions(final PrintStream out) {
        out.println("<h3>Added Functions</h3>");
	printFunctionList(out, rev.createOuterFunctions(old));
    }

    /**
       HTMLレポートの「削除された関数」を出力します。

       @param out 出力ストリーム
    */
    private void printRemovedFunctions(final PrintStream out) {
        out.println("<h3>Removed Functions</h3>");
	printFunctionList(out, old.createOuterFunctions(rev));
    }

    /**
       HTMLレポートの「基本ブロック数の変化」の行となるHTML形式の文字
       列を取得します。

       @param pair 関数ペア
       @return 「基本ブロック数の変化」の行
    */
    private String rowAllBlocksChanges(final FunctionPair pair) {
	Function function = pair.getRight();
        return Table.dataL(demangle(function.getName()))
            + Table.dataL(function.getLocation())
	    //
            + Table.dataR(trend.allBlocks(pair))
            + Table.dataR("" + function.getAllBlocks())
            + Table.dataR(delta.allBlocks(pair))
	    //
            + Table.dataR(trend.complexity(pair))
            + Table.dataR("" + function.getComplexity())
            + Table.dataR(delta.complexity(pair))
	    //
            + Table.dataR(String.format("%.1f", CENT * function.getBlockRate()))
            + Table.dataR("" + function.getExecutedBlocks()
                          + "/" + function.getAllBlocks());
    }

    /**
       HTMLレポートの「基本ブロック数の変化」を出力します。

       @param out 出力ストリーム
    */
    private void printAllBlocksChanges(final PrintStream out) {
	FunctionPairPrinter printer = new FunctionPairPrinter(
	    pairList, "All Blocks Changes",
	    FunctionPair.ALL_BLOCKS_DELTA_COMPARATOR,
	    Table.row("<th>Function</th>"
		      + "<th>File:Line</th>"
		      + "<th colspan=\"3\">AB</th>"
		      + "<th colspan=\"3\">CC</th>"
			  + "<th>R %</th>"
		      + "<th>EB/AB</th>")) {
	    public boolean isFiltered(final FunctionPair pair) {
		return (pair.getAllBlocksDelta() == 0);
	    }
	    public String getRow(final FunctionPair pair) {
		return Table.row(rowAllBlocksChanges(pair));
	    }
	};
	printer.printChanges(out);
    }

    /**
       HTMLレポートの「複雑度の変化」の行となるHTML形式の文字列を取得
       します。

       @param pair 関数ペア
       @return 「複雑度の変化」の行
    */
    private String rowComplexChanges(final FunctionPair pair) {
	Function function = pair.getRight();
        return Table.dataL(demangle(function.getName()))
            + Table.dataL(function.getLocation())
	    //
            + Table.dataR(trend.complexity(pair))
            + Table.dataR("" + function.getComplexity())
            + Table.dataR(delta.complexity(pair))
	    //
            + Table.dataR(String.format("%.1f", CENT * function.getBlockRate()))
            + Table.dataR("" + function.getExecutedBlocks()
                          + "/" + function.getAllBlocks());
    }

    /**
       HTMLレポートの「複雑度の変化」を出力します。

       @param out 出力ストリーム
    */
    private void printComplexityChanges(final PrintStream out) {
	FunctionPairPrinter printer = new FunctionPairPrinter(
	    pairList, "Complexity Changes",
	    FunctionPair.COMPLEXITY_DELTA_COMPARATOR,
	    Table.row("<th>Function</th>"
		      + "<th>File:Line</th>"
		      + "<th colspan=\"3\">CC</th>"
		      + "<th>R %</th>"
		      + "<th>EB/AB</th>")) {
	    public boolean isFiltered(final FunctionPair pair) {
		return (pair.getComplexityDelta() == 0);
	    }
	    public String getRow(final FunctionPair pair) {
		return Table.row(rowComplexChanges(pair));
	    }
	};
	printer.printChanges(out);
    }

    /**
       HTMLレポートの「未実行ブロック数の変化」の行となるHTML形式の文
       字列を取得します。

       @param pair 関数ペア
       @return 「未実行ブロック数の変化」の行
    */
    private String rowUnexecutedBlocksChanges(final FunctionPair pair) {
	Function function = pair.getRight();
	return Table.dataL(demangle(function.getName()))
            + Table.dataL(function.getLocation())
	    //
            + Table.dataR(trend.unexecutedBlocks(pair))
            + Table.dataR("" + function.getUnexecutedBlocks())
            + Table.dataR(delta.unexecutedBlocks(pair))
	    //
            + Table.dataR(trend.complexity(pair))
            + Table.dataR("" + function.getComplexity())
            + Table.dataR(delta.complexity(pair))
	    //
            + Table.dataR(String.format("%.1f", CENT * function.getBlockRate()))
            + Table.dataR("" + function.getExecutedBlocks()
                          + "/" + function.getAllBlocks());
    }

    /**
       HTMLレポートの「未実行ブロック数の変化」を出力します。

       @param out 出力ストリーム
    */
    private void printUnexecutedBlocksChanges(final PrintStream out) {
	FunctionPairPrinter printer = new FunctionPairPrinter(
	    pairList, "Unexecuted Blocks Changes",
	    FunctionPair.UNEXECUTED_BLOCKS_DELTA_COMPARATOR,
	    Table.row("<th>Function</th>"
		      + "<th>File:Line</th>"
		      + "<th colspan=\"3\">AB &minus; EB</th>"
		      + "<th colspan=\"3\">CC</th>"
		      + "<th>R %</th>"
		      + "<th>EB/AB</th>")) {
	    public boolean isFiltered(final FunctionPair pair) {
		return (pair.getUnexecutedBlocksDelta() == 0);
	    }
	    public String getRow(final FunctionPair pair) {
		return Table.row(rowUnexecutedBlocksChanges(pair));
	    }
	};
	printer.printChanges(out);
    }

    /**
       HTMLレポートの「複雑度順位とその変化」の行となるHTML形式の文字
       列を取得します。

       @param function 関数
       @param rank 関数の複雑度ランク
       @param rankTrend ランクの傾向となる文字列
       @param complexityTrend 複雑度の傾向となる文字列
       @return 「複雑度順位とその変化」の行
    */
    private String rowComplexityRanking(final Function function,
					final int rank,
					final String rankTrend,
					final String complexityTrend) {
        return Table.dataR(rankTrend)
            + Table.dataR("" + rank)
            + Table.dataL(demangle(function.getName()))
            + Table.dataL(function.getLocation())
            + Table.dataR(complexityTrend)
            + Table.dataR("" + function.getComplexity())
            + Table.dataR(String.format("%.1f", CENT * function.getBlockRate()))
            + Table.dataR("" + function.getExecutedBlocks()
                          + "/" + function.getAllBlocks());
    }

    /**
       HTMLレポートの「複雑度順位とその変化」を出力します。

       @param out 出力ストリーム
    */
    private void printComplexityRanking(final PrintStream out) {
        final int maxRank = 50;
        out.printf("<h3>Complexity Ranking (TOP %d)</h3>\n", maxRank);

        Function[] array = rev.createFunctionArray();
        Arrays.sort(array, Function.COMPLEXITY_COMPARATOR);
        out.print("<table border=\"1\">\n"
		  + "<tbody>\n");
        String h = ""
            + "<th colspan=\"2\">Rank</th>"
            + "<th>Function</th>"
            + "<th>File:Line</th>"
            + "<th colspan=\"2\">CC</th>"
            + "<th>R %</th>"
            + "<th>EB/AB</th>";
        out.println(Table.row(h));

	for (Function function : array) {
            String key = function.getKey();
            int rank = rev.getComplexityRank(key);
            if (rank > maxRank) {
                break;
            }
            Function oldFunction = old.getFunction(key);
	    String rankTrend;
	    String complexityTrend;
            if (oldFunction == null) {
		rankTrend = "New";
		complexityTrend = "New";
            } else {
		int oldRank = old.getComplexityRank(key);
		rankTrend = invertTrend.rank(oldRank, rank);
		complexityTrend = trend.complexity(oldFunction, function);
	    }
            String row = Table.row(rowComplexityRanking(function, rank,
							rankTrend,
							complexityTrend));
	    out.println(row);
        }
        out.print("</tbody>\n"
		  + "</table>\n");
    }

    /**
       HTMLレポートを出力します。

       @param out 出力ストリーム
    */
    public void printHTMLReport(final PrintStream out) {
        printSummary(out);
        printAddedFunctions(out);
        printRemovedFunctions(out);
        printAllBlocksChanges(out);
        printComplexityChanges(out);
        printUnexecutedBlocksChanges(out);
        printComplexityRanking(out);
    }

    /**
       マングルされたシンボル名をデマングルします。

       @param name マングルされた名前
       @return デマングルした名前
    */
    public String demangle(final String name) {
	CxxDemangler d = new GxxV3Demangler(name);
	return d.getName()
	    .replace("&", "&amp;")
	    .replace("<", "&lt;")
	    .replace(">", "&gt;");
    }
}
