package com.maroontress.uncover.gcov;

import com.maroontress.coverture.Block;
import com.maroontress.uncover.BlockSource;

/**
   com.maroontress.coverture.Block�򥽡����Ȥ���֥�å��������Ǥ���
*/
public final class GcovBlockSource implements BlockSource {

    /** �֥�å��Ǥ��� */
    private Block block;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public GcovBlockSource() {
    }

    /**
       �֥�å������ꤷ�ޤ���

       @param block �֥�å�
    */
    public void setBlock(final Block block) {
	this.block = block;
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
	return null;
    }

    /** {@inheritDoc} */
    public int getLineNumber() {
	return 0;
    }
}
