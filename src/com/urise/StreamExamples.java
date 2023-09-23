package com.urise;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExamples {
    public int minValue(int[] values) {
        return Integer.parseInt(Arrays.stream(values).sorted().distinct()
                .mapToObj(String::valueOf).collect(Collectors.joining()));
    }


    public List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().filter(e -> {
            if (integers.stream().reduce(0, Integer::sum) % 2 == 0) {
                return e % 2 == 0;
            } else {
                return e % 2 == 1;
            }
        }).collect(Collectors.toList());
    }
}
