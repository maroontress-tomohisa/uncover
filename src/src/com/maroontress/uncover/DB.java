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
    String[] getProjectNames() throws DBException;

    /**
       ��ӥ����̾�������������ޤ���

       @param projectName �ץ�������̾
       @return ��ӥ���������
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    String[] getRevisionNames(String projectName) throws DBException;

    /**
       Ʊ����ӥ����Υӥ�ɤ������ޤ���

       @param projectName �ץ�������̾
       @param revision ��ӥ����̾
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    void deleteBuilds(String projectName, String revision) throws DBException;

    /**
       �ӥ�ɤ������ޤ���

       @param projectName �ץ�������̾
       @param id �ӥ��ID
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    void deleteBuild(String projectName, String id) throws DBException;

    /**
       �ץ������Ȥ������ޤ���

       @param projectName �ץ�������̾
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    void deleteProject(String projectName) throws DBException;

    /**
       ����դ�������ޤ���

       @param projectName �ץ�������̾
       @param buildID �ӥ��ID
       @param function �ؿ�̾
       @param gcnoFile GCNO�ե�����̾
       @return �����
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    Graph getGraph(String projectName, String buildID, String function,
		   String gcnoFile) throws DBException;
}
