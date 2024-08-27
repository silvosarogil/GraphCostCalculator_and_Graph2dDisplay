import java.util.Scanner;
class Inputs{
    //For graphs inputs such as (Arrays, Indexes, and it's values) and CLI printings
    static int numberofIndexes =  0;
    static int[][] indexesValues(){
        Scanner scan = new  Scanner(System.in);
        boolean valid = false;
        while (!valid) {
            try {
                print("Enter the number of indexes: ");
                numberofIndexes = scan.nextInt();
                if(numberofIndexes > 0){
                    valid = true;
                    break;
                }
                else{
                    println("number of indexes should be greater than 0!");
                }
            } catch (Exception e) {
                println("Not valid number of index try again!");
                scan.next();
            }
        }

        println(" ");
        int [][] Indexes = new int[numberofIndexes][numberofIndexes];
        for(int i = 0;i < numberofIndexes;i++){
            for(int j = 0;j < numberofIndexes;j++){
                boolean condition = false;
                while (!condition) {
                    try {
                        print("Enter value from index " + (i+1) + " to index " + (j+1) + ": ");
                        Indexes[i][j] += scan.nextInt();
                        condition = true; 
                        println(" ");  
                    } catch (Exception e) {
                        println("Wrong input only intergers are allowed try again!");
                        scan.next();
                    }
                }
            }
        }
        return Indexes; 
    }
    static void costDisplay(int[][] arr){
        println("Cost for each indexes");
        for(int i = 0;i < arr.length;i++){
            for(int j = 0;j < arr.length;j++){
                System.out.print("Index " + (i+1) + " " + "to Index " + (j+1) + " Costs: " + arr[i][j]);
                println(" ");
            }
     
        }
    }
    
    static void confirm(){
        Scanner scan = new Scanner(System.in);
        boolean condition = false;
        String confirmation = "No";
        while (!condition) {
            try {
                print("Would you like to make another set of another inputs? (Yes,No): ");
                confirmation = scan.nextLine();
                if(confirmation.equalsIgnoreCase("Yes") || confirmation.equalsIgnoreCase("No")){
                    condition = true;
                    if(confirmation.equalsIgnoreCase("Yes")){
                        int Array[][] = Inputs.indexesValues();
                        Inputs.costDisplay(Array);
                        confirm();
                    }
                    else{
                        println("Have a good day!!");
                    }

                }
            } catch (Exception e) {
                println("Wrong command try again!");
                scan.next();
                
            }            
        }
    }
    
    static void println(String A){
        System.out.println(A);
    }
    static void print(String A){
        System.out.print(A);
    }
}