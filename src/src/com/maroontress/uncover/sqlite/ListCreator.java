package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
   SQLステートメントから要素を生成してリストを生成する機能を提供します。

   @param <X> 生成するリストの要素となるクラス
*/
public abstract class ListCreator<X> {
    /** JDBCの接続です。 */
    private Connection con;

    /**
       デフォルトコンストラクタは利用できません。
    */
    private ListCreator() {
    }

    /**
       インスタンスを生成します。

       @param con JDBCの接続
    */
    public ListCreator(final Connection con) {
	this.con = con;
    }

    /**
       生成した要素を取得します。

       @param toolkit ツールキットのインスタンス
       @return 生成した要素
    */
    public abstract X create(Toolkit toolkit);

    /**
       リザルトセットを設定する行を取得します。

       @return リザルトセットを設定する行
    */
    public abstract Row getRow();

    /**
       SQLステートメントとパラメータから要素を生成して、リストとして取
       得します。

       @param template クエリ文字列
       @param params クエリのパラメータ
       @return 生成した要素のリスト
       @throws SQLException エラーが発生したときにスローします。
    */
    public final List<X> getList(final String template,
				 final Object[] params) throws SQLException {
	PreparedStatement s = con.prepareStatement(template);
	setParameter(s, params);
	ResultSet rs = s.executeQuery();

	Toolkit toolkit = Toolkit.getInstance();
	Row source = getRow();
	List<X> list = new ArrayList<X>();
	while (rs.next()) {
	    source.setResultSet(rs);
	    list.add(create(toolkit));
	}
	return list;
    }

    /**
       コンパイル済みのステートメントにパラメータを設定します。

       @param s ステートメント
       @param values パラメータの値の配列
       @throws SQLException エラーが発生したときにスローします。
    */
    private static void setParameter(final PreparedStatement s,
				     final Object[] values)
	throws SQLException {
	int k = 0;
	for (Object v : values) {
	    ++k;
	    if (v instanceof String) {
		s.setString(k, (String) v);
	    } else if (v instanceof Integer) {
		s.setInt(k, (Integer) v);
	    } else {
		throw new IllegalArgumentException("unexpected type: "
						   + v.getClass().toString());
	    }
	}
    }
}
