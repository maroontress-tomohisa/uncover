package com.maroontress.uncover.coverture;

import com.maroontress.uncover.FunctionSource;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
   Covertureの出力ファイルから関数を生成するための関数ソースです。
*/
public final class ElementFunctionSource extends ElementSource
    implements FunctionSource {
    /** functionGraph要素です。 */
    private Element elem;

    /** アークの総数です。 */
    private int allArcs;

    /** 実行済みアークの数です。 */
    private int executedArcs;

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
