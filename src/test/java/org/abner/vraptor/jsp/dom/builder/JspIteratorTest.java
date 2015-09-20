package org.abner.vraptor.jsp.dom.builder;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

public class JspIteratorTest {

    @Test
    public void test() {
        InputStream is = JspIteratorTest.class.getClassLoader().getResourceAsStream("jsp-iterator-test.xml");
        JspIterator iterator = new JspIterator(is);
        while (iterator.hasNext()) {
            Assert.assertNotEquals("", iterator.next());
        }
    }
}
