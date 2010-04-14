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

    /** �ѡ����оݤΥ������󥹤Ǥ��� */
    private CharSequence sequence;

    /** �ִ�ʸ����Υꥹ�ȤǤ��� */
    private List<Exportable> substitution;

    /**
       ���󥹥��󥹤��������ޤ���

       @param sequence �ѡ������륷������
    */
    public Context(final String sequence) {
	this.sequence = sequence;
	substitution = new ArrayList<Exportable>();
    }

    /** {@inheritDoc} */
    public String toString() {
	return sequence.toString();
    }

    /**
       �ִ�ʸ������ɲä��ޤ���

       �ִ�ʸ��������Ƥϥ��ԡ����ƥ���ƥ������������ݻ����ޤ�������
       �᥽�åɸƤӽФ����e���ѹ����Ƥ⡢�ɲä������Ƥ��Ѳ����ޤ���

       @param e �ִ�ʸ����
    */
    public void addSubstitution(final Exportable e) {
	//System.err.printf("[%d] %s%n", substitution.size(), e.toString());
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
       �ѡ������֤�ʤ�ޤ���

       @param k �ѡ������֤�ʤ��ʸ����
    */
    private void advanceSequence(final int k) {
	sequence = sequence.subSequence(k, sequence.length());
    }

    /**
       �����Ҥ�³�����Ϥ����ѡ������ơ����ꤵ�줿����˽и�������
       ����ʸ������ɲä��ޤ���

       [rkV]*��ѡ������ޤ�������ƥ����Ȥ�[rKV]�ʳ���ʸ���ΤȤ���ޤ�
       �ʤߤޤ���

       @param qualifiers ������ʸ������ɲä��뽸��
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
       ���ꤵ�줿ʸ������������ƥ����Ȥ���������ޤ���

       ����ƥ����Ȥϻ��ꤵ�줿ʸ����ʬ�ʤߤޤ���

       @param len ʸ����
       @return ��������
    */
    public CharSequence getSequence(final int len) {
	CharSequence seq;
	try {
	    seq = sequence.subSequence(0, len);
	} catch (IndexOutOfBoundsException e) {
	    throw new IllegalArgumentException("can't demangle: " + this, e);
	}
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
       ����ƥ����Ȥ���Ƭ��ʸ���������ʸ�����ɤ�����������ޤ���

       �����ʸ���ξ��ϡ�����ƥ����Ȥ�1ʸ��ʬ�ʤߤޤ���

       @param c ʸ��
       @return �����ʸ���ξ���true
    */
    public boolean startsWith(final char c) {
	if (sequence.charAt(0) != c) {
	    return false;
	}
	advanceSequence(1);
	return true;
    }

    /**
       ����ƥ����Ȥ���Ƭ��ʸ����������ޤ���

       ����ƥ����Ȥ�1ʸ��ʬ�ʤߤޤ���

       @return ʸ��
    */
    public char getChar() {
	char c = sequence.charAt(0);
	advanceSequence(1);
	return c;
    }
}
