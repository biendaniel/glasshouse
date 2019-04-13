package conditions;

public  class Condition {

  private float value;

    public Condition(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "value=" + value +
                '}';
    }
}