package com.intro;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String inputString, outputString = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string: ");
        inputString = scanner.nextLine();
        // Just some pre-processing
        inputString = inputString.strip();
        inputString = inputString.toLowerCase();
        // The main container to check for duplication
        ArrayList<Character> stringArrayList = new ArrayList();
        // Where the magic happens, simply insert the characters of the input string in the array list
        // after checking if they already exist in the list and if they do just print them out then insert.
        for (Character single : inputString.toCharArray()) {
            if (stringArrayList.contains(single) && !single.equals(' ')) {
                if (!outputString.contains(single.toString())) outputString = outputString + single + ", ";
            }
            stringArrayList.add(single);
        }
        if (outputString.length() > 2) {
            outputString = outputString.substring(0, outputString.length() - 2);
        }
        System.out.println(outputString);
    }
}
