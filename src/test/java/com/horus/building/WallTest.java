package com.horus.building;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WallTest {
    private Wall wall;

    private static Stream<Arguments> provideColorsForFindByColor() {
        return Stream.of(
                Arguments.of("red", true),
                Arguments.of("light red", true),
                Arguments.of("white", true),
                Arguments.of("brown", true),
                Arguments.of("black", true),
                Arguments.of("blue", false),
                Arguments.of("pink", false),
                Arguments.of("blue", false),
                Arguments.of("ReD", false),
                Arguments.of("lightred ", false),
                Arguments.of("WHITE ", false),
                Arguments.of("  ", false),
                Arguments.of("not blank", false),
                Arguments.of("blank", false)
        );
    }

    private static Stream<Arguments> provideMaterialForFindByMaterial() {
        return Stream.of(
                Arguments.of("concrete", true),
                Arguments.of("sand", true),
                Arguments.of("wood", true),
                Arguments.of("heban wood", true),
                Arguments.of("cONcrEtE", false),
                Arguments.of("hebanwood ", false),
                Arguments.of("WOOD ", false),
                Arguments.of("  ", false),
                Arguments.of("not blank", false),
                Arguments.of("blank", false)
        );
    }

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        wall = new Wall();
        wall.getBlocks().add(buildBlock("red", "concrete"));
        wall.getBlocks().add(buildBlock("light red", "concrete"));
        wall.getBlocks().add(buildBlock("white", "sand"));
        wall.getBlocks().add(buildBlock("brown", "wood"));
        wall.getBlocks().add(buildBlock("black", "heban wood"));
        wall.getBlocks().add(buildCompositeBlock("nd", "nd"));
    }

    private Block buildBlock(String color, String material) {
        return new Block() {
            @Override
            public String getColor() {
                return color;
            }

            @Override
            public String getMaterial() {
                return material;
            }
        };
    }

    private Block buildCompositeBlock(String color, String material) {
        return new CompositeBlock() {
            @Override
            public List<Block> getBlocks() {
                return new ArrayList<>(List.of(buildBlock("dark red", "concrete"),
                        buildBlock("light brown", "oak wood")));
            }

            @Override
            public String getColor() {
                return color;
            }

            @Override
            public String getMaterial() {
                return material;
            }
        };
    }

    @ParameterizedTest
    @MethodSource("provideColorsForFindByColor")
    void givenColor_whenFindByColor_thenProperEqualsResults(String color, boolean isPresent) {
        assertEquals(isPresent, wall.findBlockByColor(color).isPresent());
    }

    @ParameterizedTest
    @MethodSource("provideMaterialForFindByMaterial")
    void givenMaterial_whenFindByMaterial_thenProperEqualsResults(String material, boolean isPresent) {
        assertEquals(isPresent, !wall.findBlocksByMaterial(material).isEmpty());
    }

    @Test
    void givenCompositeBlock_whenGetChildrenCount_thenProperCount() {
        assertEquals(2, wall.findBlockByColor("nd").orElseThrow().getChildrenCount());
    }

    @Test
    void givenBlock_whenGetChildrenCount_thenProperCount() {
        assertEquals(1, wall.findBlockByColor("red").orElseThrow().getChildrenCount());
    }
}