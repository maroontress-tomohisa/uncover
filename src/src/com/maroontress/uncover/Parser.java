package com.maroontress.uncover;

import java.util.Iterator;

/**
   �ؿ�����ե��ƥ졼���������Ǥ��륤�󥿥ե������Ǥ���
*/
public interface Parser extends Iterable<FunctionGraph> {
    /**
       �ؿ�����ե��ƥ졼����������ޤ���

       @return �ؿ�����ե��ƥ졼��
    */
    Iterator<FunctionGraph> iterator();
}
