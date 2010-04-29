package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.DBException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
*/
public final class ConnectionFactory {
    /** */
    private static final String EXT_DIRS = "com.maroontress.uncover.ext.dirs";

    /** */
    private static final String CLASS_PATH = "java.class.path";

    /** */
    private static final String JDBC_SKIRT_JAR = "jdbcskirt.jar";

    /** */
    private static final String SKIRT = "com.maroontress.jdbcskirt.Skirt";

    /** */
    private static final String GET_CONNECTION = "getConnection";

    /**
       コンストラクタは使用できません。
    */
    private ConnectionFactory() {
    }

    /**
       システムプロパティを取得します。

       @param key プロパティ名
       @return プロパティの値
    */
    private static String getProperty(final String key) {
	return System.getProperty(key);
    }

    /**
       JDBCのコネクションを取得します。

       @param className JDBCドライバのクラス名
       @param jdbcURL JDBCのデータベースURL
       @return JDBCのコネクション
       @throws DBException コネクションを取得できない場合にスローします。
    */
    public static Connection createConnection(
	final String className, final String jdbcURL) throws DBException {
	List<File> list = new ArrayList<File>();

	String extDirs = getProperty(EXT_DIRS);
	if (extDirs != null) {
	    String[] dirs = extDirs.split(File.pathSeparator);
	    for (String d : dirs) {
		File path = new File(d);
		if (!path.exists()) {
		    continue;
		}
		if (!path.isDirectory()) {
		    list.add(path);
		    continue;
		}
		File[] children = path.listFiles();
		for (File child : children) {
		    if (child.isDirectory()) {
			continue;
		    }
		    list.add(child);
		}
	    }

	}
	File baseDir = new File(getProperty(CLASS_PATH)).getParentFile();
	File jdbcSkirtJar = new File(baseDir, JDBC_SKIRT_JAR);
	list.add(jdbcSkirtJar);

	try {
	    int size = list.size();
	    URL[] urls = new URL[size];
	    for (int k = 0; k < size; ++k) {
		urls[k] = list.get(k).toURI().toURL();
	    }
	    ClassLoader loader = new URLClassLoader(urls);
	    Class<?> driverLoaderClass = loader.loadClass(SKIRT);
	    Method method = driverLoaderClass.getMethod(
		GET_CONNECTION, String.class, String.class);
	    return (Connection) method.invoke(null, className, jdbcURL);
	} catch (MalformedURLException e) {
	    throw new DBException("internal error.", e);
	} catch (ClassNotFoundException e) {
	    throw new DBException("not found: " + jdbcSkirtJar, e);
	} catch (NoSuchMethodException e) {
	    throw new DBException("broken: " + jdbcSkirtJar, e);
	} catch (IllegalAccessException e) {
	    throw new DBException("broken: " + jdbcSkirtJar, e);
	} catch (InvocationTargetException e) {
	    throw new DBException("can't load JDBC driver: " + className, e);
	}
    }
}
