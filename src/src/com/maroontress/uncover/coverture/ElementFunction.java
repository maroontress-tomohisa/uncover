package com.maroontress.uncover.coverture;

import com.maroontress.uncover.Function;
import org.w3c.dom.Element;

/**
   Covertureの出力から生成されるFunctionです。
*/
public final class ElementFunction extends Function {
    /**
       属性値を整数として取得します。

       属性値が空文字列の場合は0を返します。

       @param e 要素
       @param s 属性名
       @return 属性値
    */
    private static int getIntAttribute(final Element e, final String s) {
	String v = e.getAttribute(s);

	return (v.equals("")) ? 0 : Integer.parseInt(v);
    }

    /**
       functionGraph要素からインスタンスを生成します。

       @param e 要素
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
