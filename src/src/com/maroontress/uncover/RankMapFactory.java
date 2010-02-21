package com.maroontress.uncover;

import java.util.HashMap;
import java.util.Map;

/**
   関数とランクのマップを生成する機能を提供します。
*/
public abstract class RankMapFactory {
    /**
       インスタンスを生成します。
    */
    protected RankMapFactory() {
    }

    /**
       関数の整数値順にランキングを求め、関数とランキングのマップを生
       成します。

       @param array 関数の配列（整数値順にソート済み）
       @return 関数とランキングのマップ
    */
    public final Map<Function, Integer> create(final Function[] array) {
        Map<Function, Integer> map = new HashMap<Function, Integer>();
        int rank = 1;
        int n = array.length;
        int lastValue = getIntValue(array[0]);

        map.put(array[0], rank);
        for (int k = 1; k < n; ++k) {
            int value = getIntValue(array[k]);
            if  (lastValue != value) {
                lastValue = value;
                rank = k + 1;
            }
            map.put(array[k], rank);
        }
	return map;
    }

    /**
       関数から整数値を取得するインタフェイスです。

       @param function 関数
       @return 整数値
    */
    public abstract int getIntValue(Function function);
}
