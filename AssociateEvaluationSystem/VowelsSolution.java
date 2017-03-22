public class VowelsSolution {  
  public static void main(String[] args)  
    {  
        System.out.print(count_Vowels(args[0]));  
    }  
 public static int count_Vowels(String str)  
    {  
        int count = 0;  
        for (int i = 0; i < str.length(); i++)  
        {  
            if (str.charAt(i) == 'a' || str.charAt(i) == 'e' || str.charAt(i) == 'i'  
                    || str.charAt(i) == 'o' || str.charAt(i) == 'u')  
            {  
                count++;  
            }  
        }  
        return count;  
    }  
}  

/*
  @Config
  mathmode:true
  trimwhitespace:false
  extractwhitespace:false
  caseignore:true
  grosserrormargin:0.0
  caseerrormargin:0.0
  keyfilename:VowelsSolution.java
  testfilename:VowelsAnswer

  @ArgSet(1)
  vowel % 2


  @ArgSet(2)
  abcdefghijklmnopqrstuvwxyz % 5
  

  @ArgSet(3)
  qwertyuiopoiuytrewq % 8


  @ArgSet(4)
  hi % 1

*/