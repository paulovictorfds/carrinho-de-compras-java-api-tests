package br.edu.ufrn.carrinhodecompras.service;

import br.edu.ufrn.carrinhodecompras.exception.ItemNotFoundException;
import br.edu.ufrn.carrinhodecompras.model.Item;
import br.edu.ufrn.carrinhodecompras.repository.ItemRepository;
import java.util.List;
import java.util.Optional;
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

  public Item findById(long id) throws ItemNotFoundException {
    Optional<Item> item = itemRepository.findById(id);

    return item.orElseThrow(ItemNotFoundException::new);
  }

}
