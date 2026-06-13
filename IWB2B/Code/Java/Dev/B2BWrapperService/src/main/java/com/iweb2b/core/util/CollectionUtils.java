package com.iweb2b.core.util;

import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.stream.Stream;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CollectionUtils {

    public static <T> Stream<T> stream(Collection<T> items) {
        return items == null ? Stream.empty() : items.stream();
    }
}
