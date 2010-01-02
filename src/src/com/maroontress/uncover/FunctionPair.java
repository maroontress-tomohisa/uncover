package com.maroontress.uncover;

import java.util.Comparator;

/**
   �ؿ��ڥ��Ǥ���
*/
public final class FunctionPair {
    /** ʣ���ٽ�˥����Ȥ��뤿��Υ���ѥ졼���Ǥ��� */
    public static final Comparator<FunctionPair> COMPLEXITY_DELTA_COMPARATOR;

    /**
       ���ܥ֥�å����κ�ʬ��˥����Ȥ��뤿��Υ���ѥ졼���Ǥ���
    */
    public static final Comparator<FunctionPair> ALL_BLOCKS_DELTA_COMPARATOR;

    /**
       ̤�¹Ԥδ��ܥ֥�å����κ�ʬ��˥����Ȥ��뤿��Υ���ѥ졼���Ǥ���
    */
    public static final Comparator<FunctionPair>
    UNEXECUTED_BLOCKS_DELTA_COMPARATOR;

    /** ���δؿ��Ǥ��� */
    private Function left;

    /** ���δؿ��Ǥ��� */
    private Function right;

    static {
	COMPLEXITY_DELTA_COMPARATOR = new FunctionPairComparator() {
	    public int getInt(final FunctionPair p) {
		return p.getComplexityDelta();
	    }
	};
	ALL_BLOCKS_DELTA_COMPARATOR = new FunctionPairComparator() {
	    public int getInt(final FunctionPair p) {
		return p.getAllBlocksDelta();
	    }
	};
	UNEXECUTED_BLOCKS_DELTA_COMPARATOR = new FunctionPairComparator() {
	    public int getInt(final FunctionPair p) {
		return p.getUnexecutedBlocksDelta();
	    }
	};
    }

    /**
       2�Ĥδؿ��ڥ���ǥե���Ȥ���������Ӥ��ޤ���

       @param p1 �ؿ��ڥ�
       @param p2 �ؿ��ڥ�
       @return p1 < p2�ʤ���顢p1 > p2�ʤ�����������Ǥʤ����0
    */
    public static int compare(final FunctionPair p1, final FunctionPair p2) {
	return Function.DEFAULT_COMPARATOR.compare(p1.right, p2.right);
    }

    /**
       ���󥹥��󥹤��������ޤ���

       @param left ���δؿ�
       @param right ���δؿ�
    */
    public FunctionPair(final Function left, final Function right) {
	this.left = left;
	this.right = right;
    }

    /**
       ���δؿ���������ޤ���

       @return ���δؿ�
    */
    public Function getLeft() {
	return left;
    }

    /**
       ���δؿ���������ޤ���

       @return ���δؿ�
    */
    public Function getRight() {
	return right;
    }

    /**
       ʣ���٤κ�ʬ��������ޤ���

       @return ʣ���٤κ�ʬ
    */
    public int getComplexityDelta() {
	return right.getComplexity() - left.getComplexity();
    }

    /**
       ���ܥ֥�å�����κ�ʬ��������ޤ���

       @return ���ܥ֥�å�����κ�ʬ
    */
    public int getAllBlocksDelta() {
	return right.getAllBlocks() - left.getAllBlocks();
    }

    /**
       ̤�¹Ԥδ��ܥ֥�å�����κ�ʬ��������ޤ���

       @return ̤�¹Ԥδ��ܥ֥�å�����κ�ʬ
    */
    public int getUnexecutedBlocksDelta() {
	return right.getUnexecutedBlocks() - left.getUnexecutedBlocks();
    }
}
