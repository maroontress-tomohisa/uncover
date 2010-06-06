package com.maroontress.uncover.gcov;

import com.maroontress.gcovparser.DefaultBlock;
import com.maroontress.gcovparser.LineEntry;
import com.maroontress.uncover.BlockSource;

/**
   com.maroontress.gcovparser.DefaultBlock�򥽡����Ȥ���֥�å�����
   ���Ǥ���
*/
public final class DefaultBlockSource implements BlockSource {

    /** �֥�å��Ǥ��� */
    private DefaultBlock block;

    /** */
    private String sourceFile;

    /** */
    private int lineNumber;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public DefaultBlockSource() {
    }

    /**
       �֥�å������ꤷ�ޤ���

       @param block �֥�å�
    */
    public void setBlock(final DefaultBlock block) {
	this.block = block;
	sourceFile = null;
	lineNumber = 0;

	LineEntry[] lines = block.getLines();
	if (lines == null || lines.length == 0) {
	    return;
	}
	LineEntry e = lines[0];
	String fileName = e.getFileName();
	int[] num = e.getLines();
	if (num.length == 0) {
	    return;
	}
	sourceFile = fileName;
	lineNumber = num[0];
    }

    /** {@inheritDoc} */
    public int getNumber() {
	return block.getId();
    }

    /** {@inheritDoc} */
    public long getCount() {
	return block.getCount();
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
