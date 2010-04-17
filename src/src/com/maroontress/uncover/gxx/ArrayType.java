package com.maroontress.uncover.gxx;

/**
   ����η��Ǥ���
*/
public final class ArrayType extends Type {
    /** */
    private Type elementType;

    /** */
    private int elementNum;

    /**
       ��������Ǥη��ȿ��ǥ��󥹥��󥹤��������ޤ���

       @param elementType ���Ǥη�
       @param elementNum ���ǿ�
    */
    public ArrayType(final Type elementType, final int elementNum) {
	this.elementType = elementType;
	this.elementNum = elementNum;
    }

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	elementType.export(b);
	if (hasQualifiers()) {
	    b.append(" (");
	    exportQualifiers(b);
	    b.append(")[");
	} else {
	    b.append(" [");
	}
	b.append(Integer.toString(elementNum));
	b.append("]");
    }
}
