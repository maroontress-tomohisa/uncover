package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
   �ǥޥ󥰥�ѡ����Υ���ƥ����ȤǤ���
*/
public final class Context {
    /** �����ҤΥޥåפǤ��� */
    private static Map<Character, String> qualifierMap;

    static {
	qualifierMap = new HashMap<Character, String>();
	qualifierMap.put('r', "restrict");
	qualifierMap.put('K', "const");
	qualifierMap.put('V', "volatile");
    }

    /** ���ꥸ�ʥ�Υ������󥹤Ǥ��� */
    private String source;

    /** �ѡ����оݤΥ������󥹤Ǥ��� */
    private CharSequence sequence;

    /** ���ꥸ�ʥ���Ф��ƥ������󥹤��Ϥޤ���֤Ǥ��� */
    private int position;

    /** �ִ�ʸ����Υꥹ�ȤǤ��� */
    private List<Exportable> substitution;

    /**
       ���󥹥��󥹤��������ޤ���

       @param source �ѡ�������ʸ����
    */
    public Context(final String source) {
	this.source = source;
	sequence = source;
	position = 0;
	substitution = new ArrayList<Exportable>();
    }

    /**
       �ѡ������оݤȤ��ƻ��ꤵ�줿ʸ�����������ޤ���

       @return �ѡ�������ʸ����
    */
    public String getSource() {
	return source;
    }

    /** {@inheritDoc} */
    public String toString() {
	return sequence.toString()
	    + ":" + position
	    + ":" + sequence.subSequence(position, sequence.length());
    }

    /**
       �ִ�ʸ������ɲä��ޤ���

       �ִ�ʸ��������Ƥϥ��ԡ����ƥ���ƥ������������ݻ����ޤ�������
       �᥽�åɸƤӽФ����e���ѹ����Ƥ⡢�ɲä������Ƥ��Ѳ����ޤ���

       @param e �ִ�ʸ����
    */
    public void addSubstitution(final Exportable e) {
	//System.err.printf("[%d] %s%n", substitution.size(), e.toString());//
	substitution.add(e.clone());
    }

    /**
       �ִ�ʸ�����������ޤ���

       @param k ��������ID
       @return �ִ�ʸ����
    */
    public Exportable getSubstitution(final int k) {
	return substitution.get(k);
    }

    /**
       �ѡ������֤�ʤ�ƥ������󥹤򹹿����ޤ���

       @param k �ѡ������֤�ʤ��ʸ����
    */
    private void advanceSequence(final int k) {
	sequence = sequence.subSequence(k, sequence.length());
	position += k;
    }

    /**
       �����Ҥ�³�����Ϥ����ѡ������ơ����ꤵ�줿����˽и�������
       ����ʸ������ɲä��ޤ���

       [rkV]*��ѡ������ޤ�������ƥ����Ȥ�[rKV]�ʳ���ʸ���ΤȤ���ޤ�
       �ʤߤޤ���

       @param qualifiers ������ʸ������ɲä��뽸��
       @throws ContextException ����ƥ����Ȥ���ü��ã���Ƥ������ޤ���
       �����Ҥ�ѡ�����˽�ü��ã������祹�����ޤ���
    */
    public void parseQualifier(final Collection<String> qualifiers) {
	if (sequence.length() == 0) {
	    throw new ContextException(this);
	}
	int n = sequence.length();
	int k;
	String q;

	for (k = 0;
	     k < n && (q = qualifierMap.get(sequence.charAt(k))) != null;
	     ++k) {
	    qualifiers.add(q);
	}
	if (k == n) {
	    throw new ContextException(this);
	}
	advanceSequence(k);
    }

    /**
       ���ꤵ�줿ʸ������������ƥ����Ȥ���������ޤ���

       ����ƥ����Ȥϻ��ꤵ�줿ʸ����ʬ�ʤߤޤ���

       @param len ʸ����
       @return ��������
       @throws ContextException ��ü��ۤ���Ĺ����ʸ��������ꤷ�����
       �������ޤ���
    */
    public CharSequence getSequence(final int len) {
	if (len > sequence.length()) {
	    throw new ContextException(this);
	}
	CharSequence seq = sequence.subSequence(0, len);
	advanceSequence(len);
	return seq;
    }

    /**
       ����ƥ����Ȥ���Ƭ���Ф�������ɽ���ѥ�����Υޥå���¹Ԥ����ޥ�
       ��������Ϥ��Υޥå����������ޤ����ޥå����ʤ�����null��
       �֤��ޤ���

       ����ƥ����Ȥϥѥ�����˥ޥå�����ʸ����ʬ�ʤߤޤ���

       @param pattern ����ɽ���ѥ�����
       @return �ޥå��㡢�ޤ���null
       @throws ContextException ����ƥ����Ȥ���ü��ã���Ƥ�����祹��
       ���ޤ���
    */
    public Matcher matches(final Pattern pattern) {
	if (sequence.length() == 0) {
	    throw new ContextException(this);
	}
	Matcher m = pattern.matcher(sequence);
	if (!m.lookingAt()) {
	    return null;
	}
	advanceSequence(m.end());
	return m;
    }

    /**
       ����ƥ����Ȥ���Ƭ��ʸ���������ʸ�����ɤ�����������ޤ���

       �����ʸ���ξ��ϡ�����ƥ����Ȥ�1ʸ��ʬ�ʤߡ�true���֤��ޤ���
       �����Ǥʤ���������ƥ����Ȥ���ü��ã���Ƥ������ϡ�����false��
       �֤��ޤ���

       @param c ʸ��
       @return �����ʸ���ξ���true
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
       ����ƥ����Ȥ���Ƭ��ʸ����������ޤ���

       ����ƥ����Ȥ�1ʸ��ʬ�ʤߤޤ���

       @return ʸ��
       @throws ContextException ����ƥ����Ȥ���ü��ã���Ƥ�����祹��
       ���ޤ���
    */
    public char getChar() {
	if (sequence.length() == 0) {
	    throw new ContextException(this);
	}
	char c = sequence.charAt(0);
	advanceSequence(1);
	return c;
    }

    /**
       ����ƥ����Ȥ���Ƭ��ʸ����������ޤ���

       ����ƥ����ȤϿʤߤޤ���

       @return ʸ��
       @throws ContextException ����ƥ����Ȥ���ü��ã���Ƥ�����祹��
       ���ޤ���
    */
    public char peekChar() {
	if (sequence.length() == 0) {
	    throw new ContextException(this);
	}
	return sequence.charAt(0);
    }

    /**
       �ǥ�������ߥ͡����򥹥��åפ��ޤ���

       @throws ContextException �ǥ�������ߥ͡�����¸�ߤ������줬����
       �ʾ�祹�����ޤ���
    */
    public void skipDiscriminator() {
	if (startsWith('_') && matches(RE.NUMBER) == null) {
	    throw new ContextException(this);
	}
    }
}
