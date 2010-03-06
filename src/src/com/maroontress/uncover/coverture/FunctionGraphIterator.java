package com.maroontress.uncover.coverture;

import com.maroontress.uncover.Function;
import com.maroontress.uncover.FunctionGraph;
import com.maroontress.uncover.Graph;
import com.maroontress.uncover.Toolkit;
import java.util.Iterator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
   FunctionGraph�Υ��ƥ졼���Ǥ���

   FunctionGraph�������˻��Ѥ���ġ��륭�åȤϡ����ƥ졼���������˷���
   ���ޤ����������äơ����ƥ졼��������������˥ġ��륭�åȤ��ڤ��ؤ�
   �Ƥ⡢���Υ��ƥ졼���ϰ����Υġ��륭�åȤ���Ѥ�³���ޤ���
*/
public final class FunctionGraphIterator implements Iterator<FunctionGraph> {
    /** functionGraph���ǤΥΡ��ɥꥹ�ȤǤ��� */
    private NodeList allFunctions;

    /** ���˼��Ф����ǤΥ��ե��åȤǤ��� */
    private int offset;

    /** �Ρ��ɥꥹ�Ȥ˴ޤޤ�����Ǥο��Ǥ��� */
    private int length;

    /** �ġ��륭�åȤΥ��󥹥��󥹤Ǥ��� */
    private Toolkit toolkit;

    /**
       functionGraph���Ǥ���ؿ����������뤿��δؿ��������Ǥ���
    */
    private ElementFunctionSource functionSource;

    /**
       functionGraph���Ǥ��饰��դ��������뤿��Υ���ե������Ǥ���
    */
    private ElementGraphSource graphSource;

    /**
       Function�Υ��ƥ졼�����������ޤ���

       @param allFunctions functionGraph���ǤΥΡ��ɥꥹ��
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
