package org.com.challenge.algorithms;

import java.util.*;
import java.util.stream.Collectors;

public class KComplementary {

    public List<Pair> kComplementary(int[] nums, int k) {
        Arrays.sort(nums);
        int headPointer = 0;
        int tailPointer = nums.length - 1;

        List<Pair> pairs = new LinkedList<Pair>();
        while (headPointer < tailPointer) {
            int sum = nums[headPointer] + nums[tailPointer];
            if (sum == k) {
                pairs.add(new Pair(nums[headPointer], nums[tailPointer]));
                headPointer++;
                tailPointer--;
            } else if (sum > k) {
                tailPointer--;
            } else {
                headPointer++;
            }
        }
        return pairs.stream().distinct().collect(Collectors.toList());
    }
}
