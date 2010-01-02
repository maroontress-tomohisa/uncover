package com.maroontress.uncover;

import java.util.Comparator;

/**
   �ؿ��ڥ�����������ͤ����������Ӥ��륳��ѥ졼���Ǥ���
*/
public abstract class FunctionPairComparator
    implements Comparator<FunctionPair> {
    /** {@inheritDoc} */
    public final int compare(final FunctionPair p1, final FunctionPair p2) {
	int d = -(getInt(p1) - getInt(p2));
	if (d != 0) {
	    return d;
	}
	return FunctionPair.compare(p1, p2);
    }

    /**
       �ؿ��ڥ�����������ͤ�������ޤ���

       @param p �ؿ��ڥ�
       @return ������
    */
    public abstract int getInt(FunctionPair p);
}
