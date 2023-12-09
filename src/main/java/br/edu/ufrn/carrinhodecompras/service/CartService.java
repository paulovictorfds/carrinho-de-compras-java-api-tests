package br.edu.ufrn.carrinhodecompras.service;

import br.edu.ufrn.carrinhodecompras.controller.dto.CheckoutResponseDTO;
import br.edu.ufrn.carrinhodecompras.model.Item;
import br.edu.ufrn.carrinhodecompras.repository.ItemRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

  private final ItemRepository itemRepository;

  @Autowired
  public CartService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public CheckoutResponseDTO checkout(List<String> itemIds) {
    List<Long> itemIdsLong = itemIds.stream()
        .map(Long::valueOf)
        .toList();

    List<Item> items = itemRepository.findAllById(itemIdsLong);

    BigDecimal itemsValue = calculateItemsValue(items);
    BigDecimal shippingCost = calculateShippingCost(items);

    return new CheckoutResponseDTO(itemsValue.doubleValue(), shippingCost.doubleValue());
  }

  private BigDecimal calculateItemsValue(List<Item> items) {
    BigDecimal itemsValue = items
        .stream()
        .map(Item::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (itemsValue.compareTo(new BigDecimal("1000.00")) > 0) {
      return itemsValue.multiply(new BigDecimal("0.80"));
    }

    if (itemsValue.compareTo(new BigDecimal("500.00")) > 0) {
      return itemsValue.multiply(new BigDecimal("0.90"));
    }

    return itemsValue;
  }

  private BigDecimal calculateShippingCost(List<Item> items) {
    int totalWeight = items
        .stream()
        .mapToInt(Item::getWeight)
        .sum();

    BigDecimal shippingCost = BigDecimal.ZERO;

    if (items.size() > 5) {

      shippingCost = shippingCost.add(BigDecimal.TEN);

    } else {

      if (totalWeight > 50000) {
        shippingCost = shippingCost
            .add(new BigDecimal("7.00").multiply(
                BigDecimal.valueOf(Math.max(0, totalWeight - 50000))));
      }

      if (totalWeight > 10000) {
        shippingCost = shippingCost
            .add(new BigDecimal("4.00").multiply(
                BigDecimal.valueOf(Math.max(0, totalWeight - 10000))));
      }

      if (totalWeight > 2000) {
        shippingCost = shippingCost
            .add(new BigDecimal("2.00")
                .multiply(BigDecimal
                    .valueOf(Math.max(0, totalWeight - 2000))));
      }
    }

    return shippingCost;
  }
}
