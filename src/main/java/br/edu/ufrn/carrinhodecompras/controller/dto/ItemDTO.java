package br.edu.ufrn.carrinhodecompras.controller.dto;

import br.edu.ufrn.carrinhodecompras.model.Item;
import br.edu.ufrn.carrinhodecompras.model.ItemType;
import java.math.BigDecimal;

public record ItemDTO(
    Long id,
    String name,
    String description,
    BigDecimal price,
    int weight,
    ItemType type
) {

  public static ItemDTO fromItem(Item item) {
    return new ItemDTO(
        item.getId(),
        item.getName(),
        item.getDescription(),
        item.getPrice(),
        item.getWeight(),
        item.getType()
    );
  }

  public Item toItem() {
    Item item = new Item();
    item.setName(name);
    item.setDescription(description);
    item.setPrice(price);
    item.setWeight(weight);
    item.setType(type);

    return item;
  }
}
