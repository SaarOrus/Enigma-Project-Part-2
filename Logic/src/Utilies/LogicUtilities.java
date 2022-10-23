package Utilies;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LogicUtilities {

    public static int getRandomNumber(int min, int max) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        return tlr.nextInt(min, max + 1);
    }

    public static String convertNumberToReflectorId(int reflectorNumber) {
        String reflectorGreekNumber = "";

        switch (reflectorNumber) {
            case 1:
                reflectorGreekNumber = "I";
                break;
            case 2:
                reflectorGreekNumber = "II";
                break;
            case 3:
                reflectorGreekNumber = "III";
                break;
            case 4:
                reflectorGreekNumber = "IV";
                break;
            case 5:
                reflectorGreekNumber = "V";
                break;
        }
        return reflectorGreekNumber;
    }

    public static int convertInputOfReflectorNumber(String reflectorNumber) {
        int numberOfReflector;

        switch (reflectorNumber) {
            case "I":
                numberOfReflector = 1;
                break;
            case "II":
                numberOfReflector = 2;
                break;
            case "III":
                numberOfReflector = 3;
                break;
            case "IV":
                numberOfReflector = 4;
                break;
            case "V":
                numberOfReflector = 5;
                break;
            default:
                numberOfReflector = -1;

        }
        return numberOfReflector;
    }

    public static int[] reverseArrayInt(int[] arrayToReverse, int startIndex) {
        int[] reverseArray = new int[arrayToReverse.length];
        int j = arrayToReverse.length;

        for (int i = startIndex; i < arrayToReverse.length; i++) {
            reverseArray[j - 1] = arrayToReverse[i];
            j--;
        }
        return reverseArray;
    }

    public static char[] reverseArrayChar(char[] arrayToReverse, int startIndex) {
        char[] reverseArray = new char[arrayToReverse.length];
        int j = arrayToReverse.length;

        for (int i = startIndex; i < arrayToReverse.length; i++) {
            reverseArray[j - 1] = arrayToReverse[i];
            j--;
        }
        return reverseArray;
    }

    public static int factorial(int n){
        if (n == 0)
            return 1;
        else
            return(n * factorial(n-1));
    }

    public static long binomial(int n, int k) {
        if (k>n-k)
            k=n-k;

        long b=1;
        for (int i=1, m=n; i<=k; i++, m--)
            b=b*m/i;
        return b;
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Permutation(0, nums, result);
        return result;
    }

    private static void Permutation(int i, int[] nums, List<List<Integer>> result) {
        if (i == nums.length - 1) {
            List<Integer> list = new ArrayList<>();
            for (int n : nums) list.add(n);
            result.add(list);
        } else {
            for (int j = i, l = nums.length; j < l; j++) {
                int temp = nums[j];
                nums[j] = nums[i];
                nums[i] = temp;
                Permutation(i + 1, nums, result);
                temp = nums[j];
                nums[j] = nums[i];
                nums[i] = temp;
            }
        }
    }
}
