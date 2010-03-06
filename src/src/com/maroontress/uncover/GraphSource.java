package com.maroontress.uncover;

/**
   グラフを生成するのに必要な情報を提供するインタフェイスです。
*/
public interface GraphSource {
    /**
       関数名を取得します。

       @return 関数名
    */
    String getName();

    /**
       gcnoファイル名を取得します。

       @return gcnoファイル名
    */
    String getGCNOFile();

    /**
       基本ブロックの配列を取得します。

       取得した配列を変更しても、インスタンスはその影響を受けません。

       @return 基本ブロックの配列
    */
    Block[] getAllBlocks();

    /**
       アークの配列を取得します。

       取得した配列を変更しても、インスタンスはその影響を受けません。

       @return アークの配列
    */
    Arc[] getAllArcs();
}
