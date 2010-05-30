package com.maroontress.uncover.gcov;

import com.maroontress.uncover.Arc;
import com.maroontress.uncover.Block;
import com.maroontress.uncover.GraphSource;
import com.maroontress.uncover.Toolkit;
import java.util.ArrayList;
import java.util.List;

/**
   ノートアイテムをソースとするグラフソースです。
*/
public final class GcovGraphSource implements GraphSource {

    /** ツールキットです。 */
    private Toolkit tk;

    /** ノートアイテムです。 */
    private NoteItem item;

    /**
       インスタンスを生成します。
    */
    public GcovGraphSource() {
	tk = Toolkit.getInstance();
    }

    /**
       ノートアイテムを設定します。

       @param item ノートアイテム
    */
    public void setNoteItem(final NoteItem item) {
	this.item = item;
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
    public Block[] getAllBlocks() {
	com.maroontress.coverture.Block[] blockArray = item.getBlocks();
	List<Block> blockList = new ArrayList<Block>();
	GcovBlockSource src = new GcovBlockSource();
	for (com.maroontress.coverture.Block block : blockArray) {
	    src.setBlock(block);
	    blockList.add(tk.createBlock(src));
	}
	return blockList.toArray(new Block[blockList.size()]);
    }

    /** {@inheritDoc} */
    public Arc[] getAllArcs() {
	com.maroontress.coverture.Arc[] arcArray = item.getArcs();
	List<Arc> arcList = new ArrayList<Arc>();
	GcovArcSource src = new GcovArcSource();
	for (com.maroontress.coverture.Arc arc : arcArray) {
	    src.setArc(arc);
	    arcList.add(tk.createArc(src));
	}
	return arcList.toArray(new Arc[arcList.size()]);
    }
}
