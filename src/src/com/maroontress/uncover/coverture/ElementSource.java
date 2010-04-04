package com.maroontress.uncover.coverture;

import org.w3c.dom.Element;

/**
   Coverture�ν��ϥե����뤫�饤�󥹥��󥹤��������륽�����δ��쥯�饹
   �Ǥ���
*/
public abstract class ElementSource {
    /**
       ���󥹥��󥹤��������ޤ���
    */
    protected ElementSource() {
    }

    /**
       °���ͤ�32�ӥå������Ȥ��Ƽ������ޤ���

       °���ͤ���ʸ����ξ���0���֤��ޤ���

       @param elem ����
       @param s °��̾
       @return °����
    */
    protected static int getIntAttribute(final Element elem, final String s) {
	String v = elem.getAttribute(s);

	return (v.equals("")) ? 0 : Integer.parseInt(v);
    }

    /**
       °���ͤ�64�ӥå������Ȥ��Ƽ������ޤ���

       °���ͤ���ʸ����ξ���0���֤��ޤ���

       @param elem ����
       @param s °��̾
       @return °����
    */
    protected static long getLongAttribute(final Element elem,
					   final String s) {
	String v = elem.getAttribute(s);

	return (v.equals("")) ? 0 : Long.parseLong(v);
    }
}
