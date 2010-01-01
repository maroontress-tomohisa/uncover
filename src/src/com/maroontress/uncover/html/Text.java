package com.maroontress.uncover.html;

/**
   HTML�ƥ����ȤΤ���ε�ǽ���󶡤��ޤ���
*/
public final class Text {
    /**
       ���󥹥ȥ饯�������ѤǤ��ޤ���
    */
    private Text() {
    }

    /**
       �ܡ���ɤΥƥ����Ȥ��������ޤ���

       @param s ʸ����
       @return �ܡ���ɤΥƥ�����
    */
    public static String bold(final String s) {
        return "<b>" + s + "</b>";
    }

    /**
       2�Ĥ��ͤ���������������ޤ���

       �����ͤ��麣���ͤؤη����˱�����������������������ޤ���

       @param prev ������
       @param num ������
       @return ���
    */
    public static String trendArrow(final int prev, final int num) {
        if (prev == num) {
            return "&#x2192;";
        }
        return ((prev < num) ? "&#x2197;" : "&#x2198;");
    }
}
