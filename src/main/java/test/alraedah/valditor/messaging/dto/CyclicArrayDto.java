package test.alraedah.valditor.messaging.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class CyclicArrayDto implements Serializable {
  private static final long serialVersionUID = -7837586842475576773L;
  private String name;
  private int[] array;
}
