package com.maroontress.uncover.coverture;

import com.maroontress.uncover.BlockSource;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
   Coverture�ν��ϥե����뤫����ܥ֥�å����������뤿��Υ֥�å�����
   ���Ǥ���
*/
public final class ElementBlockSource extends ElementSource
    implements BlockSource {
    /** block���ǤǤ��� */
    private Element elem;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public ElementBlockSource() {
    }

    /**
       block���Ǥ����ꤷ�ޤ���

       @param elem block����
    */
    public void setElement(final Element elem) {
	this.elem = elem;
    }

    /** {@inheritDoc} */
    public int getNumber() {
	return getIntAttribute(elem, "id");
    }

    /** {@inheritDoc} */
    public int getCount() {
	return getIntAttribute(elem, "count");
    }

    /** {@inheritDoc} */
    public String getSourceFile() {
	NodeList nodeList = elem.getElementsByTagName("lines");
	int n = nodeList.getLength();
	if (n == 0) {
	    return null;
	}
	Element linesElem = (Element) nodeList.item(0);
	return linesElem.getAttribute("fileName");
    }

    /** {@inheritDoc} */
    public int getLineNumber() {
	NodeList nodeList = elem.getElementsByTagName("line");
	int n = nodeList.getLength();
	if (n == 0) {
	    return 0;
	}
	Element lineElem = (Element) nodeList.item(0);
	return getIntAttribute(lineElem, "number");
    }
}
