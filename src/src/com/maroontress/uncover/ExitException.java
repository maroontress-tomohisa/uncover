package com.maroontress.uncover;

/**
   ���ޥ�ɤν�λ�򥹥����ޤ���
*/
public class ExitException extends RuntimeException {
    /** ��λ���ơ������Ǥ��� */
    private int exitStatus;

    /**
       ���󥹥��󥹤��������ޤ���

       @param exitStatus ��λ���ơ�����
    */
    public ExitException(final int exitStatus) {
        this.exitStatus = exitStatus;
    }

    /**
       ��λ���ơ�������������ޤ���

       @return ��λ���ơ�����
    */
    public final int getExitStatus() {
        return exitStatus;
    }
}
