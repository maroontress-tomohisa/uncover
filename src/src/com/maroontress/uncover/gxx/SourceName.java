package com.maroontress.uncover.gxx;

/**
   ̾�����ĥ���ݡ��ͥ�ȤǤ���
*/
public final class SourceName extends Component {
    /** ����ݡ��ͥ�Ȥ�̾���Ǥ��� */
    private CharSequence name;

    /**
       ���󥹥��󥹤��������ޤ���

       @param name ����ݡ��ͥ�Ȥ�̾��
    */
    public SourceName(final CharSequence name) {
	this.name = name;
    }

    /** {@inheritDoc} */
    public void export(final Exporter b) {
	b.append(name);
    }

    /** {@inheritDoc} */
    public void exportName(final Exporter b) {
	export(b);
    }
}
