package com.maroontress.uncover.coverture;

import com.maroontress.uncover.FunctionSource;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
   Coverture�ν��ϥե����뤫��ؿ����������뤿��δؿ��������Ǥ���
*/
public final class ElementFunctionSource extends ElementSource
    implements FunctionSource {
    /** functionGraph���ǤǤ��� */
    private Element elem;

    /** ������������Ǥ��� */
    private int allArcs;

    /** �¹ԺѤߥ������ο��Ǥ��� */
    private int executedArcs;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public ElementFunctionSource() {
    }

    /**
       functionGraph���Ǥ����ꤷ�ޤ���

       @param elem functionGraph����
    */
    public void setElement(final Element elem) {
	this.elem = elem;

	allArcs = 0;
	executedArcs = 0;
	NodeList arcList = elem.getElementsByTagName("arc");
	int n = arcList.getLength();
	for (int k = 0; k < n; ++k) {
	    Element arc = (Element) arcList.item(k);
	    if (arc.getAttribute("fake").equals("true")) {
		continue;
	    }
	    ++allArcs;
	    if (getIntAttribute(arc, "count") > 0) {
		++executedArcs;
	    }
	}
    }

    /** {@inheritDoc} */
    public String getCheckSum() {
	return elem.getAttribute("checksum");
    }

    /** {@inheritDoc} */
    public int getComplexity() {
	return getIntAttribute(elem, "complexity");
    }

    /** {@inheritDoc} */
    public String getName() {
	return elem.getAttribute("functionName");
    }

    /** {@inheritDoc} */
    public String getGCNOFile() {
	Element p = (Element) elem.getParentNode();
	return p.getAttribute("file");
    }

    /** {@inheritDoc} */
    public String getSourceFile() {
	return elem.getAttribute("sourceFile");
    }

    /** {@inheritDoc} */
    public int getLineNumber() {
	return getIntAttribute(elem, "lineNumber");
    }

    /** {@inheritDoc} */
    public int getExecutedBlocks() {
	return getIntAttribute(elem, "executedBlocks");
    }

    /** {@inheritDoc} */
    public int getAllBlocks() {
	return getIntAttribute(elem, "allBlocks");
    }

    /** {@inheritDoc} */
    public int getExecutedArcs() {
	return executedArcs;
    }

    /** {@inheritDoc} */
    public int getAllArcs() {
	return allArcs;
    }
}
