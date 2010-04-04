package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.BlockSource;

/**
   �������̤���֥�å����������뤿��Υ֥�å��������Ǥ���
*/
public final class ResultSetBlockSource extends Row implements BlockSource {
    /** ���ܥ֥�å����ֹ�Ǥ��� */
    private int number;

    /** �¹Բ���Ǥ��� */
    private long count;

    /** �������ե������̾���Ǥ��� */
    private String sourceFile;

    /**
       �������ե�����Ǥι��ֹ�Ǥ����֥�å���ʣ���Ԥˤޤ��������
       ������Ƭ�Ԥˤʤ�ޤ���
    */
    private int lineNumber;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public ResultSetBlockSource() {
    }

    /** {@inheritDoc} */
    public int getNumber() {
        return number;
    }

    /** {@inheritDoc} */
    public long getCount() {
        return count;
    }

    /** {@inheritDoc} */
    public String getSourceFile() {
        return sourceFile;
    }

    /** {@inheritDoc} */
    public int getLineNumber() {
        return lineNumber;
    }
}
