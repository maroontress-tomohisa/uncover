package com.maroontress.uncover.gxx;

/**
*/
public final class StringArguments extends TemplateArguments {
    /** */
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
