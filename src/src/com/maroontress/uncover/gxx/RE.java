package com.maroontress.uncover.gxx;

import java.util.regex.Pattern;

/**
   パースに使う正規表現を提供します。
*/
public final class RE {
    /** デマングル開始のパターンです。 */
    public static final Pattern ENCODING;

    /** グローバルコンストラクタ/デストラクタ開始のパターンです。 */
    public static final Pattern GLOBAL_CTOR_DTOR;

    /** 正の整数にマッチするパターンです。 */
    public static final Pattern NUMBER;

    /** コンストラクタのパターンです。 */
    public static final Pattern CTOR;

    /** デストラクタのパターンです。 */
    public static final Pattern DTOR;

    /** 演算子のパターンです。 */
    public static final Pattern OPERATOR;

    /** 匿名名前空間のパターンです。 */
    public static final Pattern ANONYMOUS_NS;

    /** 置換文字列のシーケンスIDのパターンです。 */
    public static final Pattern SEQ_ID;

    static {
	NUMBER = Pattern.compile("[1-9][0-9]*");
	CTOR = Pattern.compile("C[123]");
	DTOR = Pattern.compile("D[012]");
	OPERATOR = Pattern.compile("[a-z][A-Za-z]");
	ANONYMOUS_NS = Pattern.compile("_GLOBAL_[._$]N");
	ENCODING = Pattern.compile("_Z");
	GLOBAL_CTOR_DTOR = Pattern.compile("_GLOBAL_[._$]([DI])_");
	SEQ_ID = Pattern.compile("([0-9A-Z]+)_");
    }

    /**
       コンストラクタは使用できません。
    */
    private RE() {
    }
}
