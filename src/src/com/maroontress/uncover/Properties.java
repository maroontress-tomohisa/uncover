package com.maroontress.uncover;

import java.util.prefs.Preferences;

/**
   �ץ�ѥƥ��Ǥ���
*/
public final class Properties {
    /** �ǥե���ȤΥǡ����١����ե�����Υ����Ǥ��� */
    public static final String KEY_DB_DEFAULT = "db.default";

    /** �ǡ����١����ե�����Υѥ��Ǥ��� */
    private String dbFile;

    /**
       �ǥե���ȤΥץ�ѥƥ����������ޤ���
    */
    public Properties() {
	Preferences prefs = Preferences.userNodeForPackage(Uncover.class);
	dbFile = prefs.get(KEY_DB_DEFAULT, null);
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
