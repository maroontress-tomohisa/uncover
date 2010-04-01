package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;

/**
   フィールドの配列を操作するためのクラスです。
*/
public final class FieldArray {
    /** クラスからSQL型の文字列に変換するマップです。 */
    private static Map<Class<?>, String> typeMap;

    static {
	typeMap = new HashMap<Class<?>, String>();
	typeMap.put(int.class, "INTEGER");
	typeMap.put(long.class, "INTEGER");
	typeMap.put(String.class, "TEXT");
    }

    /**
       コンストラクタは使用できません。
    */
    private FieldArray() {
    }

    /**
       フィールド名に区切り文字列をはさんで連結した文字列を取得します。

       @param allFields フィールドの配列
       @param separator フィールド名の区切り文字列
       @return フィールド名を区切り文字列をはさんで連結した文字列
    */
    public static String concatNames(final Field[] allFields,
				     final String separator) {
	String s = "";
	String prefix = "";
	for (Field field : allFields) {
	    s += prefix + field.getName();
	    prefix = separator;
	}
	return s;
    }

    /**
       クラスのフィールド名に区切り文字列をはさんで連結した文字列を取
       得します。

       @param clazz クラス
       @param separator フィールド名の区切り文字列
       @return フィールド名を区切り文字列をはさんで連結した文字列
    */
    public static String concatNames(final Class<? extends Row> clazz,
				     final String separator) {
	return concatNames(clazz.getDeclaredFields(), separator);
    }

    /**
       フィールド名に区切り文字列をはさんで連結した文字列を取得します。

       @param allFields フィールドの配列
       @param separator フィールド名の区切り文字列
       @return フィールド名を区切り文字列をはさんで連結した文字列
    */
    public static String concatNameTypes(final Field[] allFields,
					 final String separator) {
	String s = "";
	String prefix = "";
	for (Field field : allFields) {
	    String sqlType = typeMap.get(field.getType());
	    if (sqlType == null) {
		throw new RuntimeException("internal error: unexpected type.");
	    }
	    s += prefix + field.getName() + " " + sqlType;
	    prefix = separator;
	}
	return s;
    }

    /**
       クラスのフィールド名と型名を空白で連結したものを、さらに区切り
       文字列をはさんで連結した文字列を取得します。

       @param clazz クラス
       @param separator フィールド名の区切り文字列
       @return フィールド名と型名を空白で連結したものを区切り文字列を
       はさんで連結した文字列
    */
    public static String concatNameTypes(final Class<? extends Row> clazz,
					 final String separator) {
	return concatNameTypes(clazz.getDeclaredFields(), separator);
    }
}
