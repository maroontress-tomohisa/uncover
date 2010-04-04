package com.maroontress.uncover;

/**
   アークを生成するのに必要な情報を提供するインタフェイスです。
*/
public interface ArcSource {
    /**
       アークの始点となる基本ブロックの番号を取得します。

       @return アークの始点となる基本ブロックの番号
    */
    int getStart();

    /**
       アークの終点となる基本ブロックの番号を取得します。

       @return アークの終点となる基本ブロックの番号
    */
    int getEnd();

    /**
       実行回数を取得します。

       @return 実行回数
    */
    long getCount();

    /**
       フェイクかどうかを取得します。

       @return アークがフェイクである場合はtrue
    */
    boolean isFake();
}
