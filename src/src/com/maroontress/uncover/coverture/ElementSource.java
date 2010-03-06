package com.maroontress.uncover.coverture;

import org.w3c.dom.Element;

/**
   Covertureの出力ファイルからインスタンスを生成するソースの基底クラス
   です。
*/
public abstract class ElementSource {
    /**
       インスタンスを生成します。
    */
    protected ElementSource() {
    }

    /**
       属性値を整数として取得します。

       属性値が空文字列の場合は0を返します。

       @param elem 要素
       @param s 属性名
       @return 属性値
    */
    protected static int getIntAttribute(final Element elem, final String s) {
	String v = elem.getAttribute(s);

	return (v.equals("")) ? 0 : Integer.parseInt(v);
    }
}
