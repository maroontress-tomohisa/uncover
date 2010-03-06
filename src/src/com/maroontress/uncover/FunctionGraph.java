package com.maroontress.uncover;

/**
   関数グラフに関する情報をカプセル化します。
*/
public final class FunctionGraph {
    /** 関数です。 */
    private Function function;

    /** グラフです。 */
    private Graph graph;

    /**
       インスタンスを生成します。

       @param function 関数
       @param graph グラフ
    */
    public FunctionGraph(final Function function, final Graph graph) {
	this.function = function;
	this.graph = graph;
    }

    /**
       関数を取得します。

       @return 関数
    */
    public Function getFunction() {
	return function;
    }

    /**
       グラフを取得します。

       @return グラフ
    */
    public Graph getGraph() {
	return graph;
    }
}
