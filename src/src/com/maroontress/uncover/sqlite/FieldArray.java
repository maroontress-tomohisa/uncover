package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;

/**
   フィールドの配列を操作するためのクラスです。
*/
public final class FieldArray {
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
    public static String concatNames(final Class clazz,
				     final String separator) {
	return concatNames(clazz.getDeclaredFields(), separator);
    }
}
