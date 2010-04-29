package com.maroontress.uncover.coverture;

import com.maroontress.uncover.FunctionGraph;
import com.maroontress.uncover.Parser;
import com.maroontress.uncover.ParsingException;
import java.io.File;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
   Coverture�ν��ϥե�������᤹��ѡ����Ǥ���
*/
public final class CovertureParser implements Parser {
    /**
       Coverture�ν��ϥե�����Υѥ�����ɥ�����Ȥ��������ޤ���

       @param xmlFile Coverture�ν��ϥե�����Υѥ�
       @return �ɥ������
       @throws ParsingException �ѡ����˼��Ԥ����Ȥ��˥������ޤ���
    */
    private static Document createDocument(final String xmlFile)
	throws ParsingException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new ErrorHandler() {
                public void warning(final SAXParseException ex)
                    throws SAXException {
                    fatalError(ex);
                }
                public void error(final SAXParseException ex)
                    throws SAXException {
                    fatalError(ex);
                }
                public void fatalError(final SAXParseException ex)
                    throws SAXException {
		    throw new SAXException(xmlFile
					   + ":" + ex.getLineNumber()
					   + ":" + ex.getColumnNumber()
					   + ": " + ex.getMessage(), ex);
                }
            });
            doc = builder.parse(new File(xmlFile));
        } catch (Exception e) {
	    throw new ParsingException(e.getMessage(), e);
        }
        return doc;
    }

    /** functionGraph���ǤΥΡ��ɥꥹ�ȤǤ��� */
    private NodeList allFunctions;

    /**
       �ѡ������������ޤ���

       @param xmlFile Coverture�ν��ϥե�����Υѥ�
       @throws ParsingException �ѡ����˼��Ԥ����Ȥ��˥������ޤ���
    */
    public CovertureParser(final String xmlFile) throws ParsingException {
        Document doc = createDocument(xmlFile);
        Element root = doc.getDocumentElement();
        if (!root.getNodeName().equals("gcno")) {
	    throw new ParsingException("not a file coverture outputs: "
				       + xmlFile);
        }
        allFunctions = root.getElementsByTagName("functionGraph");
        if (allFunctions.getLength() == 0) {
	    throw new ParsingException("no functionGraph element: " + xmlFile);
        }
    }

    /** {@inheritDoc} */
    public Iterator<FunctionGraph> iterator() {
	return new FunctionGraphIterator(allFunctions);
    }
}
