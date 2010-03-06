package com.maroontress.uncover;

/**
   ����դ���������Τ�ɬ�פʾ�����󶡤��륤�󥿥ե������Ǥ���
*/
public interface GraphSource {
    /**
       �ؿ�̾��������ޤ���

       @return �ؿ�̾
    */
    String getName();

    /**
       gcno�ե�����̾��������ޤ���

       @return gcno�ե�����̾
    */
    String getGCNOFile();

    /**
       ���ܥ֥�å��������������ޤ���

       ��������������ѹ����Ƥ⡢���󥹥��󥹤Ϥ��αƶ�������ޤ���

       @return ���ܥ֥�å�������
    */
    Block[] getAllBlocks();

    /**
       �������������������ޤ���

       ��������������ѹ����Ƥ⡢���󥹥��󥹤Ϥ��αƶ�������ޤ���

       @return ������������
    */
    Arc[] getAllArcs();
}
