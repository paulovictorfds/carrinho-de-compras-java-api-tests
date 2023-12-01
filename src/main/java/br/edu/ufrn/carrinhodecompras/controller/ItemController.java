package br.edu.ufrn.carrinhodecompras.controller;

import br.edu.ufrn.carrinhodecompras.controller.dto.ItemDTO;
import br.edu.ufrn.carrinhodecompras.model.Item;
import br.edu.ufrn.carrinhodecompras.service.ItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/produtos")
public class ItemController {
  private final ItemService itemService;

  @Autowired
  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public ItemDTO createItem(@RequestBody ItemDTO itemDTO) {
    Item newItem = itemService.insertItem(itemDTO.toItem());

    return ItemDTO.fromItem(newItem);
  }

  @GetMapping
  public List<ItemDTO> findAll() {
    List<Item> items = itemService.findAll();

    return items.stream()
        .map(ItemDTO::fromItem)
        .toList();
  }
}
