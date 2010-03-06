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
   Coverture�ν��ϥե����뤫�饰��դ��������뤿��Υ���ե������Ǥ���
*/
public final class ElementGraphSource extends ElementSource
    implements GraphSource {
    /**
       ���ꤷ��̾���λ����Ǥ��饤�󥹥��󥹤�������������������Ǽ�
       ������ե����ȥ����ݥ��饹�Ǥ���
    */
    private abstract class Factory<T> {
	/** ���Ǥ�̾���Ǥ��� */
	private String tagName;

	/** �����������󥹥��󥹤Υꥹ�ȤǤ��� */
	private List<T> list;

	/**
	   ���󥹥��󥹤��������ޤ���

	   @param tagName ���Ǥ�̾��
	*/
	public Factory(final String tagName) {
	    this.tagName = tagName;
	    list = new ArrayList<T>();
	}

	/**
	   ���Ǥ��饤�󥹥��󥹤��������ޤ���

	   @param elem ����
	*/
	protected abstract T create(Element elem);

	/**
	   ���Ǥ����ꤷ�ޤ���

	   ���ꤷ�����Ǥ˴ޤޤ�롢���󥹥ȥ饯���ǻ��ꤷ��̾��������
	   ���٤Ƥ��Ф��ơ����줾��this.create()��ƤӽФ��ƥ��󥹥���
	   �����������ޤ��������������󥹥��󥹤�this.toArray()�Ǽ���
	   �Ǥ��ޤ���

	   @param elem ����
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
	   �����������󥹥��󥹤������������ޤ���

	   @param array ���󥹥��󥹤�����
	   @return ���󥹥��󥹤�����
	*/
	public T[] toArray(final T[] array) {
	    return list.toArray(array);
	}

	/**
	   �����������󥹥��󥹤���������ǿ���������ޤ���

	   @return ���󥹥��󥹤���������ǿ�
	*/
	public int size() {
	    return list.size();
	}
    }

    /** functionGraph���ǤǤ��� */
    private Element elem;

    /** ���ܥ֥�å����������������ե����ȥ�Ǥ��� */
    private Factory<Block> blockFactory;

    /** ���������������������ե����ȥ�Ǥ��� */
    private Factory<Arc> arcFactory;

    /**
       ���󥹥��󥹤��������ޤ���
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
       functionGraph���Ǥ����ꤷ�ޤ���

       @param elem functionGraph����
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
