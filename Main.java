
public class Main{
    static int Array[][];
    public static void main(String[] args) {
        // Main class for calling
        Array = Inputs.indexesValues();
        Inputs.costDisplay(Array);
        Inputs.confirm();
        DraggableLabel.call();
    }
}