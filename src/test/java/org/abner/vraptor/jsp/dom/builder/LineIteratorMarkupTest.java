package org.abner.vraptor.jsp.dom.builder;

import org.junit.Assert;
import org.junit.Test;


public class LineIteratorMarkupTest {

    @Test
    public void singleElement() {
        String line = "<justatest/>";
        LineIterator iterator = new LineIterator(line);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(line, iterator.next());
        Assert.assertEquals(0, iterator.getColIndex());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void twoElements() {
        LineIterator iterator = new LineIterator("<justa/><test/>");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<justa/>", iterator.next());
        Assert.assertEquals(0, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<test/>", iterator.next());
        Assert.assertEquals(8, iterator.getColIndex());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void twoElementsWithSpacingBetween() {
        LineIterator iterator = new LineIterator("<justa/>       <test/>");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<justa/>", iterator.next());
        Assert.assertEquals(0, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<test/>", iterator.next());
        Assert.assertEquals(15, iterator.getColIndex());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void twoElementsWithSpacingBefore() {
        LineIterator iterator = new LineIterator("  <justa/><test/>");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<justa/>", iterator.next());
        Assert.assertEquals(2, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<test/>", iterator.next());
        Assert.assertEquals(10, iterator.getColIndex());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void twoElementsWithSpacingAfter() {
        LineIterator iterator = new LineIterator("<justa/><test/>        ");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<justa/>", iterator.next());
        Assert.assertEquals(0, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<test/>", iterator.next());
        Assert.assertEquals(8, iterator.getColIndex());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void threeElements() {
        LineIterator iterator = new LineIterator("<c:if><a/></c:if>");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<c:if>", iterator.next());
        Assert.assertEquals(0, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<a/>", iterator.next());
        Assert.assertEquals(6, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("</c:if>", iterator.next());
        Assert.assertEquals(10, iterator.getColIndex());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void elementWithChild() {
        LineIterator iterator = new LineIterator("<c:if test=\"${endereco.tipoBacklight}\">xpto</c:if>");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<c:if", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("test=\"${endereco.tipoBacklight}\">", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("xpto", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("</c:if>", iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void elementWithChild2() {
        LineIterator iterator = new LineIterator("<x:td>${entity.descricao}</x:td>");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<x:td>", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("${entity.descricao}", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("</x:td>", iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }


}
