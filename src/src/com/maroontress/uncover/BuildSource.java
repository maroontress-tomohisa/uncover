package com.maroontress.uncover;

/**
   �ӥ�ɤ���������Τ�ɬ�פʾ�����󶡤��륤�󥿥ե������Ǥ���
*/
public interface BuildSource {
    /**
       ID��������ޤ���

       @return ID
    */
    String getID();

    /**
       ��ӥ�����������ޤ���

       @return ��ӥ����
    */
    String getRevision();

    /**
       �����ॹ����פ�������ޤ���

       @return �����ॹ�����
    */
    String getTimestamp();

    /**
       �ץ�åȥե������������ޤ���

       @return �ץ�åȥե�����
    */
    String getPlatform();
}
