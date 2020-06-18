package homework.lesson_1.fruitBoxes;

public class Orange extends Fruit{
    float  weight;

    Orange(){
        weight = 1.5f;
    }

    @Override
    public float getW() {
        return weight;
    }
}
