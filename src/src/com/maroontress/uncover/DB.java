package com.maroontress.uncover;

/**
   �ǡ����١������Υ��󥿥ե������Ǥ���
*/
public interface DB {
    /**
       �ǡ����١������������ޤ���

       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    void initialize() throws DBException;

    /**
       �ǡ����١�������³�򥯥������ޤ���

       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    void close() throws DBException;

    /**
       �ǡ����١����˴ؿ��������Ͽ���ޤ���

       @param source ��Ͽ�������Υ�����
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    void commit(final CommitSource source) throws DBException;

    /**
       �ǡ����١��������ӥ�����������ޤ���

       @param projectName �ץ�������̾
       @param revision ��ӥ����̾
       @return ��ӥ����
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    Revision getRevision(final String projectName, final String revision)
	throws DBException;
}
