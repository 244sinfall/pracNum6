import org.junit.Assert;
import org.junit.Test;

public class RingedLinkedListTest {
    /**
     * Правильная иницциализация
     */
    @Test
    public void TestInstantiate() {
        String[] values8 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8"};
        String[] values16 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8",
                "String9", "String10", "String11", "String12", "String13", "String14", "String15", "String16" };
        RingedLinkedList<String> empty = new RingedLinkedList<>();
        RingedLinkedList<String> list8 = new RingedLinkedList<>(values8);
        RingedLinkedList<String> list16 = new RingedLinkedList<>(values16);
        Assert.assertTrue(empty.isEmpty());
        Assert.assertEquals(empty.size(), 0);
        Assert.assertEquals(list8.size(), 8);
        Assert.assertEquals(list16.size(), 16);
        Assert.assertArrayEquals(values8, list8.toArray());
        Assert.assertArrayEquals(values16, list16.toArray());
    }

    /**
     * Правильный перенос от начала к концу списка
     */
    @Test
    public void TestStartAndTail() {
        String[] values8 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8"};
        String[] values16 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8",
                "String9", "String10", "String11", "String12", "String13", "String14", "String15", "String16" };
        RingedLinkedList<String> list1 = new RingedLinkedList<>("value");
        RingedLinkedList<String> list8 = new RingedLinkedList<>(values8);
        RingedLinkedList<String> list16 = new RingedLinkedList<>(values16);
        list1.moveStart();
        Assert.assertArrayEquals(list1.getCurrentPointerValues(), new String[]{"value"});
        list1.moveTail();
        Assert.assertArrayEquals(list1.getCurrentPointerValues(), new String[]{"value"});
        list8.moveTail();
        Assert.assertArrayEquals(list8.getCurrentPointerValues(), new String[]{"String8", "String"});
        list8.moveStart();
        Assert.assertArrayEquals(list8.getCurrentPointerValues(), new String[]{"String", "String2"});
        list16.moveTail();
        Assert.assertArrayEquals(list16.getCurrentPointerValues(), new String[]{"String16", "String"});
        list16.moveStart();
        Assert.assertArrayEquals(list16.getCurrentPointerValues(), new String[]{"String", "String2"});
    }

    /**
     * Правильный шифт влево/вправо
     */
    @Test
    public void TestShift() {
        String[] values8 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8"};
        String[] values16 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8",
                "String9", "String10", "String11", "String12", "String13", "String14", "String15", "String16" };
        RingedLinkedList<String> empty = new RingedLinkedList<>();
        RingedLinkedList<String> list1 = new RingedLinkedList<>("value");
        RingedLinkedList<String> list8 = new RingedLinkedList<>(values8);
        RingedLinkedList<String> list16 = new RingedLinkedList<>(values16);
        Assert.assertFalse(empty.shiftNext());
        Assert.assertFalse(empty.shiftPrev());
        Assert.assertFalse(list1.shiftNext());
        Assert.assertFalse(list1.shiftPrev());
        Assert.assertArrayEquals(list1.getCurrentPointerValues(), new String[]{"value"});
        Assert.assertArrayEquals(list8.getCurrentPointerValues(), new String[]{"String", "String2"});
        Assert.assertArrayEquals(list16.getCurrentPointerValues(), new String[]{"String", "String2"});
        list1.shiftNext();
        list8.shiftNext();
        list16.shiftNext();
        Assert.assertArrayEquals(list1.getCurrentPointerValues(), new String[]{"value"});
        Assert.assertArrayEquals(list8.getCurrentPointerValues(), new String[]{"String2", "String3"});
        Assert.assertArrayEquals(list16.getCurrentPointerValues(), new String[]{"String2", "String3"});
        list1.shiftPrev();
        list1.shiftPrev();
        list8.shiftPrev();
        list8.shiftPrev();
        list16.shiftPrev();
        list16.shiftPrev();
        Assert.assertArrayEquals(list1.getCurrentPointerValues(), new String[]{"value"});
        Assert.assertArrayEquals(list8.getCurrentPointerValues(), new String[]{"String8", "String"});
        Assert.assertArrayEquals(list16.getCurrentPointerValues(), new String[]{"String16", "String"});
    }

    /**
     * Правильная замена значений
     */
    @Test
    public void TestReplace() {
        String[] values8 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8"};
        String[] values16 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8",
                "String9", "String10", "String11", "String12", "String13", "String14", "String15", "String16" };
        RingedLinkedList<String> empty = new RingedLinkedList<>();
        RingedLinkedList<String> list1 = new RingedLinkedList<>("value");
        RingedLinkedList<String> list8 = new RingedLinkedList<>(values8);
        RingedLinkedList<String> list16 = new RingedLinkedList<>(values16);
        Assert.assertFalse(empty.replacePrevAndNext());
        Assert.assertFalse(list1.replacePrevAndNext());
        list8.replacePrevAndNext();
        Assert.assertArrayEquals(list8.getCurrentPointerValues(), new String[]{"String2", "String"});
        list16.moveTail();
        list16.replacePrevAndNext();
        Assert.assertArrayEquals(list16.getCurrentPointerValues(), new String[]{"String", "String16"});
    }

    /**
     * Разница между двумя методами ниже в перемещении поинтера до удаления
     */
    @Test
    public void TestRemoveAfter() {
        String[] values8 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8"};
        String[] values16 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8",
                "String9", "String10", "String11", "String12", "String13", "String14", "String15", "String16" };
        RingedLinkedList<String> list1 = new RingedLinkedList<>("value");
        RingedLinkedList<String> list8 = new RingedLinkedList<>(values8);
        RingedLinkedList<String> list16 = new RingedLinkedList<>(values16);
        list1.removeAfter();
        Assert.assertTrue(list1.isEmpty());
        list8.shiftNext();
        list8.shiftNext();
        list8.removeAfter();
        Assert.assertArrayEquals(list8.getCurrentPointerValues(), new String[]{"String3", "String5"});
        Assert.assertEquals(list8.size(), 7);
        list16.shiftPrev();
        list16.shiftPrev();
        list16.removeAfter();
        Assert.assertArrayEquals(list16.getCurrentPointerValues(), new String[]{"String15", "String"});
        Assert.assertEquals(list16.size(), 15);
    }
    @Test
    public void TestRemoveBefore() {
        String[] values8 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8"};
        String[] values16 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8",
                "String9", "String10", "String11", "String12", "String13", "String14", "String15", "String16" };
        RingedLinkedList<String> list1 = new RingedLinkedList<>("value");
        RingedLinkedList<String> list8 = new RingedLinkedList<>(values8);
        RingedLinkedList<String> list16 = new RingedLinkedList<>(values16);
        list1.removeBefore();
        Assert.assertTrue(list1.isEmpty());
        list8.shiftNext();
        list8.shiftNext();
        list8.removeBefore();
        Assert.assertArrayEquals(list8.toArray(), new String[]{"String", "String2", "String4", "String5", "String6", "String7", "String8"});
        Assert.assertEquals(list8.size(), 7);
        list16.shiftPrev();
        list16.shiftPrev();
        list16.removeBefore();
        Assert.assertArrayEquals(list16.toArray(), new String[]{"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8",
                "String9", "String10", "String11", "String12", "String13", "String14", "String16" });
        Assert.assertEquals(list16.size(), 15);
    }

    /**
     * Разница между двумя методами ниже в перемещении поинтера после удаления
     */
    @Test
    public void TestAddAfter() {
        String[] values8 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8"};
        String[] values16 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8",
                "String9", "String10", "String11", "String12", "String13", "String14", "String15", "String16" };
        RingedLinkedList<String> list1 = new RingedLinkedList<>("value");
        RingedLinkedList<String> list8 = new RingedLinkedList<>(values8);
        RingedLinkedList<String> list16 = new RingedLinkedList<>(values16);
        list1.addAfter("newString");
        Assert.assertEquals(list1.size(), 2);
        Assert.assertArrayEquals(list1.getCurrentPointerValues(), new String[]{"value", "newString"});
        list8.shiftNext();
        list8.shiftNext();
        list8.addAfter("newString");
        Assert.assertArrayEquals(list8.toArray(), new String[]{"String", "String2", "String3", "newString", "String4", "String5", "String6", "String7", "String8"});
        Assert.assertArrayEquals(list8.getCurrentPointerValues(), new String[]{"String3", "newString"});
        Assert.assertEquals(list8.size(), 9);
        list16.shiftPrev();
        list16.shiftPrev();
        list16.addAfter("newString");
        Assert.assertArrayEquals(list16.toArray(), new String[]{"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8",
                "String9", "String10", "String11", "String12", "String13", "String14", "String15", "newString", "String16" });
        Assert.assertArrayEquals(list16.getCurrentPointerValues(), new String[]{"String15", "newString"});
        Assert.assertEquals(list16.size(), 17);
    }
    @Test
    public void TestAddBefore() {
        String[] values8 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8"};
        String[] values16 = {"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8",
                "String9", "String10", "String11", "String12", "String13", "String14", "String15", "String16" };
        RingedLinkedList<String> list1 = new RingedLinkedList<>("value");
        RingedLinkedList<String> list8 = new RingedLinkedList<>(values8);
        RingedLinkedList<String> list16 = new RingedLinkedList<>(values16);
        list1.addBefore("newString");
        Assert.assertEquals(list1.size(), 2);
        Assert.assertArrayEquals(list1.getCurrentPointerValues(), new String[]{"newString", "value"});
        list8.shiftNext();
        list8.shiftNext();
        list8.addBefore("newString");
        Assert.assertArrayEquals(list8.toArray(), new String[]{"String", "String2", "String3", "newString", "String4", "String5", "String6", "String7", "String8"});
        Assert.assertArrayEquals(list8.getCurrentPointerValues(), new String[]{"newString", "String4"});
        Assert.assertEquals(list8.size(), 9);
        list16.shiftPrev();
        list16.shiftPrev();
        list16.addBefore("newString");
        Assert.assertArrayEquals(list16.toArray(), new String[]{"String", "String2", "String3", "String4", "String5", "String6", "String7", "String8",
                "String9", "String10", "String11", "String12", "String13", "String14", "String15", "newString", "String16" });
        Assert.assertArrayEquals(list16.getCurrentPointerValues(), new String[]{"newString", "String16"});
        Assert.assertEquals(list16.size(), 17);
    }
}
