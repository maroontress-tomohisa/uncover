package com.maroontress.uncover.html;

/**
   HTMLテキストのための機能を提供します。
*/
public final class Text {
    /**
       コンストラクタは利用できません。
    */
    private Text() {
    }

    /**
       ボールドのテキストを生成します。

       @param s 文字列
       @return ボールドのテキスト
    */
    public static String bold(final String s) {
        return "<b>" + s + "</b>";
    }

    /**
       2つの値から矢印を生成します。

       前の値から今の値への傾向に応じた向きの矢印を生成します。

       @param prev 前の値
       @param num 今の値
       @return 矢印
    */
    public static String trendArrow(final int prev, final int num) {
        if (prev == num) {
            return "&#x2192;";
        }
        return ((prev < num) ? "&#x2197;" : "&#x2198;");
    }
}
