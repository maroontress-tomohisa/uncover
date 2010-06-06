package com.maroontress.gcovparser;

/**
   �ؿ�����դΥ���������ݥ��饹�Ǥ���
*/
public abstract class AbstractArc {

    /**
       ���������������ޤ���
    */
    protected AbstractArc() {
    }

    /**
       �������ν�λ�֥�å���������ޤ���

       @return ��λ�֥�å�
    */
    public abstract AbstractBlock getEnd();

    /**
       ��������ʬ�����ʤ��ä���ϩ�Ǥ��뤫�ɤ����������ޤ���

       @return ��������ʬ�����ʤ��ä���ϩ�ξ���true�������Ǥʤ����
       false
    */
    public abstract boolean isFallThrough();

    /**
       ��������exit()�ʤɤΤ褦�����ʤ��ؿ��θƤӽФ��Ǥ��뤫�ɤ���
       �������ޤ���

       @return ���ʤ��ؿ��θƤӽФ��ξ���true�������Ǥʤ����false
    */
    public abstract boolean isCallNonReturn();

    /**
       �������ιԤ��褬catch�ޤ���setjmp()�Ǥ��뤫�ɤ�����������ޤ���

       @return �������ιԤ��褬catch�ޤ���setjmp()�Ǥ������true����
       ���Ǥʤ����false
    */
    public abstract boolean isNonLocalReturn();

    /**
       ̵���ʬ�����ɤ�����������ޤ���

       @return ̵���ʬ���ʤ�true�������Ǥʤ����false
    */
    public abstract boolean isUnconditional();

    /**
       �����������ѥ˥󥰥ĥ꡼�������뤫�ɤ����������ޤ���

       @return ���ѥ˥󥰥ĥ꡼�����������true�������Ǥʤ����
       false
    */
    public abstract boolean isOnTree();

    /**
       �����������Υ��������ɤ����������ޤ���

       @return ���Υ������ξ���true�������Ǥʤ����false
    */
    public abstract boolean isFake();

    /**
       �¹Բ�����ɲä��ޤ���

       @param delta �ɲä���¹Բ��
    */
    public abstract void addCount(long delta);

    /**
       �¹Բ����������ޤ���

       @return �¹Բ��
    */
    public abstract long getCount();
}
