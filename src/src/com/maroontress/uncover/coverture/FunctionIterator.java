package com.maroontress.uncover.coverture;

import com.maroontress.uncover.Function;
import java.util.Iterator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
   Functionのイテレータです。
*/
public final class FunctionIterator implements Iterator<Function> {
    /** functionGraph要素のノードリストです。 */
    private NodeList allFunctions;

    /** 次に取り出す要素のオフセットです。 */
    private int offset;

    /** ノードリストに含まれる要素の数です。 */
    private int length;

    /**
       Functionのイテレータを生成します。

       @param allFunctions functionGraph要素のノードリスト
    */
    public FunctionIterator(final NodeList allFunctions) {
	this.allFunctions = allFunctions;
	offset = 0;
	length = allFunctions.getLength();
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
	return (offset < length);
    }

    /** {@inheritDoc} */
    public Function next() {
	Element e = (Element) allFunctions.item(offset);
	++offset;
	return new ElementFunction(e);
    }

    /** {@inheritDoc} */
    public void remove() {
	throw new UnsupportedOperationException();
    }
}
