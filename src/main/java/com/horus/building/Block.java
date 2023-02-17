package com.horus.building;

import java.util.Optional;
import java.util.function.Predicate;

interface Block {
    String getColor();

    String getMaterial();

    default int getChildrenCount() {
        return 1;
    }

    default Optional<Block> getMatchingObject(Predicate<Block> predicate) {
        return predicate.test(this) ? Optional.of(this) : Optional.empty();
    }
}
