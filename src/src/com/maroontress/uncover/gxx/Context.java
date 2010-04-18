package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
   デマングルパーサのコンテキストです。
*/
public final class Context {
    /** 修飾子のマップです。 */
    private static Map<Character, String> qualifierMap;

    static {
	qualifierMap = new HashMap<Character, String>();
	qualifierMap.put('r', "restrict");
	qualifierMap.put('K', "const");
	qualifierMap.put('V', "volatile");
    }

    /** オリジナルのシーケンスです。 */
    private String source;

    /** パース対象のシーケンスです。 */
    private CharSequence sequence;

    /** オリジナルに対してシーケンスが始まる位置です。 */
    private int position;

    /** 置換文字列のリストです。 */
    private List<Exportable> substitution;

    /**
       インスタンスを生成します。

       @param source パースするシーケンス
    */
    public Context(final String source) {
	this.source = source;
	sequence = source;
	position = 0;
	substitution = new ArrayList<Exportable>();
    }

    /** {@inheritDoc} */
    public String toString() {
	return sequence.toString()
	    + ":" + position
	    + ":" + sequence.subSequence(position, sequence.length());
    }

    /**
       置換文字列を追加します。

       置換文字列の内容はコピーしてコンテキスト内部に保持します。この
       メソッド呼び出し後にeを変更しても、追加した内容は変化しません。

       @param e 置換文字列
    */
    public void addSubstitution(final Exportable e) {
	//System.err.printf("[%d] %s%n", substitution.size(), e.toString());//
	substitution.add(e.clone());
    }

    /**
       置換文字列を取得します。

       @param k シーケンスID
       @return 置換文字列
    */
    public Exportable getSubstitution(final int k) {
	return substitution.get(k);
    }

    /**
       パース位置を進めてシーケンスを更新します。

       @param k パース位置を進める文字数
    */
    private void advanceSequence(final int k) {
	sequence = sequence.subSequence(k, sequence.length());
	position += k;
    }

    /**
       修飾子が続く場合はそれをパースして、指定された集合に出現した修
       飾子文字列を追加します。

       [rkV]*をパースします。コンテキストは[rKV]以外の文字のところまで
       進みます。

       @param qualifiers 修飾子文字列を追加する集合
    */
    public void parseQualifier(final Collection<String> qualifiers) {
	int k;
	String q;

	for (k = 0; (q = qualifierMap.get(sequence.charAt(k))) != null; ++k) {
	    qualifiers.add(q);
	}
	advanceSequence(k);
    }

    /**
       指定された文字数だけコンテキストから取得します。

       コンテキストは指定された文字数分進みます。

       @param len 文字数
       @return シーケンス
    */
    public CharSequence getSequence(final int len) {
	CharSequence seq;
	try {
	    seq = sequence.subSequence(0, len);
	} catch (IndexOutOfBoundsException e) {
	    throw new IllegalArgumentException("can't demangle: " + this);
	}
	advanceSequence(len);
	return seq;
    }

    /**
       コンテキストの先頭に対して正規表現パターンのマッチを実行し、マッ
       チする場合はそのマッチャを取得します。マッチしない場合はnullを
       返します。

       コンテキストはパターンにマッチした文字数分進みます。

       @param pattern 正規表現パターン
       @return マッチャ、またはnull
    */
    public Matcher matches(final Pattern pattern) {
	Matcher m = pattern.matcher(sequence);
	if (!m.lookingAt()) {
	    return null;
	}
	advanceSequence(m.end());
	return m;
    }

    /**
       コンテキストの先頭の文字が指定の文字かどうかを取得します。

       指定の文字の場合は、コンテキストは1文字分進みます。

       @param c 文字
       @return 指定の文字の場合はtrue
    */
    public boolean startsWith(final char c) {
	if (sequence.length() == 0
	    || sequence.charAt(0) != c) {
	    return false;
	}
	advanceSequence(1);
	return true;
    }

    /**
       コンテキストの先頭の文字を取得します。

       コンテキストは1文字分進みます。

       @return 文字
    */
    public char getChar() {
	char c = sequence.charAt(0);
	advanceSequence(1);
	return c;
    }

    /**
       コンテキストの先頭の文字を取得します。

       コンテキストは進みません。

       @return 文字
    */
    public char peekChar() {
	return sequence.charAt(0);
    }

    /**
       ディスクリミネータをスキップします。
    */
    public void skipDiscriminator() {
	if (!startsWith('_') || matches(RE.NUMBER) != null) {
	    return;
	}
	throw new IllegalArgumentException("can't demangle: " + this);
    }
}
