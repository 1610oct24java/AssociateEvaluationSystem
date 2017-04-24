
public class FizzBuzzSolution {

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

/*
  @Config
  mathmode:false
  trimwhitespace:false
  extractwhitespace:false
  caseignore:true
  grosserrormargin:0.0
  caseerrormargin:0.0
  keyfilename:FizzBuzzSolution.java
  testfilename:FizzBuzzAnswer

  @ArgSet(1)
  1 % true
  10 % true

  @ArgSet(2)
  -100 % true
  100 % true

  @ArgSet(3)
  56 % true
  106 % true


  @ArgSet(4)
  900 % true
  926 % true
  useless % ignored

*/
