public class Palindrome
{
    public static boolean isPalindrome(String s)
    {
        if(s.length()==0)
            return false;
        else if(s.length()==1)
            return true;
        else if(s.length()==2)
        {
            if(s.charAt(0)==s.charAt(1))
                return true;
            else
                return false;
        }
        else if(s.charAt(0)==s.charAt(s.length()-1))
            return isPalindrome(s.substring(1,s.length()-1));
        else
            return false;
    }

    public static void main(String[] args)
    {
        System.out.println(isPalindrome(args[0]));
    }
}

/*
  @Config
  mathmode:false
  trimwhitespace:false
  extractwhitespace:false
  caseignore:false
  grosserrormargin:0.0
  caseerrormargin:0.0
  keyfilename:key.cpp
  testfilename:test

  @ArgSet(1)
  racecar % true

  @ArgSet(2)
  thisisnotapalindrome % false
  bob % this second argument is ignored

  @ArgSet(3)
  anna % true

  @ArgSet(4)
  raceCar % false (capitalization)
*/
