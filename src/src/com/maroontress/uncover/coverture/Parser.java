package com.maroontress.uncover.coverture;

import com.maroontress.uncover.Function;
import java.io.File;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;

/**
   Covertureの出力ファイルを解釈するパーサです。
*/
public final class Parser implements Iterable<Function> {
    /**
       Covertureの出力ファイルのパスからドキュメントを生成します。

       @param xmlFile Covertureの出力ファイルのパス
       @return ドキュメント
       @throws ParsingException パースに失敗したときにスローします。
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

    /** functionGraph要素のノードリストです。 */
    private NodeList allFunctions;

    /**
       パーサを生成します。

       @param xmlFile Covertureの出力ファイルのパス
       @throws ParsingException パースに失敗したときにスローします。
    */
    public Parser(final String xmlFile) throws ParsingException {
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
    public Iterator<Function> iterator() {
	return new FunctionIterator(allFunctions);
    }
}
