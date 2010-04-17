package com.maroontress.uncover.gxx;

/**
   �ǥե���Ȥη��μ����Ǥ���
*/
public final class DefaultType extends Type {
    /** ����̾����ɽ�����󥹥��󥹤Ǥ��� */
    private TypeName name;

    /**
       ʸ����Ƿ�̾���������ޤ���

       @param name ��̾
    */
    public DefaultType(final String name) {
	this.name = new StringTypeName(name);
    }

    /**
       �������ݡ����֥�Ƿ�̾���������ޤ���

       @param name ��̾
    */
    public DefaultType(final Exportable name) {
	this.name = new ExportableTypeName(name);
    }

    /**
       ����ƥ����ȤǷ�̾���������ޤ���

       ����ƥ����ȤϷ�̾���Ϥޤ���֤ˤ���ɬ�פ�����ޤ���

       ����ƥ����ȤϷ�̾ʬ�ʤߤޤ���

       @param context ����ƥ�����
    */
    public DefaultType(final Context context) {
	name = new ExportableTypeName(context);
    }

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	name.exportName(b);
	exportQualifiers(b);
    }
}
