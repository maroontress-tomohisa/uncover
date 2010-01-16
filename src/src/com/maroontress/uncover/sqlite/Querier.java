package com.maroontress.uncover.sqlite;

import java.sql.PreparedStatement;

/**
   �ơ��֥�˹�ñ�̤����뵡ǽ���󶡤��ޤ���

   @param <T> �ơ��֥�ιԤ�ɽ�����饹
*/
public abstract class Querier<T extends Row> {
    /** ����ѥ���Ѥߥ��ơ��ȥ��ȤǤ��� */
    private PreparedStatement ps;

    /** �ԤΥ��󥹥��󥹤Ǥ��� */
    private T instance;

    /**
       ���󥹥ȥ饯���Ǥ���

       @param ps ����ѥ���Ѥߥ��ơ��ȥ���
    */
    protected Querier(final PreparedStatement ps) {
	this.ps = ps;
    }

    /**
       �Ԥ�ɽ�����󥹥��󥹤����ꤷ�ޤ���

       @param instance �Ԥ�ɽ�����󥹥���
    */
    public final void setRow(final T instance) {
	this.instance = instance;
    }

    /**
       �Ԥ�ɽ�����󥹥��󥹤�������ޤ���

       @return �Ԥ�ɽ�����󥹥���
    */
    public final T getRow() {
	return instance;
    }

    /**
       ����ѥ���Ѥߥ��ơ��ȥ��Ȥ�������ޤ���

       @return ����ѥ���Ѥߥ��ơ��ȥ���
    */
    protected final PreparedStatement getStatement() {
	return ps;
    }
}
