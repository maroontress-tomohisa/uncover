package com.maroontress.gcovparser;

import java.util.ArrayDeque;
import java.util.Queue;

/**
   �ե�����դ��褹�뤿��Υ���ФǤ���
*/
public final class Solver {
    /** �¹Բ����Ƚ�������Ȥ��˥֥�å����ɲä��륭�塼�Ǥ��� */
    private Queue<AbstractBlock> validBlocks;

    /** �¹Բ���������ʤȤ��˥֥�å����ɲä��륭�塼�Ǥ��� */
    private Queue<AbstractBlock> invalidBlocks;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public Solver() {
	validBlocks = new ArrayDeque<AbstractBlock>();
	invalidBlocks = new ArrayDeque<AbstractBlock>();
    }

    /**
       �¹Բ����Ƚ�������֥�å����ɲä��ޤ���

       @param b �¹Բ�������ΤΥ֥�å�
    */
    public void addValid(final AbstractBlock b) {
	validBlocks.add(b);
    }

    /**
       �¹Բ���������ʥ֥�å����ɲä��ޤ���

       @param b �¹Բ���������Υ֥�å�
    */
    public void addInvalid(final AbstractBlock b) {
	invalidBlocks.add(b);
    }

    /**
       �֥�å����ɲä��ޤ���

       @param b �֥�å�
       @param isValid �֥�å��μ¹Բ����Ƚ�����Ƥ������true
    */
    public void add(final AbstractBlock b, final boolean isValid) {
	(isValid ? validBlocks : invalidBlocks).add(b);
    }

    /**
       �ե�����դ�򤭤ޤ���

       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    public void solve() throws CorruptedFileException {
	int size = invalidBlocks.size();
	while (size > 0) {
	    AbstractBlock e;
	    while ((e = invalidBlocks.poll()) != null) {
		e.validate(this);
	    }
	    while ((e = validBlocks.poll()) != null) {
		e.validateSides(this);
	    }
	    int nextSize = invalidBlocks.size();
	    if (nextSize == size) {
		throw new CorruptedFileException("graph is unsolvable: "
						 + nextSize);
	    }
	    size = nextSize;
	}
    }
}
