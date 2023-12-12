package br.edu.ufrn.carrinhodecompras.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckoutResponseDTO {
  double itemsValue;
  double shippingCost;
}
