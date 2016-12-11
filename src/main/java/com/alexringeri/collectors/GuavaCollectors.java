package com.alexringeri.collectors;

import java.util.function.Function;
import java.util.stream.Collector;

import com.google.common.collect.*;

public final class GuavaCollectors {

  private GuavaCollectors() {
    // private constructor for utility class
  }

  public static <T> Collector<T, ImmutableList.Builder<T>, ImmutableList<T>> toImmutableList() {
    return Collector.of(
        ImmutableList::builder,
        ImmutableList.Builder::add,
        (left, right) -> left.addAll(right.build()),
        ImmutableList.Builder::build
    );
  }

  public static <T> Collector<T, ImmutableSet.Builder<T>, ImmutableSet<T>> toImmutableSet() {
    return Collector.of(
        ImmutableSet::builder,
        ImmutableSet.Builder::add,
        (left, right) -> left.addAll(right.build()),
        ImmutableSet.Builder::build
    );
  }

  public static <T, K, V> Collector<T, ImmutableMap.Builder<K, V>, ImmutableMap<K, V>> toImmutableMap(
      Function<? super T, ? extends K> keyMapper,
      Function<? super T, ? extends V> valueMapper) {
    return Collector.of(
        ImmutableMap::builder,
        (builder, element) -> builder.put(
            keyMapper.apply(element),
            valueMapper.apply(element)),
        (left, right) -> left.putAll(right.build()),
        ImmutableMap.Builder::build
    );
  }

  public static <T, K, V> Collector<T, BiMap<K,V>, BiMap<K, V>> toBiMap(
      Function<? super T, ? extends K> keyMapper,
      Function<? super T, ? extends V> valueMapper) {

    return Collector.of(
        HashBiMap::create,
        (kvBiMap, element) -> kvBiMap.put(
            keyMapper.apply(element),
            valueMapper.apply(element)),
        GuavaCollectors::combineAsHashBiMap
    );
  }

  private static <K,V> BiMap<K, V> combineAsHashBiMap(BiMap<K, V> left, BiMap<K, V> right) {
    HashBiMap<K, V> combined = HashBiMap.create(left);
    combined.putAll(right);
    return combined;
  }

  public static <T, K, V> Collector<T, ImmutableBiMap.Builder<K, V>, ImmutableBiMap<K, V>> toImmutableBiMap(
      Function<? super T, ? extends K> keyMapper,
      Function<? super T, ? extends V> valueMapper) {

    return Collector.of(
        ImmutableBiMap::builder,
        (builder, element) -> builder.put(
            keyMapper.apply(element),
            valueMapper.apply(element)),
        (left, right) -> left.putAll(right.build()),
        ImmutableBiMap.Builder::build
    );
  }
}
