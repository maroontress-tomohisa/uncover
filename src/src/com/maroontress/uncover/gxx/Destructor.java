package com.maroontress.uncover.gxx;

/**
   �ǥ��ȥ饯������ݡ��ͥ�ȤǤ���
*/
public final class Destructor extends Component {
    /**
       ���󥹥��󥹤��������ޤ���
    */
    public Destructor() {
    }

    /** {@inheritDoc} */
    public void export(final Exporter b) {
	b.append("~");
	b.appendComponent();
    }
}
