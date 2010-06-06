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
   �ؿ�����դΥ��ƥ졼���Ǥ���
*/
public final class FunctionGraphIterator implements Iterator<FunctionGraph> {

    /** ���ϥե�����Υѥ�������Ǥ��� */
    private String[] files;

    /** ���˼��Ф����ǤΥ��ե��åȤǤ��� */
    private int offset;

    /** �ġ��륭�åȤΥ��󥹥��󥹤Ǥ��� */
    private Toolkit toolkit;

    /**
       �ؿ����������뤿��δؿ��������Ǥ���
    */
    private NoteItemFunctionSource functionSource;

    /**
       ����դ��������뤿��Υ���ե������Ǥ���
    */
    private NoteItemGraphSource graphSource;

    /**
       �ҤȤĤΥե�����˴ޤޤ��ؿ�����դ��ݻ����륭�塼�Ǥ���
    */
    private ArrayDeque<NoteItem> queue;

    /**
       Function�Υ��ƥ졼�����������ޤ���

       @param files ���ϥե�����Υѥ�������
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
