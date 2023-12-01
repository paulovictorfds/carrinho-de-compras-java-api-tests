package br.edu.ufrn.carrinhodecompras.service;

import br.edu.ufrn.carrinhodecompras.model.Item;
import br.edu.ufrn.carrinhodecompras.repository.ItemRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

  private final ItemRepository itemRepository;

  @Autowired
  public ItemService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public Item insertItem(Item item) {
    return itemRepository.save(item);
  }

  public List<Item> findAll() {
    return itemRepository.findAll();
  }

}
