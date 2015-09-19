package org.abner.vraptor.parser.jsp.builder;

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
        LineIterator iterator = new LineIterator(" <just/><a/><test/>");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<just/>", iterator.next());
        Assert.assertEquals(1, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<a/>", iterator.next());
        Assert.assertEquals(8, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("<test/>", iterator.next());
        Assert.assertEquals(12, iterator.getColIndex());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void threeElem2ents() {
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
}
