package com.maroontress.uncover.gcov;

import com.maroontress.uncover.FunctionGraph;
import com.maroontress.uncover.Parser;
import com.maroontress.uncover.ParsingException;
import java.util.Iterator;
import java.util.List;

/**
   �Ρ��ȥѡ����Ǥ���
*/
public final class GcovNoteParser implements Parser {

    /** gcno�ե�����Υѥ�������Ǥ��� */
    private String[] files;

    /**
       �ѡ������������ޤ���

       @param fileList gcno�ե�����Υѥ��Υꥹ��
       @throws ParsingException �ѡ����˼��Ԥ����Ȥ��˥������ޤ���
    */
    public GcovNoteParser(final List<String> fileList)
	throws ParsingException {
	files = fileList.toArray(new String[fileList.size()]);
    }

    /** {@inheritDoc} */
    public Iterator<FunctionGraph> iterator() {
	return new FunctionGraphIterator(files);
    }
}
