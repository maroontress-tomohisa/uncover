package com.maroontress.uncover.gxx;

/**
   テンプレート引数付きコンポーネントです。
*/
public final class TemplatedComponent extends Component {
    /** エクスポータブルです。 */
    private Exportable name;

    /** エクスポータブルのテンプレート引数です。 */
    private TemplateArguments args;

    /**
       インスタンスを生成します。

       @param name エクスポータブル
       @param args テンプレート引数
    */
    private TemplatedComponent(final Exportable name,
			       final TemplateArguments args) {
	this.name = name;
	this.args = args;
    }

    /**
       インスタンスを生成します。

       @param name エクスポータブル
       @param context コンテキスト
    */
    public TemplatedComponent(final Exportable name, final Context context) {
	this(name, TemplateArguments.create(context));
    }

    /**
       インスタンスを生成します。

       @param name エクスポータブル
       @param args 引数
    */
    public TemplatedComponent(final Exportable name, final String args) {
	this(name, TemplateArguments.create(args));
    }

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	name.export(b);
	args.export(b);
    }

    /** {@inheritDoc} */
    @Override public void exportName(final Exporter b) {
	name.export(b);
    }
}
