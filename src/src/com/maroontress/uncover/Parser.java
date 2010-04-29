package com.maroontress.uncover;

import java.util.Iterator;

/**
   関数グラフイテレータを生成できるインタフェイスです。
*/
public interface Parser extends Iterable<FunctionGraph> {
    /**
       関数グラフイテレータを取得します。

       @return 関数グラフイテレータ
    */
    Iterator<FunctionGraph> iterator();
}
