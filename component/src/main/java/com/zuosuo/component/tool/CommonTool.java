package com.zuosuo.component.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommonTool {

    public static <T, R> List<R> parseList(T[] list, Function<T, R> func) {
        if (list == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(list).stream().map(func).collect(Collectors.toList());
    }
}
