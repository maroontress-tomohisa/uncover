package com.maroontress.uncover.gcov;

import com.maroontress.coverture.Block;
import com.maroontress.uncover.BlockSource;

/**
   com.maroontress.coverture.Blockをソースとするブロックソースです。
*/
public final class GcovBlockSource implements BlockSource {

    /** ブロックです。 */
    private Block block;

    /**
       インスタンスを生成します。
    */
    public GcovBlockSource() {
    }

    /**
       ブロックを設定します。

       @param block ブロック
    */
    public void setBlock(final Block block) {
	this.block = block;
    }

    /** {@inheritDoc} */
    public int getNumber() {
	return block.getId();
    }

    /** {@inheritDoc} */
    public long getCount() {
	return block.getCount();
    }

    /** {@inheritDoc} */
    public String getSourceFile() {
	return null;
    }

    /** {@inheritDoc} */
    public int getLineNumber() {
	return 0;
    }
}
