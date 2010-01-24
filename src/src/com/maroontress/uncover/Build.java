package com.maroontress.uncover;

/**
   �ӥ�ɤ˴ؤ������򥫥ץ��벽���ޤ���
*/
public final class Build implements BuildSource {
    /** ID�Ǥ��� */
    private String id;

    /** ��ӥ����Ǥ��� */
    private String revision;

    /** �����ॹ����פǤ��� */
    private String timestamp;

    /** �ץ�åȥե�����Ǥ��� */
    private String platform;

    /**
       �ǥե���ȥ��󥹥ȥ饯���ϻ��ѤǤ��ޤ���
    */
    private Build() {
    }

    /**
       ���󥹥��󥹤��������ޤ���

       @param s �ӥ�ɥ�����
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
