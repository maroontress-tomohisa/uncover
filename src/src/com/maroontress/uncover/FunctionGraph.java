package com.maroontress.uncover;

/**
   �ؿ�����դ˴ؤ������򥫥ץ��벽���ޤ���
*/
public final class FunctionGraph {
    /** �ؿ��Ǥ��� */
    private Function function;

    /** ����դǤ��� */
    private Graph graph;

    /**
       ���󥹥��󥹤��������ޤ���

       @param function �ؿ�
       @param graph �����
    */
    public FunctionGraph(final Function function, final Graph graph) {
	this.function = function;
	this.graph = graph;
    }

    /**
       �ؿ���������ޤ���

       @return �ؿ�
    */
    public Function getFunction() {
	return function;
    }

    /**
       ����դ�������ޤ���

       @return �����
    */
    public Graph getGraph() {
	return graph;
    }
}
