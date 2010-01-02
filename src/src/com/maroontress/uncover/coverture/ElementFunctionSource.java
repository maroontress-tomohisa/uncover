package com.maroontress.uncover.coverture;

import com.maroontress.uncover.FunctionSource;
import org.w3c.dom.Element;

/**
   Coverture�ν��ϥե����뤫��ؿ����������뤿��δؿ��������Ǥ���
*/
public final class ElementFunctionSource implements FunctionSource {
    /** functionGraph���ǤǤ��� */
    private Element elem;

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
	return 0;
    }

    /** {@inheritDoc} */
    public int getAllArcs() {
	return 0;
    }

    /**
       °���ͤ������Ȥ��Ƽ������ޤ���

       °���ͤ���ʸ����ξ���0���֤��ޤ���

       @param elem ����
       @param s °��̾
       @return °����
    */
    private static int getIntAttribute(final Element elem, final String s) {
	String v = elem.getAttribute(s);

	return (v.equals("")) ? 0 : Integer.parseInt(v);
    }
}
