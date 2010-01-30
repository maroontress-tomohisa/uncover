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
    void commit(CommitSource source) throws DBException;

    /**
       Ʊ����ӥ����Υӥ�ɤ�������ޤ���

       @param projectName �ץ�������̾
       @param revision ��ӥ����̾
       @return �ӥ�ɤ�����
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    Build[] getBuilds(String projectName, String revision) throws DBException;

    /**
       �ӥ�ɤ�������ޤ���

       @param projectName �ץ�������̾
       @param id �ӥ��ID
       @return �ӥ��
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    Build getBuild(String projectName, String id) throws DBException;

    /**
       ��ӥ�����������ޤ���

       @param id �ӥ��ID
       @return ��ӥ����
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    Revision getRevision(String id) throws DBException;

    /**
       �ץ�������̾�������������ޤ���

       @return �ץ�������̾������
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    String[] getProjects() throws DBException;
}
