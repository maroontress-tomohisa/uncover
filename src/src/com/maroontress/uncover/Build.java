package com.maroontress.uncover;

/**
   ビルドに関する情報をカプセル化します。
*/
public final class Build implements BuildSource {
    /** IDです。 */
    private String id;

    /** リビジョンです。 */
    private String revision;

    /** タイムスタンプです。 */
    private String timestamp;

    /** プラットフォームです。 */
    private String platform;

    /**
       デフォルトコンストラクタは使用できません。
    */
    private Build() {
    }

    /**
       インスタンスを生成します。

       @param s ビルドソース
    */
    public Build(final BuildSource s) {
	id = s.getID();
	revision = s.getRevision().intern();
	timestamp = s.getTimestamp();
	platform = s.getPlatform().intern();
    }

    /** {@inheritDoc} */
    public String getID() {
	return id;
    }

    /** {@inheritDoc} */
    public String getRevision() {
	return revision;
    }

    /** {@inheritDoc} */
    public String getTimestamp() {
	return timestamp;
    }

    /** {@inheritDoc} */
    public String getPlatform() {
	return platform;
    }
}
