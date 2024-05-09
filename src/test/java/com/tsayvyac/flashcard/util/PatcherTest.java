package com.tsayvyac.flashcard.util;

import com.tsayvyac.flashcard.model.Flashcard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PatcherTest {

    @Test
    void updateObject_AttributesUpdatedCorrectly() throws IllegalAccessException {
        String updatedFront = "New front info";
        String updatedBack = "New back info";

        Flashcard oldInfo = Flashcard.builder()
                .front("Old front info")
                .back("Old back info")
                .build();
        Flashcard newInfo = Flashcard.builder()
                .front(updatedFront)
                .back(updatedBack)
                .build();

        Patcher.patchUpdate(oldInfo, newInfo);

        Assertions.assertEquals(updatedFront, oldInfo.getFront());
        Assertions.assertEquals(updatedBack, oldInfo.getBack());
    }
}
