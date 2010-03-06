package com.maroontress.uncover.coverture;

import com.maroontress.uncover.Arc;
import com.maroontress.uncover.Block;
import com.maroontress.uncover.GraphSource;
import com.maroontress.uncover.Toolkit;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
   Covertureの出力ファイルからグラフを生成するためのグラフソースです。
*/
public final class ElementGraphSource extends ElementSource
    implements GraphSource {
    /**
       指定した名前の子要素からインスタンスを生成し、それらを配列で取
       得するファクトリの抽象クラスです。
    */
    private abstract class Factory<T> {
	/** 要素の名前です。 */
	private String tagName;

	/** 生成したインスタンスのリストです。 */
	private List<T> list;

	/**
	   インスタンスを生成します。

	   @param tagName 要素の名前
	*/
	public Factory(final String tagName) {
	    this.tagName = tagName;
	    list = new ArrayList<T>();
	}

	/**
	   要素からインスタンスを生成します。

	   @param elem 要素
	*/
	protected abstract T create(Element elem);

	/**
	   要素を設定します。

	   指定した要素に含まれる、コンストラクタで指定した名前の要素
	   すべてに対して、それぞれthis.create()を呼び出してインスタン
	   スを生成します。生成したインスタンスはthis.toArray()で取得
	   できます。

	   @param elem 要素
	*/
	public void setElement(final Element elem) {
	    list.clear();
	    NodeList nodeList = elem.getElementsByTagName(tagName);
	    int n = nodeList.getLength();
	    for (int k = 0; k < n; ++k) {
		list.add(create((Element) nodeList.item(k)));
	    }
	}

	/**
	   生成したインスタンスの配列を取得します。

	   @param array インスタンスの配列
	   @return インスタンスの配列
	*/
	public T[] toArray(final T[] array) {
	    return list.toArray(array);
	}

	/**
	   生成したインスタンスの配列の要素数を取得します。

	   @return インスタンスの配列の要素数
	*/
	public int size() {
	    return list.size();
	}
    }

    /** functionGraph要素です。 */
    private Element elem;

    /** 基本ブロックの配列を生成するファクトリです。 */
    private Factory<Block> blockFactory;

    /** アークの配列を生成するファクトリです。 */
    private Factory<Arc> arcFactory;

    /**
       インスタンスを生成します。
    */
    public ElementGraphSource() {
	final Toolkit tk = Toolkit.getInstance();
	blockFactory = new Factory<Block>("block") {
	    private ElementBlockSource blockSource = new ElementBlockSource();
	    public Block create(final Element e) {
		blockSource.setElement(e);
		return tk.createBlock(blockSource);
	    }
	};
	arcFactory = new Factory<Arc>("arc") {
	    private ElementArcSource arcSource = new ElementArcSource();
	    public Arc create(final Element e) {
		arcSource.setElement(e);
		return tk.createArc(arcSource);
	    }
	};
    }

    /**
       functionGraph要素を設定します。

       @param elem functionGraph要素
    */
    public void setElement(final Element elem) {
	this.elem = elem;

	arcFactory.setElement(elem);
	blockFactory.setElement(elem);
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
    public Block[] getAllBlocks() {
	return blockFactory.toArray(new Block[blockFactory.size()]);
    }

    /** {@inheritDoc} */
    public Arc[] getAllArcs() {
	return arcFactory.toArray(new Arc[arcFactory.size()]);
    }
}
