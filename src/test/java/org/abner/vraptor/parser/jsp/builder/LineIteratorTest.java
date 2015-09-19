package org.abner.vraptor.parser.jsp.builder;

import org.junit.Assert;
import org.junit.Test;


public class LineIteratorTest {

    @Test
    public void emptyLine() {
        LineIterator iterator = new LineIterator("");
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void emptySpaceLine() {
        LineIterator iterator = new LineIterator("      ");
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void singleWord() {
        String line = "justatest";
        LineIterator iterator = new LineIterator(line);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(line, iterator.next());
        Assert.assertEquals(0, iterator.getColIndex());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void twoWords() {
        LineIterator iterator = new LineIterator("justa test");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("justa", iterator.next());
        Assert.assertEquals(0, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("test", iterator.next());
        Assert.assertEquals(6, iterator.getColIndex());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void twoWordsWithSpacingBetween() {
        LineIterator iterator = new LineIterator("justa       test");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("justa", iterator.next());
        Assert.assertEquals(0, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("test", iterator.next());
        Assert.assertEquals(12, iterator.getColIndex());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void twoWordsWithSpacingBefore() {
        LineIterator iterator = new LineIterator("  justa test");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("justa", iterator.next());
        Assert.assertEquals(2, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("test", iterator.next());
        Assert.assertEquals(8, iterator.getColIndex());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void twoWordsWithSpacingAfter() {
        LineIterator iterator = new LineIterator("justa test        ");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("justa", iterator.next());
        Assert.assertEquals(0, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("test", iterator.next());
        Assert.assertEquals(6, iterator.getColIndex());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void threeWords() {
        LineIterator iterator = new LineIterator(" just  a  test  ");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("just", iterator.next());
        Assert.assertEquals(1, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("a", iterator.next());
        Assert.assertEquals(7, iterator.getColIndex());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("test", iterator.next());
        Assert.assertEquals(10, iterator.getColIndex());
        Assert.assertFalse(iterator.hasNext());
    }

}
