
public class FizzBuzzAnswer {

    public static void main(String[] args) {
        // write your code here
        for (int i = Integer.parseInt(args[0]); i < Integer.parseInt(args[1]); i++) {

            if (i % 3 == 0 && i % 5 == 0){

                System.out.println("fizzbuzz");

            }

            else if (i % 3 == 0) {

                System.out.println("fizz");

            } else if (i % 5 == 0) {

                System.out.println("buzz");

            }

            else {

                System.out.println(i);

            }

        }
    }
}