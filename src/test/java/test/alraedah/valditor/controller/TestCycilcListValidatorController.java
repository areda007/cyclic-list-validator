package test.alraedah.valditor.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static test.alraedah.valditor.DataOnDemandUtility.constructCyclicArrayMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import test.alraedah.valditor.service.CyclicListValidtorService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
@TestPropertySource(properties = "rabbitmq.enable=false")
class TestCycilcListValidatorController {

  @Test
  void testValidateArray() throws Exception {
    String listName = "list1";

    String bodyJson = new ObjectMapper().writeValueAsString(constructCyclicArrayMap(listName));

    when(cyclicListValidtorService.validateLists(anyMap())).thenReturn(Map.of(listName, true));

    mockMvc
        .perform(post("/validate").contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE).content(bodyJson))
        .andExpect(status().isOk()).andExpect(jsonPath("$." + listName, equalTo(true)));
  }

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CyclicListValidtorService cyclicListValidtorService;

  @MockBean
  private AmqpTemplate amqpTemplate;
}
