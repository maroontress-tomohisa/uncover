package com.maroontress.gcovparser;

import java.util.ArrayList;

/**
   �ؿ�����դΥΡ��ɤȤʤ���ܥ֥�å�����ݥ��饹�Ǥ���
*/
public abstract class AbstractBlock {

    /**
       �֥�å����������ޤ���
    */
    protected AbstractBlock() {
    }

    /**
       ������Ȥ�������ޤ���������Ȥ�ͭ���Ǥʤ��Ȥ���0���֤��ޤ���

       @return �������
    */
    public abstract long getCount();

    /**
       ���Υ֥�å��ιԥ���ȥ����������ꤷ�ޤ���

       @param lines �ԥ���ȥ������
    */
    public abstract void setLines(LineEntry[] lines);

    /**
       ���̻Ҥ�������ޤ���

       @return ���̻�
    */
    public abstract int getId();

    /**
       �����륢�����פΥꥹ�Ȥ�������ޤ���

       @return �����륢�����פΥꥹ��
    */
    public abstract ArrayList<? extends AbstractArc> getInArcs();

    /**
       �ֽФ륢�����פΥꥹ�Ȥ�������ޤ���

       @return �ֽФ륢�����פΥꥹ��
    */
    public abstract ArrayList<? extends AbstractArc> getOutArcs();

    /**
       �ե�����դ�򤯤���ν����򤷤ޤ���

       �֥�å�����Ф뵶�Ǥʤ���������1�Ĥ����ʤ���硢���Υ�������̵
       ���ʬ�������ꤷ�ޤ�������ˡ����Υ�����������֥�å����ָƤ�
       �Ф���������פǤ��뤫�ɤ��������ꤷ�ޤ���
    */
    public abstract void presolve();

    /**
       �ֽФ륢�����פΥꥹ�Ȥ򤽤ν�λ�֥�å��μ��̻ҽ�˥����Ȥ���
       ����
    */
    public abstract void sortOutArcs();

    /**
       �¹Բ������ޤ���

       �����륢�����פμ¹Բ�������٤Ƶ�ޤäƤ��뤫���ֽФ륢������
       �μ¹Բ�������٤Ƶ�ޤäƤ����硢���������¤�֥�å��μ�
       �Բ���Ȥ��Ʒ׻������֥�å��򥽥�Ф��ɲä��ޤ���

       �����륢�����פȡֽФ륢�����פΤɤ����¹Բ������ޤäƤ���
       �����ϲ��⤷�ޤ���

       @param s �ե�����ե����
    */
    public abstract void validate(Solver s);

    /**
       �֥�å������륢�������Ф륢�������줾��ˤĤ��ơ��¹Բ������
       ���ʤ�Τ�1�Ĥ����ʤ顢����μ¹Բ������ޤ���

       �֥�å��ϴ��˼¹Բ����Ƚ�����Ƥ���ɬ�פ�����ޤ���

       @param s �ե�����ե����
    */
    public abstract void validateSides(Solver s);
}
