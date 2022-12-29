package com.zuosuo.treehole.factory;

import java.util.ArrayList;
import java.util.List;

public class NoteCacheListCount<T> {
    public long count;
    public List<T> list;

    public NoteCacheListCount() {
        count = 0;
        list = new ArrayList<>();
    }
}
