package com.maroontress.uncover;

/**
   DB���󥿥ե�������commit�᥽�åɤΰ����Ȥʤ륤�󥿥ե������Ǥ���
*/
public interface CommitSource {
    /**
       �ץ�������̾��������ޤ���

       @return �ץ�������̾
    */
    String getProjectName();

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

    /**
       �ؿ��Υ��ƥ졼���Υե����ȥ��������ޤ���

       @return �ؿ��Υ��ƥ졼���Υե����ȥ�
    */
    Iterable<Function> getAllFunctions();
}
