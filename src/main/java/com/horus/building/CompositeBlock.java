package com.horus.building;

import java.util.List;

interface CompositeBlock extends Block {
    List<Block> getBlocks();

    default int getChildrenCount() {
        return getBlocks().stream().mapToInt(Block::getChildrenCount).sum();
    }
}
