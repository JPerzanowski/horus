package com.horus.building;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Wall implements Structure {
    private List<Block> blocks = new ArrayList<>();

    public List<Block> getBlocks() {
        return blocks;
    }

    public Optional<Block> findBlockByColor(String color) {
        return findByPredicate(block -> color.equals(block.getColor()));
    }

    public List<Block> findBlocksByMaterial(String material) {
        return findAllByPredicate(n -> material.equals(n.getMaterial()));
    }

    private List<Block> findAllByPredicate(Predicate<Block> predicate) {
        return blocks.stream()
                .map(f -> f.getMatchingObject(predicate))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Block> findByPredicate(Predicate<Block> predicate) {
        return blocks.stream()
                .map(f -> f.getMatchingObject(predicate))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    public int count() {
        return blocks.stream().mapToInt(Block::getChildrenCount).sum();
    }
}
