package com.tsayvyac.flashcard.dto;

import java.util.List;

public record PageDto<T>(
        List<T> list,
        int pageNo,
        int pageSize,
        long totalElement,
        int totalPages,
        boolean isLast
) {
}
