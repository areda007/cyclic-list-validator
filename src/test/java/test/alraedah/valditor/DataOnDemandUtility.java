package test.alraedah.valditor;

import java.util.Map;

public class DataOnDemandUtility {

  public static Map<String, int[]> constructCyclicArrayMap(String listName) {

    return Map.of(listName, getCyclicArray());
  }

  public static Map<String, int[]> constructNotCyclicArrayMap(String listName) {

    return Map.of(listName, getNotCyclicArray());

  }

  public static Map<String, int[]> constructOutOfIndexArrayMap(String listName) {

    return Map.of(listName, getNotCyclicArray());

  }

  public static Map<String, int[]> constructMultipleArrayMap(String cyclicListName,
      String notCyclicListName) {

    return Map.of(cyclicListName, getCyclicArray(), notCyclicListName, getNotCyclicArray());
  }

  public static int[] getCyclicArray() {
    return new int[] {3, 0, 1, 2};
  }

  public static int[] getNotCyclicArray() {
    return new int[] {0, 1, 2};
  }

  public static int[] getNotCyclicOutOfIndexArray() {
    return new int[] {1, 5, 2};
  }
}
