package com.maroontress.uncover.gxx;

/**
   ���ѥ���ݡ��ͥ�ȤǤ���
*/
public final class ConstantComponent extends Component {
    /** ����ݡ��ͥ�Ȥ�̾���Ǥ��� */
    private CharSequence name;

    /**
       ���󥹥��󥹤��������ޤ���

       �����������󥹥��󥹤����ѥ��֥������ȤǤ���

       @param name ����ݡ��ͥ�Ȥ�̾��
    */
    public ConstantComponent(final String name) {
	this.name = name;
    }

    /** {@inheritDoc} */
    public void export(final Exporter b) {
	b.append(name);
    }
}
