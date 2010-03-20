package com.maroontress.uncover.sqlite;

/**
   テーブルに関する定数を保持します。
*/
public final class Table {
    /** テーブルのプレフィックスです。 */
    public static final String PREFIX = "com_maroontress_uncover_";

    /** 設定のテーブル名です。 */
    public static final String CONFIG = PREFIX + "config";

    /** プロジェクトのテーブル名です。 */
    public static final String PROJECT = PREFIX + "project";

    /** ビルドのテーブル名です。 */
    public static final String BUILD = PREFIX + "build";

    /** 関数のテーブル名です。 */
    public static final String FUNCTION = PREFIX + "function";

    /** グラフのテーブル名です。 */
    public static final String GRAPH = PREFIX + "graph";

    /** グラフサマリのテーブル名です。 */
    public static final String GRAPH_SUMMARY = PREFIX + "graphSummary";

    /** グラフブロックのテーブル名です。 */
    public static final String GRAPH_BLOCK = PREFIX + "graphBlock";

    /** グラフアークのテーブル名です。 */
    public static final String GRAPH_ARC = PREFIX + "graphArc";

    /**
       コンストラクタにはアクセスできません。
    */
    private Table() {
    }
}
