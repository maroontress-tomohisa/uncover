package com.maroontress.uncover;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
   リビジョンに関する情報をカプセル化します。
*/
public final class Revision {
    /** 関数のリストです。 */
    private List<Function> list;

    /** キーと関数のマップです。 */
    private Map<String, Function> keyMap;

    /** 複雑度ランキングのマップです。 */
    private Map<Function, Integer> complexityRankMap;

    /**
       インスタンスを生成します。

       @param list 関数のリスト
    */
    public Revision(final List<Function> list) {
        this.list = list;
        keyMap = new HashMap<String, Function>();
        complexityRankMap = new HashMap<Function, Integer>();

	for (Function function : list) {
            keyMap.put(function.getKey(), function);
	}

        Function[] array = createFunctionArray();
        Arrays.sort(array, Function.COMPLEXITY_COMPARATOR);
	RankMapFactory complexityRankMapFactory = new RankMapFactory() {
	    public int getIntValue(final Function function) {
                return function.getComplexity();
	    }
	};
	complexityRankMap = complexityRankMapFactory.create(array);
    }

    /**
       関数の数を取得します。

       @return 関数の数
    */
    public int getSize() {
	return list.size();
    }

    /**
       このリビジョンの関数のうち、比較対象のリビジョンに含まれないも
       のをリストで取得します。

       @param target 比較対象のリビジョン
       @return 比較対象のリビジョンに含まれない関数のリスト
    */
    public List<Function> createOuterFunctions(final Revision target) {
        List<Function> deltaList = new ArrayList<Function>();
        for (Function function : list) {
	    if (target.keyMap.get(function.getKey()) == null) {
                deltaList.add(function);
            }
        }
        return deltaList;
    }

    /**
       このリビジョンの関数のうち、比較対象のリビジョンにも含まれるも
       のを関数ペアのリストで取得します。

       リストの要素になる関数ペアは、このリビジョンに所属する関数が左、
       比較対象のリビジョンに所属する関数が右になります。

       @param target 比較対象のリビジョン
       @return 比較対象のリビジョンにも含まれる関数の関数ペアのリスト
    */
    public List<FunctionPair> createInnerFunctionPairs(final Revision target) {
        List<FunctionPair> pairList = new ArrayList<FunctionPair>();
        for (Function function : list) {
            Function targetFunction = target.keyMap.get(function.getKey());
            if (targetFunction == null) {
                continue;
            }
	    pairList.add(new FunctionPair(function, targetFunction));
        }
	return pairList;
    }

    /**
       このリビジョンの関数を配列で取得します。

       @return 関数の配列
    */
    public Function[] createFunctionArray() {
	return list.toArray(new Function[list.size()]);
    }

    /**
       このリビジョンにおける関数の複雑度ランクを取得します。

       @param key 関数のキー
       @return 複雑度ランク、キーに対応する関数が存在しない場合は0
    */
    public int getComplexityRank(final String key) {
	Function function = keyMap.get(key);
	if (function == null) {
	    return 0;
	}
	return complexityRankMap.get(function);
    }

    /**
       このリビジョンの関数を取得します。

       @param key 関数のキー
       @return 関数、キーに対応する関数が存在しない場合はnull
    */
    public Function getFunction(final String key) {
	return keyMap.get(key);
    }
}
