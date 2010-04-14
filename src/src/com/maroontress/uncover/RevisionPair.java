package com.maroontress.uncover;

import com.maroontress.uncover.gxx.GxxV3Demangler;
import com.maroontress.uncover.html.Arrow;
import com.maroontress.uncover.html.Table;
import com.maroontress.uncover.html.Text;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

/**
   ��Ӥ���2�ĤΥ�ӥ����Ǥ���
*/
public final class RevisionPair {
    /** Ψ��ѡ�����Ȥ��Ѵ����뤿��η����Ǥ��� */
    private static final int CENT = 100;

    /** ��Ӥ����ӥ����θŤ����Ǥ��� */
    private Revision old;

    /** ��Ӥ����ӥ����ο��������Ǥ��� */
    private Revision rev;

    /** 2�ĤΥ�ӥ�����̤�¸�ߤ���ؿ��ڥ��Υꥹ�ȤǤ��� */
    private List<FunctionPair> pairList;

    /** 2�Ĥ��ͤ��鷹��ɽ��ʸ�����������ޤ��� */
    private Arrow trend;

    /** 2�Ĥ��ͤ��鷹��ɽ��ʸ���������ո����ˤ�������ޤ��� */
    private Arrow invertTrend;

    /** 2�Ĥ��ͤ��麹ʬ��ɽ��ʸ�����������ޤ��� */
    private Arrow delta;

    /**
       ���󥹥��󥹤��������ޤ���

       @param old �Ť���ӥ����
       @param rev ��������ӥ����
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
       HTML��ݡ��Ȥ��������Ϥ��ޤ���

       @param out ���ϥ��ȥ꡼��
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
       �ؿ��Υꥹ�Ȥ���Ϥ��ޤ���

       @param out ���ϥ��ȥ꡼��
       @param deltaList �ؿ��Υꥹ��
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
       HTML��ݡ��ȤΡ��ɲä��줿�ؿ��פ���Ϥ��ޤ���

       @param out ���ϥ��ȥ꡼��
    */
    private void printAddedFunctions(final PrintStream out) {
        out.println("<h3>Added Functions</h3>");
	printFunctionList(out, rev.createOuterFunctions(old));
    }

    /**
       HTML��ݡ��ȤΡֺ�����줿�ؿ��פ���Ϥ��ޤ���

       @param out ���ϥ��ȥ꡼��
    */
    private void printRemovedFunctions(final PrintStream out) {
        out.println("<h3>Removed Functions</h3>");
	printFunctionList(out, old.createOuterFunctions(rev));
    }

    /**
       HTML��ݡ��ȤΡִ��ܥ֥�å������Ѳ��פιԤȤʤ�HTML������ʸ��
       ���������ޤ���

       @param pair �ؿ��ڥ�
       @return �ִ��ܥ֥�å������Ѳ��פι�
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
       HTML��ݡ��ȤΡִ��ܥ֥�å������Ѳ��פ���Ϥ��ޤ���

       @param out ���ϥ��ȥ꡼��
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
       HTML��ݡ��ȤΡ�ʣ���٤��Ѳ��פιԤȤʤ�HTML������ʸ��������
       ���ޤ���

       @param pair �ؿ��ڥ�
       @return ��ʣ���٤��Ѳ��פι�
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
       HTML��ݡ��ȤΡ�ʣ���٤��Ѳ��פ���Ϥ��ޤ���

       @param out ���ϥ��ȥ꡼��
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
       HTML��ݡ��ȤΡ�̤�¹ԥ֥�å������Ѳ��פιԤȤʤ�HTML������ʸ
       �����������ޤ���

       @param pair �ؿ��ڥ�
       @return ��̤�¹ԥ֥�å������Ѳ��פι�
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
       HTML��ݡ��ȤΡ�̤�¹ԥ֥�å������Ѳ��פ���Ϥ��ޤ���

       @param out ���ϥ��ȥ꡼��
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
       HTML��ݡ��ȤΡ�ʣ���ٽ�̤Ȥ����Ѳ��פιԤȤʤ�HTML������ʸ��
       ���������ޤ���

       @param function �ؿ�
       @param rank �ؿ���ʣ���٥��
       @param rankTrend ��󥯤η����Ȥʤ�ʸ����
       @param complexityTrend ʣ���٤η����Ȥʤ�ʸ����
       @return ��ʣ���ٽ�̤Ȥ����Ѳ��פι�
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
       HTML��ݡ��ȤΡ�ʣ���ٽ�̤Ȥ����Ѳ��פ���Ϥ��ޤ���

       @param out ���ϥ��ȥ꡼��
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
       HTML��ݡ��Ȥ���Ϥ��ޤ���

       @param out ���ϥ��ȥ꡼��
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
       �ޥ󥰥뤵�줿����ܥ�̾��ǥޥ󥰥뤷�ޤ���

       @param name �ޥ󥰥뤵�줿̾��
       @return �ǥޥ󥰥뤷��̾��
    */
    public String demangle(final String name) {
	CxxDemangler d = new GxxV3Demangler(name);
	return d.getName()
	    .replace("&", "&amp;")
	    .replace("<", "&lt;")
	    .replace(">", "&gt;");
    }
}
