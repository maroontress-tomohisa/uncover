package com.maroontress.uncover.gxx;

/**
   ʸ����Ǽ¸������ƥ�ץ졼�Ȱ����Ǥ���
*/
public final class StringArguments extends TemplateArguments {
    /** �ƥ�ץ졼�Ȱ�����ɽ��ʸ����Ǥ��� */
    private String args;

    /**
       ���󥹥��󥹤��������ޤ���

       @param args ����
    */
    public StringArguments(final String args) {
	this.args = args;
    }

    /** {@inheritDoc} */
    @Override public void exportArgs(final Exporter b) {
	b.append(args);
    }
}
