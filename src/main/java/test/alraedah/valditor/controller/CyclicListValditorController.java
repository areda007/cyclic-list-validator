package test.alraedah.valditor.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.alraedah.valditor.service.CyclicListValidtorService;

@RestController
public class CyclicListValditorController {

  private CyclicListValidtorService cyclicListValidtorService;

  @Autowired
  public CyclicListValditorController(CyclicListValidtorService cyclicListValidtorService) {
    this.cyclicListValidtorService = cyclicListValidtorService;
  }

  @PostMapping(value = "/validate")
  public Map<String, Boolean> validateLists(@RequestBody Map<String, int[]> lists) {
    return cyclicListValidtorService.validateLists(lists);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<String> handleException(Exception e) {
    return ResponseEntity.internalServerError().build();
  }
}
