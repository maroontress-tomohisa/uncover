package com.maroontress.uncover.gxx;

import java.util.HashMap;
import java.util.Map;

/**
   組み込みの型名です。
*/
public final class BuiltinType {
    /** 文字と組み込みの型名のマップです。 */
    private static Map<Character, String> map;

    static {
	map = new HashMap<Character, String>();
	map.put('a', "signed char");
	map.put('b', "bool");
	map.put('c', "char");
	map.put('d', "double");
	map.put('e', "long double");
	map.put('f', "float");
	map.put('g', "__float128");
	map.put('h', "unsigned char");
	map.put('i', "int");
	map.put('j', "unsigned int");
	map.put('l', "long");
	map.put('m', "unsigned long");
	map.put('n', "__int128");
	map.put('o', "unsigned __int128");
	map.put('s', "short");
	map.put('t', "unsigned short");
	map.put('v', "void");
	map.put('w', "wchar_t");
	map.put('x', "long long");
	map.put('y', "unsigned long long");
	map.put('z', "...");
    }

    /**
       コンストラクタは使用できません。
    */
    private BuiltinType() {
    }

    /**
       文字に対応する組み込みの型名を取得します。

       対応する型名が存在しない場合はnullを返します。

       @param key 文字
       @return 型名、またはnull
    */
    public static String getName(final char key) {
	return map.get(key);
    }
}
