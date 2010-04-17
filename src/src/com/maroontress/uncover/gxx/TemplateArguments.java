package com.maroontress.uncover.gxx;

/**
*/
public abstract class TemplateArguments extends Exportable {
    /**
       @param context ����ƥ�����
       @return �ƥ�ץ졼�Ȱ���
    */
    public static TemplateArguments create(final Context context) {
	return new ListArguments(context);
    }

    /**
       @param args �ƥ�ץ졼�Ȱ�����ɽ��ʸ����
       @return �ƥ�ץ졼�Ȱ���
    */
    public static TemplateArguments create(final String args) {
	return new StringArguments(args);
    }

    /** {@inheritDoc} */
    @Override public final void export(final Exporter b) {
	char c = b.lastChar();
	if (!Character.isLetterOrDigit(c) && c != '_') {
	    b.append(" ");
	}
	b.append("<");
	exportArgs(b);
	b.append(">");
    }

    /**
       �ƥ�ץ졼�Ȱ�������Ȥ���Ϥ��ޤ���

       @param b �������ݡ���
    */
    protected abstract void exportArgs(final Exporter b);
}
