package com.maroontress.uncover;

import java.util.Arrays;

/**
   グラフに関する情報をカプセル化します。
*/
public final class Graph implements GraphSource {
    /** 関数名です。 */
    private String name;

    /** 由来するgcnoファイルです。 */
    private String gcnoFile;

    /** すべての基本ブロックです。 */
    private Block[] allBlocks;

    /** すべてのアークです。 */
    private Arc[] allArcs;

    /**
       インスタンスを生成します。

       @param s グラフソース
    */
    public Graph(final GraphSource s) {
	name = s.getName();
	gcnoFile = s.getGCNOFile().intern();
	allBlocks = s.getAllBlocks();
	allArcs = s.getAllArcs();
    }

    /** {@inheritDoc} */
    public String getName() {
	return name;
    }

    /** {@inheritDoc} */
    public String getGCNOFile() {
	return gcnoFile;
    }

    /** {@inheritDoc} */
    public Block[] getAllBlocks() {
	return Arrays.copyOf(allBlocks, allBlocks.length);
    }

    /** {@inheritDoc} */
    public Arc[] getAllArcs() {
	return Arrays.copyOf(allArcs, allArcs.length);
    }
}
