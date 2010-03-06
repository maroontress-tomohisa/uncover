package com.maroontress.uncover.coverture;

import com.maroontress.uncover.Function;
import com.maroontress.uncover.FunctionGraph;
import com.maroontress.uncover.Graph;
import com.maroontress.uncover.Toolkit;
import java.util.Iterator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
   FunctionGraphのイテレータです。

   FunctionGraphの生成に使用するツールキットは、イテレータ生成時に決定
   します。したがって、イテレータを生成した後にツールキットを切り替え
   ても、そのイテレータは以前のツールキットを使用し続けます。
*/
public final class FunctionGraphIterator implements Iterator<FunctionGraph> {
    /** functionGraph要素のノードリストです。 */
    private NodeList allFunctions;

    /** 次に取り出す要素のオフセットです。 */
    private int offset;

    /** ノードリストに含まれる要素の数です。 */
    private int length;

    /** ツールキットのインスタンスです。 */
    private Toolkit toolkit;

    /**
       functionGraph要素から関数を生成するための関数ソースです。
    */
    private ElementFunctionSource functionSource;

    /**
       functionGraph要素からグラフを生成するためのグラフソースです。
    */
    private ElementGraphSource graphSource;

    /**
       Functionのイテレータを生成します。

       @param allFunctions functionGraph要素のノードリスト
    */
    public FunctionGraphIterator(final NodeList allFunctions) {
	this.allFunctions = allFunctions;
	offset = 0;
	length = allFunctions.getLength();
	toolkit = Toolkit.getInstance();
	functionSource = new ElementFunctionSource();
	graphSource = new ElementGraphSource();
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
	return (offset < length);
    }

    /** {@inheritDoc} */
    public FunctionGraph next() {
	Element elem = (Element) allFunctions.item(offset);
	functionSource.setElement(elem);
	Function function = toolkit.createFunction(functionSource);
	graphSource.setElement(elem);
	Graph graph = toolkit.createGraph(graphSource);
	++offset;
	return new FunctionGraph(function, graph);
    }

    /** {@inheritDoc} */
    public void remove() {
	throw new UnsupportedOperationException();
    }
}
