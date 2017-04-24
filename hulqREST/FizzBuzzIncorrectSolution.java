
public class FizzBuzzIncorrectSolution {

    public static void main(String[] args) {
        // write your code here
        System.out.println(args[0]+ "<-");
        System.out.println(args[1]+ "<-");
        for (int i = Integer.parseInt(args[0]); i < Integer.parseInt(args[1]); i++) {

            if (i % 3 == 0 && i % 5 == 0){

                System.out.println("fizzbuzz");

            }

            else if (i % 3 == 0) {

                System.out.println("buzz");

            } else if (i % 5 == 0) {

                System.out.println("buzz");

            }

            else {

                System.out.println(i);

            }

        }
    }
}
