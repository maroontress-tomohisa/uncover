package com.maroontress.uncover.gcov;

import com.maroontress.gcovparser.AbstractFunctionGraph;
import com.maroontress.gcovparser.CorruptedFileException;
import com.maroontress.gcovparser.DefaultArc;
import com.maroontress.gcovparser.DefaultBlock;
import com.maroontress.gcovparser.gcno.FunctionGraphRecord;

/**
   �ؿ�����դǤ���
*/
public final class NoteItem
    extends AbstractFunctionGraph<DefaultBlock, DefaultArc> {

    /** �Ρ��ȤǤ��� */
    private Note note;

    /**
       �ؿ�����ե쥳���ɤ��饤�󥹥��󥹤��������ޤ���

       @param note �Ρ���
       @param rec �ؿ�����ե쥳����
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
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
       �Ρ��Ȥ�������ޤ���

       @return �Ρ���
    */
    public Note getNote() {
	return note;
    }
}
