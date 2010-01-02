package com.maroontress.uncover;

/**
   �ؿ�����������Τ�ɬ�פʾ�����󶡤��륤�󥿥ե������Ǥ���
*/
public interface FunctionSource {
    /**
       �����å������������ޤ���

       @return �����å�����
    */
    String getCheckSum();

    /**
       ʣ���٤�������ޤ���

       @return ʣ����
    */
    int getComplexity();

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
       �������ե�����̾��������ޤ���

       @return �������ե�����̾
    */
    String getSourceFile();

    /**
       �������ե�����ǽи�������ֹ��������ޤ���

       @return �������ե�����ǽи�������ֹ�
    */
    int getLineNumber();

    /**
       �¹ԺѤߤδ��ܥ֥�å�����������ޤ���

       @return �¹ԺѤߤδ��ܥ֥�å���
    */
    int getExecutedBlocks();

    /**
       ���ܥ֥�å������������ޤ���

       @return ���ܥ֥�å����
    */
    int getAllBlocks();

    /**
       �¹ԺѤߤΥ���������������ޤ���

       @return �¹ԺѤߤΥ�������
    */
    int getExecutedArcs();

    /**
       �����������������ޤ���

       @return ���������
    */
    int getAllArcs();
}
