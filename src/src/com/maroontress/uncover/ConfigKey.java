package com.maroontress.uncover;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.TreeSet;

/**
   設定のキーです。
*/
public final class ConfigKey {
    /** デフォルトのデータベースファイルのキーです。 */
    public static final String DB_DEFAULT = "db.default";

    /** コンストラクタは使用できません。 */
    private ConfigKey() {
    }

    /**
       設定キーのセットを取得します。

       @return 設定キーのセット
    */
    public static Set<String> keySet() {
	try {
	    Set<String> set = new TreeSet<String>();
	    Field[] allFields = ConfigKey.class.getDeclaredFields();
	    for (Field field : allFields) {
		String val = field.getName();
		set.add((String) field.get(null));
	    }
	    return set;
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error.", e);
	}
    }
}
