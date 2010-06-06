package com.maroontress.uncover.gcov;

import com.maroontress.uncover.Function;
import com.maroontress.uncover.FunctionGraph;
import com.maroontress.uncover.Graph;
import com.maroontress.uncover.Toolkit;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
   関数グラフのイテレータです。
*/
public final class FunctionGraphIterator implements Iterator<FunctionGraph> {

    /** 入力ファイルのパスの配列です。 */
    private String[] files;

    /** 次に取り出す要素のオフセットです。 */
    private int offset;

    /** ツールキットのインスタンスです。 */
    private Toolkit toolkit;

    /**
       関数を生成するための関数ソースです。
    */
    private NoteItemFunctionSource functionSource;

    /**
       グラフを生成するためのグラフソースです。
    */
    private NoteItemGraphSource graphSource;

    /**
       ひとつのファイルに含まれる関数グラフを保持するキューです。
    */
    private ArrayDeque<NoteItem> queue;

    /**
       Functionのイテレータを生成します。

       @param files 入力ファイルのパスの配列
    */
    public FunctionGraphIterator(final String[] files) {
	this.files = files;
	offset = 0;
	toolkit = Toolkit.getInstance();
	functionSource = new NoteItemFunctionSource();
	graphSource = new NoteItemGraphSource();
	queue = new ArrayDeque<NoteItem>();
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
	while (queue.isEmpty()) {
	    Note note = null;
	    do {
		if (offset == files.length) {
		    return false;
		}
		String file = files[offset];
		++offset;

		try {
		    note = Note.parse(file);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    } while (note == null);
	    queue.addAll(note.getNoteItems());
	}
	return true;
    }

    /** {@inheritDoc} */
    public FunctionGraph next() {
	NoteItem item = queue.poll();
	if (item == null) {
	    throw new NoSuchElementException();
	}
	functionSource.setNoteItem(item);
	Function function = toolkit.createFunction(functionSource);
	graphSource.setNoteItem(item);
	Graph graph = toolkit.createGraph(graphSource);
	return new FunctionGraph(function, graph);
    }

    /** {@inheritDoc} */
    public void remove() {
	throw new UnsupportedOperationException();
    }
}
