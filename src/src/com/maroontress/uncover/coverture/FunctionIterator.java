package com.maroontress.uncover.coverture;

import com.maroontress.uncover.Function;
import java.util.Iterator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
   Function�Υ��ƥ졼���Ǥ���
*/
public final class FunctionIterator implements Iterator<Function> {
    /** functionGraph���ǤΥΡ��ɥꥹ�ȤǤ��� */
    private NodeList allFunctions;

    /** ���˼��Ф����ǤΥ��ե��åȤǤ��� */
    private int offset;

    /** �Ρ��ɥꥹ�Ȥ˴ޤޤ�����Ǥο��Ǥ��� */
    private int length;

    /**
       Function�Υ��ƥ졼�����������ޤ���

       @param allFunctions functionGraph���ǤΥΡ��ɥꥹ��
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
