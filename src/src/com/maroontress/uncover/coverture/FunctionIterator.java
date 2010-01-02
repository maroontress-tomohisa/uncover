package com.maroontress.uncover.coverture;

import com.maroontress.uncover.Toolkit;
import com.maroontress.uncover.Function;
import java.util.Iterator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
   Function�Υ��ƥ졼���Ǥ���

   Function�������˻��Ѥ���ġ��륭�åȤϡ����ƥ졼���������˷��ꤷ��
   �����������äơ����ƥ졼��������������˥ġ��륭�åȤ��ڤ��ؤ��Ƥ⡢
   ���Υ��ƥ졼���ϰ����Υġ��륭�åȤ���Ѥ�³���ޤ���
*/
public final class FunctionIterator implements Iterator<Function> {
    /** functionGraph���ǤΥΡ��ɥꥹ�ȤǤ��� */
    private NodeList allFunctions;

    /** ���˼��Ф����ǤΥ��ե��åȤǤ��� */
    private int offset;

    /** �Ρ��ɥꥹ�Ȥ˴ޤޤ�����Ǥο��Ǥ��� */
    private int length;

    /** �ġ��륭�åȤΥ��󥹥��󥹤Ǥ��� */
    private Toolkit toolkit;

    /** �ġ��륭�åȤΥ��󥹥��󥹤Ǥ��� */
    private ElementFunctionSource source;

    /**
       Function�Υ��ƥ졼�����������ޤ���

       @param allFunctions functionGraph���ǤΥΡ��ɥꥹ��
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
