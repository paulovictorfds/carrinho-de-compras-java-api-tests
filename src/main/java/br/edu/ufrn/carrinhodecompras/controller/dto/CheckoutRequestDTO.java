package br.edu.ufrn.carrinhodecompras.controller.dto;

import java.util.List;
import lombok.Data;

@Data
public class  CheckoutRequestDTO {
  List<String> itemIds;
}
