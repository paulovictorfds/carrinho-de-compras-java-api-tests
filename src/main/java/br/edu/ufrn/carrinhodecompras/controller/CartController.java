package br.edu.ufrn.carrinhodecompras.controller;

import br.edu.ufrn.carrinhodecompras.controller.dto.CheckoutRequestDTO;
import br.edu.ufrn.carrinhodecompras.controller.dto.CheckoutResponseDTO;
import br.edu.ufrn.carrinhodecompras.model.Item;
import br.edu.ufrn.carrinhodecompras.service.CartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/carrinho")
public class CartController {
  private final CartService cartService;

  @Autowired
  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @PostMapping("/checkout")
  public CheckoutResponseDTO checkout(@RequestBody CheckoutRequestDTO itemIds) {

    return cartService.checkout(itemIds.getItemIds());
  }
}
