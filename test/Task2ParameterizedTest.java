import homework.lesson_6.Task2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(value = Parameterized.class)
public class Task2ParameterizedTest {
    private Task2 task2 = new Task2();

    private int[] resArr;
    private int[] sourceArr;
    private Class<? extends Exception> expectedException;

    public Task2ParameterizedTest(int[] resArr, int[] sourceArr, Class<? extends Exception> expectedException){
        this.resArr = resArr;
        this.sourceArr = sourceArr;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{5, 6}, new int[]{1, 2, 3, 4, 5, 6}, null},
                {new int[]{}, new int[]{1, 2, 3, 4, 5, 6, 4}, null},
                {new int[]{5,6,7,8}, new int[]{4, 5, 6, 7, 8}, null},

                {null, new int[]{}, RuntimeException.class},
                {null, new int[]{1,2,3}, RuntimeException.class}
        });
    }

    @Test
    public void testGetNumbersAfterLastFour(){
        Assert.assertArrayEquals(resArr, task2.getNumbersAfterLastFour(sourceArr));
    }

    @Test
    public void testGetNumbersAfterLastFour01() {
        Assert.assertThrows( expectedException, ()->{task2.getNumbersAfterLastFour(sourceArr);});
    }

}
