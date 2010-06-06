package com.maroontress.uncover.gcov;

import com.maroontress.gcovparser.AbstractArc;
import com.maroontress.gcovparser.DefaultBlock;
import com.maroontress.uncover.FunctionSource;
import java.util.ArrayList;

/**
   �Ρ��ȥ����ƥ�򥽡����Ȥ���ؿ��������Ǥ���
*/
public final class NoteItemFunctionSource implements FunctionSource {

    /** �Ρ��ȥ����ƥ�Ǥ��� */
    private NoteItem item;

    /** ������������Ǥ��� */
    private int allArcs;

    /** �¹ԺѤߥ������ο��Ǥ��� */
    private int executedArcs;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public NoteItemFunctionSource() {
    }

    /**
       �Ρ��ȥ����ƥ�����ꤷ�ޤ���

       @param item �Ρ��ȥ����ƥ�
    */
    public void setNoteItem(final NoteItem item) {
	this.item = item;

        allArcs = 0;
        executedArcs = 0;
        Iterable<DefaultBlock> blocks = item.getBlocks();
	for (DefaultBlock block : blocks) {
	    ArrayList<? extends AbstractArc> arcList = block.getOutArcs();
 	    for (AbstractArc arc : arcList) {
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
