import homework.lesson_6.Task2;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class Task2Test {

    private static Task2 task2;

    @BeforeClass
    public static void initTest() {
        task2 = new Task2();
        System.out.println("init suite");
    }

        @Test
    public void testGetNumbersAfterLastFour01() {
        Assert.assertArrayEquals(new int[]{5, 6}, task2.getNumbersAfterLastFour(new int[]{1, 2, 3, 4, 5, 6}));
    }
    @Test
    public void testGetNumbersAfterLastFour02() {
        Assert.assertArrayEquals(new int[]{17}, task2.getNumbersAfterLastFour(new int[]{1, 2, 3, 4, 5, 6, 4, 17}));
    }
    @Test
    public void testGetNumbersAfterLastFour03() {
        Assert.assertThrows( RuntimeException.class, ()->{task2.getNumbersAfterLastFour(new int[]{});});
    }
    @Test
    public void testGetNumbersAfterLastFour04() {
        Assert.assertThrows( RuntimeException.class, ()->{task2.getNumbersAfterLastFour(new int[]{1, 2, 3, 5, 6});});
    }
}
