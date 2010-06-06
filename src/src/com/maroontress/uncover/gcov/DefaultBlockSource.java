package com.maroontress.uncover.gcov;

import com.maroontress.gcovparser.DefaultBlock;
import com.maroontress.gcovparser.LineEntry;
import com.maroontress.uncover.BlockSource;

/**
   com.maroontress.gcovparser.DefaultBlockをソースとするブロックソー
   スです。
*/
public final class DefaultBlockSource implements BlockSource {

    /** ブロックです。 */
    private DefaultBlock block;

    /** */
    private String sourceFile;

    /** */
    private int lineNumber;

    /**
       インスタンスを生成します。
    */
    public DefaultBlockSource() {
    }

    /**
       ブロックを設定します。

       @param block ブロック
    */
    public void setBlock(final DefaultBlock block) {
	this.block = block;
	sourceFile = null;
	lineNumber = 0;

	LineEntry[] lines = block.getLines();
	if (lines == null) {
	    return;
	}
	for (LineEntry e : lines) {
	    String fileName = e.getFileName();
	    int[] nums = e.getLines();
	    if (nums.length == 0) {
		continue;
	    }
	    sourceFile = fileName;
	    lineNumber = nums[0];
	    return;
	}
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
	return sourceFile;
    }

    /** {@inheritDoc} */
    public int getLineNumber() {
	return lineNumber;
    }
}
