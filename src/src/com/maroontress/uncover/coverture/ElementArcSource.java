package com.maroontress.uncover.coverture;

import com.maroontress.uncover.ArcSource;
import org.w3c.dom.Element;

/**
   Covertureの出力ファイルからアークを生成するためのアークソースです。
*/
public final class ElementArcSource extends ElementSource
    implements ArcSource {
    /** arc要素です。 */
    private Element elem;

    /**
       インスタンスを生成します。
    */
    public ElementArcSource() {
    }

    /**
       arc要素を設定します。

       @param elem arc要素
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
