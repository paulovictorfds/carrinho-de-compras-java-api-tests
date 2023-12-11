package br.edu.ufrn.carrinhodecompras.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public Item(String name, String description, double price, int weight, ItemType type) {
    this.name = name;
    this.description = description;
    this.price = BigDecimal.valueOf(price);
    this.weight = weight;
    this.type = type;
  }

  private String name;

  private String description;

  private BigDecimal price;

  private int weight;

  private ItemType type;





}
