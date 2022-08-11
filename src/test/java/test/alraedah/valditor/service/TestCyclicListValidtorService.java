package test.alraedah.valditor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.alraedah.valditor.DataOnDemandUtility.constructCyclicArrayMap;
import static test.alraedah.valditor.DataOnDemandUtility.constructMultipleArrayMap;
import static test.alraedah.valditor.DataOnDemandUtility.constructNotCyclicArrayMap;
import static test.alraedah.valditor.DataOnDemandUtility.constructOutOfIndexArrayMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestCyclicListValidtorService {

  @Autowired
  private CyclicListValidtorService cyclicListValidtorService;

  @Test
  void testValidate_validCyclicArray() {
    String listName = "test1";

    Map<String, Boolean> result =
        cyclicListValidtorService.validateLists(constructCyclicArrayMap(listName));

    assertEquals(true, result.get(listName));
  }

  @Test
  void testValidate_notCyclicArray() {
    String listName = "test1";

    Map<String, Boolean> result =
        cyclicListValidtorService.validateLists(constructNotCyclicArrayMap(listName));

    assertEquals(false, result.get(listName));
  }

  @Test
  void testValidate_OutOfIndexArray() {
    String listName = "test1";

    Map<String, Boolean> result =
        cyclicListValidtorService.validateLists(constructOutOfIndexArrayMap(listName));

    assertEquals(false, result.get(listName));
  }


  @Test
  void testValidate_MixedList() {
    String cyclicListName = "test1";
    String notCyclicListName = "test2";

    Map<String, Boolean> result = cyclicListValidtorService
        .validateLists(constructMultipleArrayMap(cyclicListName, notCyclicListName));

    assertEquals(false, result.get(notCyclicListName));
    assertEquals(true, result.get(cyclicListName));
  }


}
