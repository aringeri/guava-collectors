package com.alexringeri.collectors;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class GuavaCollectorsTest {

  @Test
  public void testToImmutableList() {
    ImmutableList<String> result = Stream.of("a", "b", "c")
        .collect(GuavaCollectors.toImmutableList());

    assertEquals(
        ImmutableList.of("a", "b", "c"), result);
  }

  @Test
  public void testToImmutableSet() {
    ImmutableSet<String> set = Stream.of("a", "b", "c")
        .collect(GuavaCollectors.toImmutableSet());

    assertEquals(ImmutableSet.of("a", "b", "c"), set);
  }

  @Test
  public void testToImmutableMap() {
    ImmutableMap<String, String> expected = ImmutableMap.of(
        "key1", "val1",
        "key2", "val2",
        "key3", "val3"
    );

    ImmutableMap<String,String> actual =
        Stream.of("1", "2", "3")
            .collect(GuavaCollectors.toImmutableMap(
                e -> "key" + e,
                e -> "val" + e
            )
    );

    assertEquals(expected, actual);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExceptionForDuplicateKeys() {
    Stream.of("1", "2", "3")
        .collect(GuavaCollectors.toImmutableMap(
            e -> "key",
            e -> "val"
        )
    );
  }

  @Test
  public void testToBiMap() {
    BiMap<Character, Integer> expected = HashBiMap.create(
        ImmutableMap.of(
            'A', 65,
            'B', 66,
            'C', 67)
    );

    BiMap<Character, Integer> actual = Stream.of('A', 'B', 'C')
        .collect(GuavaCollectors.toBiMap(
            Function.identity(),
            c -> (int)c));

    assertEquals(expected, actual);
  }

  @Test
  public void testToImmutableBiMap() {
    ImmutableBiMap<Character, Integer> expected = ImmutableBiMap.of(
        'A', 65,
        'B', 66,
        'C', 67
    );

    ImmutableBiMap<Character, Integer> actual = Stream.of('A', 'B', 'C')
        .collect(GuavaCollectors.toImmutableBiMap(
            Function.identity(),
            c -> (int)c));

    assertEquals(expected, actual);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExceptionForDuplicateBiMapKeys() {
    Stream.of('A', 'A', 'C')
        .collect(GuavaCollectors.toImmutableBiMap(
            Function.identity(),
            c -> (int)c));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExceptionForDuplicateBiMapValues() {
    final int duplicateVal = 1;

    Stream.of('A', 'B', 'C')
        .collect(GuavaCollectors.toImmutableBiMap(
            Function.identity(),
            c -> duplicateVal));
  }

}