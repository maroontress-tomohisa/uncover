package com.maroontress.uncover;

/**
   �ץ�ѥƥ��Ǥ���
*/
public final class Properties {
    /** �ǡ����١����ե�����Υѥ��Ǥ��� */
    private String dbFile;

    /**
       �ǥե���ȤΥץ�ѥƥ����������ޤ���
    */
    public Properties() {
	dbFile = null;
    }

    /**
       �ǡ����١����ե�����Υѥ������ꤷ�ޤ���

       @param dbFile �ǡ����١����ե�����Υѥ�
    */
    public void setDBFile(final String dbFile) {
	this.dbFile = dbFile;
    }

    /**
       �ǡ����١����ե�����Υѥ���������ޤ���

       @return �ǡ����١����ե�����Υѥ�
    */
    public String getDBFile() {
	return dbFile;
    }
}
