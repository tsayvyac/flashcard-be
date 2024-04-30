package com.tsayvyac.flashcard.util;

import lombok.Getter;

@Getter
public class Pair<V, W> {
    private final V first;
    private final W second;

    private Pair(V first, W second) {
        this.first = first;
        this.second = second;
    }

    public static <V, W> Pair<V, W> of(V first, W second) {
        return new Pair<>(first, second);
    }

    @Override
    public String toString() {
        return "(first:" + first + ", second:" + second + ")";
    }

}
