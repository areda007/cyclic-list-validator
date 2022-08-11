package test.alraedah.valditor;

import java.util.HashMap;
import java.util.Map;

public class DataOnDemandUtility {

  public static Map<String, int[]> constructCyclicArrayMap(String listName) {
    Map<String, int[]> map = new HashMap<>();

    map.put(listName, getCyclicArray());
    return map;
  }

  public static Map<String, int[]> constructNotCyclicArrayMap(String listName) {
    Map<String, int[]> map = new HashMap<>();

    map.put(listName, getNotCyclicArray());
    return map;
  }
  
  public static Map<String, int[]> constructOutOfIndexArrayMap(String listName) {
    Map<String, int[]> map = new HashMap<>();

    map.put(listName, getNotCyclicArray());
    return map;
  }

  public static Map<String, int[]> constructMultipleArrayMap(String cyclicListName,
      String notCyclicListName) {
    Map<String, int[]> map = new HashMap<>();

    map.put(cyclicListName, getCyclicArray());
    map.put(notCyclicListName, getNotCyclicArray());

    return map;
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
