package com.maroontress.uncover.gxx;

/**
   �������ݡ����֥�ˤ�뷿̾�Ǥ���
*/
public final class ExportableTypeName implements TypeName {
    /** ����̾���Ǥ��� */
    private Exportable name;

    /**
       ���󥹥��󥹤��������ޤ���

       @param name ��̾
    */
    public ExportableTypeName(final Exportable name) {
	this.name = name;
    }

    /**
       ���󥹥��󥹤��������ޤ���

       ����ƥ����Ȥ�ľ����ʸ����S�Ǥ�����Substitution.parse()����Ѥ�
       �Ʒ�̾��������ޤ���

       @param context ����ƥ�����
    */
    public ExportableTypeName(final Context context) {
	Substitution.parse(context, new SubstitutionListener() {
	    public void templeateFound(final Context context) {
		name = Composite.newSubstitutionTemplate(context);
	    }
	    public void standardPrefixFound(final Context context,
					    final Composite prefix) {
		name = prefix;
	    }
	    public void substitutionFound(final Exportable e) {
		name = e;
	    }
	});
    }

    /** {@inheritDoc} */
    public void exportName(final Exporter b) {
	name.export(b);
    }
}
