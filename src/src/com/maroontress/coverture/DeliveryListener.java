package com.maroontress.coverture;

/**
   �ͤ��������륤�󥿥ե������Ǥ���

   @param <T> ���������ͤΥ��饹
*/
public interface DeliveryListener<T> {

    /**
       �ͤ��������ޤ���

       @param instance ��
    */
    void deliver(T instance);
}
