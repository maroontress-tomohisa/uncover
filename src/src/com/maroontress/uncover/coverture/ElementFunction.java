package com.maroontress.uncover.coverture;

import com.maroontress.uncover.Function;
import org.w3c.dom.Element;

/**
   Coverture�ν��Ϥ������������Function�Ǥ���
*/
public final class ElementFunction extends Function {
    /**
       °���ͤ������Ȥ��Ƽ������ޤ���

       °���ͤ���ʸ����ξ���0���֤��ޤ���

       @param e ����
       @param s °��̾
       @return °����
    */
    private static int getIntAttribute(final Element e, final String s) {
	String v = e.getAttribute(s);

	return (v.equals("")) ? 0 : Integer.parseInt(v);
    }

    /**
       functionGraph���Ǥ��饤�󥹥��󥹤��������ޤ���

       @param e ����
    */
    public ElementFunction(final Element e) {
	Element p = (Element) e.getParentNode();
	setSource(e.getAttribute("functionName"),
		  e.getAttribute("sourceFile").intern(),
		  getIntAttribute(e, "lineNumber"),
		  p.getAttribute("file").intern(),
		  e.getAttribute("checksum"));
	setGraph(getIntAttribute(e, "complexity"),
		 getIntAttribute(e, "executedBlocks"),
		 getIntAttribute(e, "allBlocks"),
		 0,
		 0);
    }
}
