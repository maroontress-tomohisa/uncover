package com.maroontress.uncover;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
   関数ペアを出力するためのクラスです。
*/
public abstract class FunctionPairPrinter {
    /** 関数ペアのリストです。 */
    private List<FunctionPair> pairList;

    /** タイトルです。 */
    private String title;

    /** 関数ペアの出力順を決定するコンパレータです。 */
    private Comparator<FunctionPair> comparator;

    /** HTMLテーブルのヘッダとなる行です。 */
    private String header;

    /**
       デフォルトコンストラクタは使用できません。
    */
    private FunctionPairPrinter() {
    }

    /**
       インスタンスを生成します。

       @param pairList 関数ペアのリスト
       @param title タイトル
       @param comparator 関数ペアの出力順を決定するコンパレータ
       @param header HTMLテーブルのヘッダとなる行
    */
    protected FunctionPairPrinter(final List<FunctionPair> pairList,
				  final String title,
				  final Comparator<FunctionPair> comparator,
				  final String header) {
	this.pairList = pairList;
	this.title = title;
	this.comparator = comparator;
	this.header = header;
    }

    /**
       HTMLレポートの「変化」の行を取得します。

       @param pair 関数ペア
       @return 「変化」の行
    */
    public abstract String getRow(FunctionPair pair);

    /**
       関数ペアをフィルタするかどうかを取得します。

       @param pair 関数ペア
       @return フィルタする場合はtrue
    */
    public abstract boolean isFiltered(FunctionPair pair);

    /**
       選ばれた関数ペアの要素だけを配列で取得します。

       @param all 関数ペア
       @return 選択された関数ペアの配列
    */
    private FunctionPair[] select(final Iterable<FunctionPair> all) {
        List<FunctionPair> list = new ArrayList<FunctionPair>();
	for (FunctionPair pair : all) {
	    if (isFiltered(pair)) {
		continue;
	    }
            list.add(pair);
	}
	return list.toArray(new FunctionPair[list.size()]);
    }

    /**
       HTMLレポートの「変化」を出力します。

       @param out 出力ストリーム
    */
    public final void printChanges(final PrintStream out) {
        out.printf("<h3>%s</h3>\n", title);
        FunctionPair[] array = select(pairList);
        if (array.length == 0) {
            out.print("<p>None</p>\n");
            return;
        }
        out.printf("<p>%d/%d function(s) found.</p>\n",
		   array.length, pairList.size());
        Arrays.sort(array, comparator);
        out.print("<table border=\"1\">\n"
		  + "<tbody>\n");
        out.printf("%s\n", header);
        for (FunctionPair pair : array) {
            out.printf("%s\n", getRow(pair));
        }
        out.print("</tbody>\n"
		  + "</table>\n");
    }
}
