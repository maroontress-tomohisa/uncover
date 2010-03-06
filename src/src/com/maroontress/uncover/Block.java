package com.maroontress.uncover;

/**
   ���ܥ֥�å��˴ؤ������򥫥ץ��벽���ޤ���
*/
public final class Block implements BlockSource {
    /** ���ܥ֥�å����ֹ�Ǥ��� */
    private int number;

    /** �¹Բ���Ǥ��� */
    private int count;

    /** �������ե������̾���Ǥ��� */
    private String sourceFile;

    /**
       �������ե�����Ǥι��ֹ�Ǥ����֥�å���ʣ���Ԥˤޤ��������
       ������Ƭ�Ԥˤʤ�ޤ���
    */
    private int lineNumber;

    /**
       �ǥե���ȥ��󥹥ȥ饯���ϻ��ѤǤ��ޤ���
    */
    private Block() {
    }

    /**
       ���󥹥��󥹤��������ޤ���

       @param s �֥�å�������
    */
    public Block(final BlockSource s) {
	number = s.getNumber();
	count = s.getCount();
	sourceFile = s.getSourceFile();
	if (sourceFile != null) {
	    sourceFile = sourceFile.intern();
	}
	lineNumber = s.getLineNumber();
    }

    /** {@inheritDoc} */
    public int getNumber() {
	return number;
    }

    /** {@inheritDoc} */
    public int getCount() {
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
