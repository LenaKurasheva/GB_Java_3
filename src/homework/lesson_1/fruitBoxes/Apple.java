package homework.lesson_1.fruitBoxes;

public class Apple extends Fruit {
    float  weight;

    Apple(){
        weight = 1.0f;
    }

    @Override
    public float getW() {
        return weight;
    }
}
