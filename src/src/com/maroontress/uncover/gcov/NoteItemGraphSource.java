package com.maroontress.uncover.gcov;

import com.maroontress.gcovparser.DefaultArc;
import com.maroontress.gcovparser.DefaultBlock;
import com.maroontress.uncover.Arc;
import com.maroontress.uncover.Block;
import com.maroontress.uncover.GraphSource;
import com.maroontress.uncover.Toolkit;
import java.util.ArrayList;
import java.util.List;

/**
   ノートアイテムをソースとするグラフソースです。
*/
public final class NoteItemGraphSource implements GraphSource {

    /** ツールキットです。 */
    private Toolkit tk;

    /** ノートアイテムです。 */
    private NoteItem item;

    /**
       インスタンスを生成します。
    */
    public NoteItemGraphSource() {
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
	Iterable<DefaultBlock> blocks = item.getBlocks();
	List<Block> blockList = new ArrayList<Block>();
	DefaultBlockSource src = new DefaultBlockSource();
	for (DefaultBlock block : blocks) {
	    src.setBlock(block);
	    blockList.add(tk.createBlock(src));
	}
	return blockList.toArray(new Block[blockList.size()]);
    }

    /** {@inheritDoc} */
    public Arc[] getAllArcs() {
	Iterable<DefaultArc> arcs = item.getArcs();
	List<Arc> arcList = new ArrayList<Arc>();
	DefaultArcSource src = new DefaultArcSource();
	for (DefaultArc arc : arcs) {
	    src.setArc(arc);
	    arcList.add(tk.createArc(src));
	}
	return arcList.toArray(new Arc[arcList.size()]);
    }
}
