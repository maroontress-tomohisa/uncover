package com.maroontress.uncover.sqlite;

/**
   ����եơ��֥�ιԤ˴ؤ��������ݻ����ޤ���
*/
public final class GraphRow extends Row {
    /** �ؿ�ID�Ǥ��� */
    private String functionID;

    /** */
    private String buildID;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public GraphRow() {
    }

    /**
       �ե�����ɤ��ͤ����ꤷ�ޤ���

       @param functionID �ؿ�ID
       @param buildID �ӥ��ID
    */
    public void set(final String functionID, final String buildID) {
	this.functionID = functionID;
	this.buildID = buildID;
    }
}
