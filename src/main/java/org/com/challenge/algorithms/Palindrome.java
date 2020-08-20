package org.com.challenge.algorithms;

public class Palindrome {

    public boolean isPalindrome(String s) {
        int headPointer = 0;
        int tailPointer = s.length() - 1;
        String word = s.toLowerCase();
        boolean isPalindrome = true;
        while (headPointer < tailPointer && isPalindrome) {
            while (headPointer < tailPointer && !Character.isLetterOrDigit(word.charAt(headPointer))) {
                headPointer++;
            }
            while (tailPointer > headPointer && !Character.isLetterOrDigit(word.charAt(tailPointer))) {
                tailPointer--;
            }
            if (headPointer < tailPointer && word.charAt(headPointer) != word.charAt(tailPointer)) {
                isPalindrome = false;
            } else {
                headPointer++;
                tailPointer--;
            }
        }
        return isPalindrome;
    }
}
