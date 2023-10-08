package com.company.batchprocessing.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class ListUtil {

    private ListUtil() {
    }

    public static <T> List<List<T>> partition(Collection<T> collection, int size) {
        return partition(new ArrayList<>(collection), size);
    }

    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        final List<List<T>> partitions = new ArrayList<>();

        for (int i = 0; i < list.size(); i += size) {
            int end = Math.min(i + size, list.size());
            partitions.add(list.subList(i, end));
        }

        return partitions;
    }
}
