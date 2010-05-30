package com.maroontress.uncover.gcov;

import com.maroontress.coverture.Arc;
import com.maroontress.coverture.Block;
import com.maroontress.uncover.FunctionSource;
import java.util.List;

/**
   ノートアイテムをソースとする関数ソースです。
*/
public final class GcovFunctionSource implements FunctionSource {

    /** ノートアイテムです。 */
    private NoteItem item;

    /** アークの総数です。 */
    private int allArcs;

    /** 実行済みアークの数です。 */
    private int executedArcs;

    /**
       インスタンスを生成します。
    */
    public GcovFunctionSource() {
    }

    /**
       ノートアイテムを設定します。

       @param item ノートアイテム
    */
    public void setNoteItem(final NoteItem item) {
	this.item = item;

        allArcs = 0;
        executedArcs = 0;
        Block[] blockArray = item.getBlocks();
	for (Block block : blockArray) {
	    List<Arc> arcList = block.getOutArcs();
	    for (Arc arc : arcList) {
		if (arc.isFake()) {
		    continue;
		}
		++allArcs;
		if (arc.getCount() > 0) {
		    ++executedArcs;
		}
	    }
	}
    }

    /** {@inheritDoc} */
    public String getCheckSum() {
	return String.format("0x%x", item.getChecksum());
    }

    /** {@inheritDoc} */
    public int getComplexity() {
	return item.getComplexity();
    }

    /** {@inheritDoc} */
    public String getName() {
	return item.getFunctionName();
    }

    /** {@inheritDoc} */
    public String getGCNOFile() {
	return item.getNote().getOrigin().getNoteFile().getPath();
    }

    /** {@inheritDoc} */
    public String getSourceFile() {
	return item.getSourceFile();
    }

    /** {@inheritDoc} */
    public int getLineNumber() {
	return item.getLineNumber();
    }

    /** {@inheritDoc} */
    public int getExecutedBlocks() {
	return item.getExecutedBlockCount();
    }

    /** {@inheritDoc} */
    public int getAllBlocks() {
	return item.getBlockCount();
    }

    /** {@inheritDoc} */
    public int getExecutedArcs() {
	return executedArcs;
    }

    /** {@inheritDoc} */
    public int getAllArcs() {
	return allArcs;
    }
}
