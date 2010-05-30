package com.maroontress.coverture;

import java.util.Iterator;

/**
   ���Ǥ�ȥ�С������ޤ���

   @param <E> ����
*/
public final class Traverser<E> {

    /** ���Ǥ�ȿ���ҤǤ��� */
    private Iterator<E> iterator;

    /** �ȥ�С������֤����ǤǤ��� */
    private E element;

    /**
       �ȥ�С��������������ޤ���

       @param i ȿ����ǽ�ʥ��󥹥���
    */
    public Traverser(final Iterable<E> i) {
	iterator = i.iterator();
	updateElement();
    }

    /**
       �ȥ�С������֤����Ǥ򹹿����ޤ���
    */
    private void updateElement() {
	element = (iterator.hasNext()) ? iterator.next() : null;
    }

    /**
       �ȥ�С����ΰ��֤����Ǥ�������ޤ����ȥ�С����ΰ��֤��ѹ����ޤ���

       �ȥ�С����ΰ��֤���ü��ã���Ƥ������null���֤��ޤ���

       @return �ȥ�С����ΰ��֤����ǡ��ޤ���null
    */
    public E peek() {
	return element;
    }

    /**
       �ȥ�С����ΰ��֤����Ǥ�������ޤ����ȥ�С����ΰ��֤�ҤȤĿʤ�ޤ���

       �ȥ�С����ΰ��֤���ü��ã���Ƥ������null���֤��ޤ���

       @return �ȥ�С����ΰ��֤����ǡ��ޤ���null
    */
    public E poll() {
	E e = element;
	updateElement();
	return e;
    }
}
