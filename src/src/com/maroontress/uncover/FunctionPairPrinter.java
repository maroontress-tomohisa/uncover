package com.maroontress.uncover;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
   �ؿ��ڥ�����Ϥ��뤿��Υ��饹�Ǥ���
*/
public abstract class FunctionPairPrinter {
    /** �ؿ��ڥ��Υꥹ�ȤǤ��� */
    private List<FunctionPair> pairList;

    /** �����ȥ�Ǥ��� */
    private String title;

    /** �ؿ��ڥ��ν��Ͻ����ꤹ�륳��ѥ졼���Ǥ��� */
    private Comparator<FunctionPair> comparator;

    /** HTML�ơ��֥�Υإå��Ȥʤ�ԤǤ��� */
    private String header;

    /**
       �ǥե���ȥ��󥹥ȥ饯���ϻ��ѤǤ��ޤ���
    */
    private FunctionPairPrinter() {
    }

    /**
       ���󥹥��󥹤��������ޤ���

       @param pairList �ؿ��ڥ��Υꥹ��
       @param title �����ȥ�
       @param comparator �ؿ��ڥ��ν��Ͻ����ꤹ�륳��ѥ졼��
       @param header HTML�ơ��֥�Υإå��Ȥʤ��
    */
    protected FunctionPairPrinter(final List<FunctionPair> pairList,
				  final String title,
				  final Comparator<FunctionPair> comparator,
				  final String header) {
	this.pairList = pairList;
	this.title = title;
	this.comparator = comparator;
	this.header = header;
    }

    /**
       HTML��ݡ��ȤΡ��Ѳ��פιԤ�������ޤ���

       @param pair �ؿ��ڥ�
       @return ���Ѳ��פι�
    */
    public abstract String getRow(FunctionPair pair);

    /**
       �ؿ��ڥ���ե��륿���뤫�ɤ�����������ޤ���

       @param pair �ؿ��ڥ�
       @return �ե��륿�������true
    */
    public abstract boolean isFiltered(FunctionPair pair);

    /**
       ���Ф줿�ؿ��ڥ������Ǥ���������Ǽ������ޤ���

       @param all �ؿ��ڥ�
       @return ���򤵤줿�ؿ��ڥ�������
    */
    private FunctionPair[] select(final Iterable<FunctionPair> all) {
        List<FunctionPair> list = new ArrayList<FunctionPair>();
	for (FunctionPair pair : all) {
	    if (isFiltered(pair)) {
		continue;
	    }
            list.add(pair);
	}
	return list.toArray(new FunctionPair[list.size()]);
    }

    /**
       HTML��ݡ��ȤΡ��Ѳ��פ���Ϥ��ޤ���

       @param out ���ϥ��ȥ꡼��
    */
    public final void printChanges(final PrintStream out) {
        out.printf("<h3>%s</h3>\n", title);
        FunctionPair[] array = select(pairList);
        if (array.length == 0) {
            out.print("<p>None</p>\n");
            return;
        }
        out.printf("<p>%d/%d function(s) found.</p>\n",
		   array.length, pairList.size());
        Arrays.sort(array, comparator);
        out.print("<table border=\"1\">\n"
		  + "<tbody>\n");
        out.printf("%s\n", header);
        for (FunctionPair pair : array) {
            out.printf("%s\n", getRow(pair));
        }
        out.print("</tbody>\n"
		  + "</table>\n");
    }
}
