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
       ���󥹥��󥹤Υ��ԡ����������ޤ���

       @return ���󥹥��󥹤Υ��ԡ�
    */
    public final Exportable createCopy() {
	/*
	  �Ѷ�Ū��Object.clone()��Ȥ������櫓�ǤϤʤ���Checkstyle��
	  DesignForExtension���������Τ��񤷤������Υ������Ǥ�clone()
	  ������ͤ�ºݤΥ��饹�˥��㥹�Ȥ���ɬ�פ�ʤ���
	*/
	return new SourceName(toString());
    }
}
