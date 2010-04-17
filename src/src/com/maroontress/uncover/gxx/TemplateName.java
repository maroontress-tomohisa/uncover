package com.maroontress.uncover.gxx;

/**
   フルネームと短縮形の名前を保持します。
*/
public final class TemplateName {
    /** コンポーネントの通称です。 */
    private Component name;

    /** コンポーネントのフルネームです。 */
    private Component fullName;

    /**
       インスタンスを生成します。

       @param name 通称
       @param fullName フルネーム
       @param args 引数
    */
    public TemplateName(final CharSequence name,
			final CharSequence fullName,
			final String args) {
	this.name = new SourceName(name);
	this.fullName = new TemplatedComponent(new SourceName(fullName), args);
    }

    /**
       インスタンスを生成します。

       @param name 名前
    */
    public TemplateName(final CharSequence name) {
	this.name = new SourceName(name);
	this.fullName = this.name;
    }

    /**
       通称のコンポーネントを取得します。

       @return 通称のコンポーネント
    */
    public Component getName() {
	return name;
    }

    /**
       フルネームのコンポーネントを取得します。

       @return フルネームのコンポーネント
    */
    public Component getFullName() {
	return fullName;
    }
}
