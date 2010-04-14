package com.maroontress.uncover.gxx;

/**
   �������ݡ����֥�ν�����Ǥ���
*/
public final class Exporter {
    /** �ºݤν�����Ȥʤ�ʸ����ӥ���Ǥ��� */
    private StringBuilder builder;

    /** �ǽ�����ݡ��ͥ�ȤǤ��� */
    private Component component;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public Exporter() {
	builder = new StringBuilder();
    }

    /**
       �ǽ�����ݡ��ͥ�Ȥ����ꤷ�ޤ���

       @param component ����ݡ��ͥ��
    */
    public void setComponent(final Component component) {
	this.component = component;
    }

    /**
       �ǽ�����ݡ��ͥ�Ȥ���Ϥ��ޤ���
    */
    public void appendComponent() {
	component.export(this);
    }

    /**
       ʸ���󥷡����󥹤���Ϥ��ޤ���

       @param seq ʸ���󥷡�����
       @return ���Υ��󥹥���
    */
    public Exporter append(final CharSequence seq) {
	builder.append(seq);
	return this;
    }

    /**
       ���Ϥ�ʸ����Ȥ��Ƽ������ޤ���

       @return ʸ����
    */
    public String toString() {
	return builder.toString();
    }

    /**
       ���Ϥ��줿�Ǹ��ʸ����������ޤ���

       @return ���Ϥ��줿�Ǹ��ʸ��
    */
    public char lastChar() {
	return builder.charAt(builder.length() - 1);
    }
}
