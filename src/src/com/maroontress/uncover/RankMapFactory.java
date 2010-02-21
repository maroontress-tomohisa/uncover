package com.maroontress.uncover;

import java.util.HashMap;
import java.util.Map;

/**
   �ؿ��ȥ�󥯤Υޥåפ��������뵡ǽ���󶡤��ޤ���
*/
public abstract class RankMapFactory {
    /**
       ���󥹥��󥹤��������ޤ���
    */
    protected RankMapFactory() {
    }

    /**
       �ؿ��������ͽ�˥�󥭥󥰤��ᡢ�ؿ��ȥ�󥭥󥰤Υޥåפ���
       �����ޤ���

       @param array �ؿ�������������ͽ�˥����ȺѤߡ�
       @return �ؿ��ȥ�󥭥󥰤Υޥå�
    */
    public final Map<Function, Integer> create(final Function[] array) {
        Map<Function, Integer> map = new HashMap<Function, Integer>();
        int rank = 1;
        int n = array.length;
        int lastValue = getIntValue(array[0]);

        map.put(array[0], rank);
        for (int k = 1; k < n; ++k) {
            int value = getIntValue(array[k]);
            if  (lastValue != value) {
                lastValue = value;
                rank = k + 1;
            }
            map.put(array[k], rank);
        }
	return map;
    }

    /**
       �ؿ����������ͤ�������륤�󥿥ե������Ǥ���

       @param function �ؿ�
       @return ������
    */
    public abstract int getIntValue(Function function);
}
