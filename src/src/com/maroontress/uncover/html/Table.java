package com.maroontress.uncover.html;

/**
   HTMLテーブルのための機能を提供します。
*/
public final class Table {
    /**
       コンストラクタは利用できません。
    */
    private Table() {
    }

    /**
       テーブルのデータ（左寄せ）を作成します。

       @param s 文字列
       @return テーブルのデータ
    */
    public static String dataL(final String s) {
        return "<td align=\"left\">" + s + "</td>";
    }

    /**
       テーブルのデータ（右寄せ）を作成します。

       @param s 文字列
       @return テーブルのデータ
    */
    public static String dataR(final String s) {
        return "<td align=\"right\">" + s + "</td>";
    }

    /**
       テーブルの行を作成します。

       @param s 文字列
       @return テーブルの行
    */
    public static String row(final String s) {
        return "<tr>" + s + "</tr>";
    }
}
