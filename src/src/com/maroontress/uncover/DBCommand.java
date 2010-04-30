package com.maroontress.uncover;

/**
   �ǡ����١����˥����������륳�ޥ�ɤǤ���
*/
public abstract class DBCommand extends Command {
    /**
       ���ޥ�ɤ��������ޤ���

       @param props �ץ�ѥƥ�
    */
    protected DBCommand(final Properties props) {
	super(props);
    }

    /**
       ���ޥ�ɤ�¹Ԥ��ޤ���

       @param db �ǡ����١���
       @throws CommandException ���ޥ�ɤμ¹Ԥ˴ؤ��륨�顼��ȯ������
       �Ȥ��˥������ޤ���
    */
    protected abstract void run(DB db) throws CommandException;

    /**
       ���ޥ�ɤ�¹Ԥ��ޤ���

       @param subname �ǡ����١����ե�����Υѥ�
       @throws CommandException ���ޥ�ɤμ¹Ԥ˴ؤ��륨�顼��ȯ������
       �Ȥ��˥������ޤ���
    */
    private void runCommand(final String subname) throws CommandException {
	DB db;
	try {
	    db = Toolkit.getInstance().createDB(subname);
	} catch (DBException e) {
	    throw new CommandException("can't open database.", e);
	}
	run(db);
	try {
	    db.close();
	} catch (DBException e) {
	    throw new CommandException("can't close database.", e);
	}
    }

    /**
       ���ޥ�ɤμ¹Ԥ�ȯ�������㳰��������ޤ���

       @param e ���ޥ�ɤμ¹Ԥ˴ؤ����㳰
    */
    private void handleException(final CommandException e) {
	String subname = getProperties().getDBFile();
	try {
	    throw e.getCause();
	} catch (DBException cause) {
	    System.err.println(subname + ": " + e.getMessage());
	    cause.printStackTrace();
	} catch (MultipleBuildsException cause) {
	    System.err.println(subname + ": " + e.getMessage());
	    cause.printDescription(System.err);
	} catch (Throwable cause) {
	    cause.printStackTrace();
	}
    }

    /**
       ���ޥ�ɤ�¹Ԥ��ޤ���
    */
    public final void run() {
	String subname = getProperties().getDBFile();
	if (subname == null) {
	    System.err.println("database file not specified.");
	    usage();
	}
	try {
	    runCommand(subname);
	} catch (CommandException e) {
	    if (e.getCause() == null) {
		e.printStackTrace();
	    } else {
		handleException(e);
	    }
	    throw new ExitException(1);
	}
    }

    /**
       �ץ�������̾�ȥ�ӥ���󡢤ޤ��ϥӥ��ID����ꤷ�ơ��ӥ�ɤ�
       �������ޤ���

       @param db �ǡ����١���
       @param projectName �ץ�������̾
       @param rev ��ӥ���󡢤ޤ��ϥӥ��ID
       @param howToFix ���ꤷ����ӥ�����ʣ���Υӥ�ɤ�¸�ߤ���Ȥ���
       �ɤΤ褦�ˤ���Хӥ�ɤ�����Ǥ��뤫����������ʸ����
       @return �ӥ��
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
       @throws MultipleBuildsException ���ꤷ����ӥ�����ʣ���Υӥ�
       �ɤ�¸�ߤ���Ȥ��˥������ޤ���
    */
    protected static Build getBuild(final DB db, final String projectName,
				    final String rev, final String howToFix)
        throws DBException, MultipleBuildsException {
        if (rev.startsWith("@")) {
            return db.getBuild(projectName, rev.substring(1));
        }
        Build[] builds = db.getBuilds(projectName, rev);
        if (builds.length > 1) {
            throw new MultipleBuildsException(rev, builds, howToFix);
        }
        return builds[0];
    }
}
