package com.maroontress.uncover.gxx;

/**
   ̾����������ݴ��쥯�饹�Ǥ����������ݡ����˼�ʬ���Ȥ���Ϥ��뵡ǽ
   ���󶡤��ޤ���
*/
public abstract class Exportable {
    /** {@inheritDoc} */
    public final String toString() {
	Exporter b = new Exporter();
	export(b);
	return b.toString();
    }

    /**
       �������ݡ����˽��Ϥ��ޤ���

       @param b �������ݡ���
    */
    public abstract void export(Exporter b);

    /**
       ���󥹥��󥹤�ʣ�����ޤ���

       clone()�Ȥ���̾���Ϥ��롣createCopy()�Ȥ��ˤ��롣

       S[0-9A-Z]*_�Τ��Ȥ�I��³���ʤ����Ȥ��μ¤ʤ顢����ͤη�̾��
       ConstantComponent�������褤���ɡ�

       TemplatedComponent�����ѤǤϤʤ��Τ�������

       @return ʣ���������󥹥���
    */
    public final Exportable clone() {
	return TemplatedComponent.create(this.toString());
    }
}
