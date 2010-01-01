package com.maroontress.uncover.html;

/**
   HTML�ơ��֥�Τ���ε�ǽ���󶡤��ޤ���
*/
public final class Table {
    /**
       ���󥹥ȥ饯�������ѤǤ��ޤ���
    */
    private Table() {
    }

    /**
       �ơ��֥�Υǡ����ʺ��󤻡ˤ�������ޤ���

       @param s ʸ����
       @return �ơ��֥�Υǡ���
    */
    public static String dataL(final String s) {
        return "<td align=\"left\">" + s + "</td>";
    }

    /**
       �ơ��֥�Υǡ����ʱ��󤻡ˤ�������ޤ���

       @param s ʸ����
       @return �ơ��֥�Υǡ���
    */
    public static String dataR(final String s) {
        return "<td align=\"right\">" + s + "</td>";
    }

    /**
       �ơ��֥�ιԤ�������ޤ���

       @param s ʸ����
       @return �ơ��֥�ι�
    */
    public static String row(final String s) {
        return "<tr>" + s + "</tr>";
    }
}
