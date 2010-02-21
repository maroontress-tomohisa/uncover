package com.maroontress.uncover.html;

import com.maroontress.uncover.Function;
import com.maroontress.uncover.FunctionPair;

/**
   �ؿ��λ�ɸ�Ѳ��η����亹ʬ��ɽ��ʸ������������뤿��ε�ǽ���󶡤��ޤ���
*/
public abstract class Arrow {
    /**
       ���󥹥��󥹤��������ޤ���
    */
    protected Arrow() {
    }

    /**
       �ؿ��λ�ɸ�Ѳ��η����亹ʬ��ɽ��ʸ�����������ޤ���

       @param v1 ������
       @param v2 ������
       @return ��ɸ�Ѳ���ɽ��ʸ����
    */
    protected abstract String get(int v1, int v2);

    /**
       ���ܥ֥�å������Ѳ���ɽ��ʸ�����������ޤ���

       @param pair �ؿ��ڥ�
       @return ��ɸ�Ѳ���ɽ��ʸ����
    */
    public final String allBlocks(final FunctionPair pair) {
	return get(pair.getLeft().getAllBlocks(),
		   pair.getRight().getAllBlocks());
    }

    /**
       ʣ���٤��Ѳ���ɽ��ʸ�����������ޤ���

       @param pair �ؿ��ڥ�
       @return ��ɸ�Ѳ���ɽ��ʸ����
    */
    public final String complexity(final FunctionPair pair) {
        return complexity(pair.getLeft(), pair.getRight());
    }

    /**
       ʣ���٤��Ѳ���ɽ��ʸ�����������ޤ���

       @param left ���δؿ�
       @param right ���δؿ�
       @return ��ɸ�Ѳ���ɽ��ʸ����
    */
    public final String complexity(final Function left, final Function right) {
        return get(left.getComplexity(), right.getComplexity());
    }

    /**
       ̤�¹Ԥδ��ܥ֥�å������Ѳ���ɽ��ʸ�����������ޤ���

       @param pair �ؿ��ڥ�
       @return ��ɸ�Ѳ���ɽ��ʸ����
    */
    public final String unexecutedBlocks(final FunctionPair pair) {
        return get(pair.getLeft().getUnexecutedBlocks(),
		   pair.getRight().getUnexecutedBlocks());
    }

    /**
       ��̤��Ѳ���ɽ��ʸ�����������ޤ���

       @param v1 ���ν��
       @param v2 ���ν��
       @return ��ɸ�Ѳ���ɽ��ʸ����
    */
    public final String rank(final int v1, final int v2) {
	return get(v1, v2);
    }
}
