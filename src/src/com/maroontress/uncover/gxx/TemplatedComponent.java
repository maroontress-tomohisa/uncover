package com.maroontress.uncover.gxx;

/**
   �ƥ�ץ졼�Ȱ����դ�����ݡ��ͥ�ȤǤ���
*/
public final class TemplatedComponent extends Component {
    /** �������ݡ����֥�Ǥ��� */
    private Exportable name;

    /** �������ݡ����֥�Υƥ�ץ졼�Ȱ����Ǥ��� */
    private TemplateArguments args;

    /**
       ���󥹥��󥹤��������ޤ���

       @param name �������ݡ����֥�
       @param args �ƥ�ץ졼�Ȱ���
    */
    private TemplatedComponent(final Exportable name,
			       final TemplateArguments args) {
	this.name = name;
	this.args = args;
    }

    /**
       ���󥹥��󥹤��������ޤ���

       @param name �������ݡ����֥�
       @param context ����ƥ�����
    */
    public TemplatedComponent(final Exportable name, final Context context) {
	this(name, TemplateArguments.create(context));
    }

    /**
       ���󥹥��󥹤��������ޤ���

       @param name �������ݡ����֥�
       @param args ����
    */
    public TemplatedComponent(final Exportable name, final String args) {
	this(name, TemplateArguments.create(args));
    }

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	name.export(b);
	args.export(b);
    }

    /** {@inheritDoc} */
    @Override public void exportName(final Exporter b) {
	name.export(b);
    }
}
