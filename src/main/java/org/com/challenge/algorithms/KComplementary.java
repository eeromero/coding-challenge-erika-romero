package org.com.challenge.algorithms;

import java.util.*;
import java.util.stream.Collectors;

public class KComplementary {

    // O(nlogn)
    public static List<Pair> kComplementary(int[] nums, int k) {
        // n = nums.length
        // O(nlogn)
        Arrays.sort(nums);
        int headPointer = 0;
        int tailPointer = nums.length - 1;

        List<Pair> pairs = new LinkedList<>();
        //O(n)
        while (headPointer < tailPointer) {
            int sum = nums[headPointer] + nums[tailPointer];
            if (sum == k) {
                pairs.add(new Pair(nums[headPointer++], nums[tailPointer--]));
            } else if (sum > k) {
                tailPointer--;
            } else {
                headPointer++;
            }
        }
        //pairs.size() < n
        //O(pairs.size())
        return pairs.stream().distinct().collect(Collectors.toList());
    }

    public static void main(String[] args) {
        int[] arr = Arrays.stream(args[0].substring(1, args[0].length() - 1).split(","))
                .map(String::trim).mapToInt(Integer::parseInt).toArray();
        int k = Integer.valueOf(args[1]);
        System.out.println("The k-complementary of " + args[0] + " " + args[1] + " is " + KComplementary.kComplementary(arr, k));
    }
}
