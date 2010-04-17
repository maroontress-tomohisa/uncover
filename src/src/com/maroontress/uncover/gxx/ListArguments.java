package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;

/**
*/
public final class ListArguments extends TemplateArguments {
    /** テンプレートの引数のリストです。 */
    private List<Type> argList;

    /**
       インスタンスを生成します。

       コンテキストからテンプレート引数をパースします。パースした型を
       引数として追加します。

       コンテキストはテンプレート引数の終端となるEまで進みます。

       @param context コンテキスト
    */
    public ListArguments(final Context context) {
	argList = new ArrayList<Type>();
	do {
	    argList.add(Type.create(context));
	} while (!context.startsWith('E'));
    }

    /** {@inheritDoc} */
    @Override public void exportArgs(final Exporter b) {
	argList.get(0).export(b);
	for (int k = 1; k < argList.size(); ++k) {
	    b.append(", ");
	    argList.get(k).export(b);
	}
    }
}
