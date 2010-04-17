package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;

/**
   関数の型です。
*/
public final class FunctionType extends Type {
    /** 戻り値の型です。 */
    private Type returnType;

    /** 引数の型のリストです。 */
    private List<Type> argTypeList;

    /**
       配列の要素の型と数でインスタンスを生成します。

       @param context コンテキスト
    */
    public FunctionType(final Context context) {
	argTypeList = new ArrayList<Type>();
	returnType = Type.create(context);
	while (!context.startsWith('E')) {
	    argTypeList.add(Type.create(context));
	}
    }

    /**
       引数の型を出力します。

       @param b エクスポータ
    */
    private void exportArgs(final Exporter b) {
	int n = argTypeList.size();
	if (n == 0
	    || (n == 1 && argTypeList.get(0).toString().equals("void"))) {
	    return;
	}
	argTypeList.get(0).export(b);
	for (int k = 1; k < n; ++k) {
	    b.append(", ");
	    argTypeList.get(k).export(b);
	}
    }

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	returnType.export(b);
	b.append(" (");
	if (hasQualifiers()) {
	    exportQualifiers(b);
	}
	b.append(")(");
	exportArgs(b);
	b.append(")");
    }
}
