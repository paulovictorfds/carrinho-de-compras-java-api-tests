package br.edu.ufrn.carrinhodecompras.repository;

import br.edu.ufrn.carrinhodecompras.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ItemRepository extends JpaRepository<Item, Long> {

}

