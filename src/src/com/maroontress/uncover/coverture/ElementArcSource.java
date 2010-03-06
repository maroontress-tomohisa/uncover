package com.maroontress.uncover.coverture;

import com.maroontress.uncover.ArcSource;
import org.w3c.dom.Element;

/**
   Coverture�ν��ϥե����뤫�饢�������������뤿��Υ������������Ǥ���
*/
public final class ElementArcSource extends ElementSource
    implements ArcSource {
    /** arc���ǤǤ��� */
    private Element elem;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public ElementArcSource() {
    }

    /**
       arc���Ǥ����ꤷ�ޤ���

       @param elem arc����
    */
    public void setElement(final Element elem) {
	this.elem = elem;
    }

    /** {@inheritDoc} */
    public int getStart() {
	Element p = (Element) elem.getParentNode();
	return getIntAttribute(p, "id");
    }

    /** {@inheritDoc} */
    public int getEnd() {
	return getIntAttribute(elem, "destination");
    }

    /** {@inheritDoc} */
    public int getCount() {
	return getIntAttribute(elem, "count");
    }

    /** {@inheritDoc} */
    public boolean isFake() {
	return elem.getAttribute("fake").equals("true");
    }
}
