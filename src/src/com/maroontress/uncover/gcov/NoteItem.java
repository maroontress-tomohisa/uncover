package com.maroontress.uncover.gcov;

import com.maroontress.gcovparser.AbstractFunctionGraph;
import com.maroontress.gcovparser.CorruptedFileException;
import com.maroontress.gcovparser.DefaultArc;
import com.maroontress.gcovparser.DefaultBlock;
import com.maroontress.gcovparser.gcno.FunctionGraphRecord;

/**
   関数グラフです。
*/
public final class NoteItem
    extends AbstractFunctionGraph<DefaultBlock, DefaultArc> {

    /** ノートです。 */
    private Note note;

    /**
       関数グラフレコードからインスタンスを生成します。

       @param note ノート
       @param rec 関数グラフレコード
       @throws CorruptedFileException ファイルの構造が壊れていることを検出
    */
    public NoteItem(final Note note, final FunctionGraphRecord rec)
	throws CorruptedFileException {
	super(rec);
	this.note = note;
    }

    /** {@inheritDoc} */
    @Override protected DefaultBlock createBlock(final int id,
						 final int blockFlags) {
        return new DefaultBlock(id, blockFlags);
    }

    /** {@inheritDoc} */
    @Override protected DefaultArc createArc(final DefaultBlock start,
                                             final DefaultBlock end,
                                             final int flags) {
        return new DefaultArc(start, end, flags);
    }

    /**
       ノートを取得します。

       @return ノート
    */
    public Note getNote() {
	return note;
    }
}
