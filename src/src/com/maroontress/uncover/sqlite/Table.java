package com.maroontress.uncover.sqlite;

/**
   �ơ��֥�˴ؤ���������ݻ����ޤ���
*/
public final class Table {
    /** �ơ��֥�Υץ�ե��å����Ǥ��� */
    public static final String PREFIX = "com_maroontress_uncover_";

    /** ����Υơ��֥�̾�Ǥ��� */
    public static final String CONFIG = PREFIX + "config";

    /** �ץ������ȤΥơ��֥�̾�Ǥ��� */
    public static final String PROJECT = PREFIX + "project";

    /** �ӥ�ɤΥơ��֥�̾�Ǥ��� */
    public static final String BUILD = PREFIX + "build";

    /** �ؿ��Υơ��֥�̾�Ǥ��� */
    public static final String FUNCTION = PREFIX + "function";

    /** ����դΥơ��֥�̾�Ǥ��� */
    public static final String GRAPH = PREFIX + "graph";

    /** ����ե��ޥ�Υơ��֥�̾�Ǥ��� */
    public static final String GRAPH_SUMMARY = PREFIX + "graphSummary";

    /** ����ե֥�å��Υơ��֥�̾�Ǥ��� */
    public static final String GRAPH_BLOCK = PREFIX + "graphBlock";

    /** ����ե������Υơ��֥�̾�Ǥ��� */
    public static final String GRAPH_ARC = PREFIX + "graphArc";

    /**
       ���󥹥ȥ饯���ˤϥ��������Ǥ��ޤ���
    */
    private Table() {
    }
}
