package test.alraedah.valditor.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CyclicListValidtorService {

  public Map<String, Boolean> validateLists(Map<String, int[]> lists) {
    Map<String, Boolean> result = new HashMap<>();

    lists.forEach((name, array) -> result.put(name, isItCyclic(array)));

    return result;
  }

  public boolean isItCyclic(int[] list) {
    if (list.length == 0) {
      return true;
    }

    Set<Integer> visited = new HashSet<>();

    int currentIndex = 0;
    while (visited.size() < list.length) {
      if (currentIndex >= list.length) {
        return false;
      }
      currentIndex = list[currentIndex];

      if (visited.contains(currentIndex)) {
        // it means there's a loop here so it won't be valid cyclic list
        return false;
      } else {
        visited.add(currentIndex);
      }

      if (visited.size() == list.length - 1 && list[currentIndex] != 0) {
        return false;
      }
    }

    return visited.size() == list.length;
  }
}
