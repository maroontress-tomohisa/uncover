package com.maroontress.uncover.coverture;

import com.maroontress.uncover.Toolkit;
import com.maroontress.uncover.Function;
import java.util.Iterator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
   Functionのイテレータです。

   Functionの生成に使用するツールキットは、イテレータ生成時に決定しま
   す。したがって、イテレータを生成した後にツールキットを切り替えても、
   そのイテレータは以前のツールキットを使用し続けます。
*/
public final class FunctionIterator implements Iterator<Function> {
    /** functionGraph要素のノードリストです。 */
    private NodeList allFunctions;

    /** 次に取り出す要素のオフセットです。 */
    private int offset;

    /** ノードリストに含まれる要素の数です。 */
    private int length;

    /** ツールキットのインスタンスです。 */
    private Toolkit toolkit;

    /** ツールキットのインスタンスです。 */
    private ElementFunctionSource source;

    /**
       Functionのイテレータを生成します。

       @param allFunctions functionGraph要素のノードリスト
    */
    public FunctionIterator(final NodeList allFunctions) {
	this.allFunctions = allFunctions;
	offset = 0;
	length = allFunctions.getLength();
	toolkit = Toolkit.getInstance();
	source = new ElementFunctionSource();
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
	return (offset < length);
    }

    /** {@inheritDoc} */
    public Function next() {
	source.setElement((Element) allFunctions.item(offset));
	++offset;
	return toolkit.createFunction(source);
    }

    /** {@inheritDoc} */
    public void remove() {
	throw new UnsupportedOperationException();
    }
}
