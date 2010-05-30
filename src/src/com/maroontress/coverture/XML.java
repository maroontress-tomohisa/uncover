package com.maroontress.coverture;

import java.util.HashMap;

/**
   XML���ϤΤ���Υ桼�ƥ���ƥ��Ǥ���
*/
public final class XML {

    /** ���������פ���ʸ���ȥ��������׸��ʸ����Υޥå� */
    private static HashMap<Character, String> map;

    static {
        map = new HashMap<Character, String>();
        map.put('>', "&gt;");
        map.put('<', "&lt;");
        map.put('&', "&amp;");
        map.put('"', "&quot;");
        map.put('\'', "&apos;");
    }

    /**
       ���󥹥ȥ饯���Ǥ���
    */
    private XML() {
    }

    /**
       XML�ǽ��ϤǤ���褦�˥��������פ���ʸ�����������ޤ���

       @param s ʸ����
       @return ���������פ���ʸ����
    */
    public static String escape(final String s) {
        StringBuilder b = new StringBuilder();
        int n = s.length();
        for (int k = 0; k < n; ++k) {
            char c = s.charAt(k);
            String m = map.get(c);
            if (m != null) {
                b.append(m);
            } else {
                b.append(c);
            }
        }
        return b.toString();
    }
}
