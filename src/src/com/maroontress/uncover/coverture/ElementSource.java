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
       °���ͤ������Ȥ��Ƽ������ޤ���

       °���ͤ���ʸ����ξ���0���֤��ޤ���

       @param elem ����
       @param s °��̾
       @return °����
    */
    protected static int getIntAttribute(final Element elem, final String s) {
	String v = elem.getAttribute(s);

	return (v.equals("")) ? 0 : Integer.parseInt(v);
    }
}
