package com.maroontress.uncover.coverture;

import com.maroontress.uncover.FunctionSource;
import org.w3c.dom.Element;

/**
   Covertureの出力ファイルから関数を生成するための関数ソースです。
*/
public final class ElementFunctionSource implements FunctionSource {
    /** functionGraph要素です。 */
    private Element elem;

    /**
       インスタンスを生成します。
    */
    public ElementFunctionSource() {
    }

    /**
       functionGraph要素を設定します。

       @param elem functionGraph要素
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
       属性値を整数として取得します。

       属性値が空文字列の場合は0を返します。

       @param elem 要素
       @param s 属性名
       @return 属性値
    */
    private static int getIntAttribute(final Element elem, final String s) {
	String v = elem.getAttribute(s);

	return (v.equals("")) ? 0 : Integer.parseInt(v);
    }
}
