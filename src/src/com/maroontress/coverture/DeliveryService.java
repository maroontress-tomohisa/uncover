package com.maroontress.coverture;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
   ���������ӥ��Ǥ���

   @param <T> ���������ͤΥ��饹
*/
public final class DeliveryService<T> {

    /** ���������ͤθĿ��Ǥ��� */
    private int taskCount;

    /** �ͤ�����������Ʊ���������Υ��塼�Ǥ��� */
    private CompletionService<T> service;

    /**
       ����åɥס���Υ���åɿ�����ꤷ�ơ����󥹥��󥹤��������ޤ���

       @param threads ����åɿ�
    */
    public DeliveryService(final int threads) {
	service = new ExecutorCompletionService<T>(
	    Executors.newFixedThreadPool(threads));
	taskCount = 0;
    }

    /**
       �ͤ��֤��¹��ѥ��������������ޤ���

       @param callable �ͤ��֤��¹��ѥ�����
    */
    public void submit(final Callable<T> callable) {
	++taskCount;
	service.submit(callable);
    }

    /**
       �����ӥ������̤���������ꥹ�ʤ����Τ��ޤ������٤ƤΥ�������
       ��̤����ΤǤ���ޤǥ֥�å����ޤ���

       @param listener �ꥹ��
       @throws ExecutionException �������åɤ��㳰�򥹥�
    */
    public void deliver(final DeliveryListener<T> listener)
	throws ExecutionException {
 	try {
	    while (taskCount > 0) {
		Future<T> future = service.take();
		T instance = future.get();
		if (instance == null) {
		    continue;
		}
		listener.deliver(instance);
		--taskCount;
	    }
	} catch (InterruptedException e) {
	    throw new RuntimeException("internal error.", e);
	}
    }
}
