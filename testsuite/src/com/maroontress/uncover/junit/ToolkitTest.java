package com.maroontress.uncover.junit;

import com.maroontress.uncover.Toolkit;
import com.maroontress.uncover.DefaultToolkit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ToolkitTest {
    private Toolkit myToolkit;

    @Before public void init() {
	myToolkit = new DefaultToolkit();
    }

    @Test public void getInstance() {
	assertTrue(Toolkit.getInstance() instanceof DefaultToolkit);
    }

    @Test public void setInstance() {
	Toolkit.setInstance(myToolkit);
	assertSame(Toolkit.getInstance(), myToolkit);
    }
}
