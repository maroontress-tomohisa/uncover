package com.maroontress.uncover.gxx;

/**
   ʸ����ˤ�뷿̾�Ǥ���
*/
public final class StringTypeName implements TypeName {
    /** ����̾���Ǥ��� */
    private String name;

    /**
       ���󥹥��󥹤��������ޤ���

       @param name ��̾
    */
    public StringTypeName(final String name) {
	this.name = name;
    }

    /** {@inheritDoc} */
    public void exportName(final Exporter b) {
	b.append(name);
    }
}
