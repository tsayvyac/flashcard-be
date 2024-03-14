package com.tsayvyac.flashcard.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class Patcher {

    private Patcher() {}

    public static <T> void patchUpdate(T existing, T incomplete) throws IllegalAccessException {
        Field[] fields = existing.getClass().getDeclaredFields();
        for (var f : fields) {
            f.setAccessible(true);
            var value = f.get(incomplete);
            if (value != null) f.set(existing, value);
            f.setAccessible(false);
        }
    }

}
