import homework.lesson_6.Task3;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(value = Parameterized.class)
public class Task3Test {
    private Task3 task3 = new Task3();

    private int[] sourceArr;
    private boolean result;

    public Task3Test(boolean result, int[] sourceArr){
        this.result = result;
        this.sourceArr = sourceArr;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {false, new int[]{1,1,1,1,1,1,1}},
                {false, new int[]{4,4,4,4}},
                {false, new int[]{1,4,1,4,2}},
                {false, new int[]{}},

                {true, new int[]{1,4,1,4}},
                {true, new int[]{1,1,1,4,1,4}},
                {true, new int[]{1,4,4,4,4,4,4,4,4,4,4,1,4}}
        });
    }

    @Test
    public void testGetNumbersAfterLastFour(){
        Assert.assertEquals(result, task3.containsOnlyOneAndFour(sourceArr));
    }

}

