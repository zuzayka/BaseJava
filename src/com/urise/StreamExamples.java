package com.urise;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExamples {
    public int minValue(int[] values) {
        return Arrays.stream(values).sorted().distinct()
                .reduce(0, (base, element) -> base * 10 + element);
    }

    public List<Integer> oddOrEven(List<Integer> integers) {
        boolean even = integers.stream().reduce(0, Integer::sum) % 2 == 0;
        return integers.stream().filter(e -> {
            if (even) {
                return e % 2 == 0;
            } else {
                return e % 2 == 1;
            }
        }).collect(Collectors.toList());
    }
}
