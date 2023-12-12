package br.edu.ufrn.carrinhodecompras.service;


import br.edu.ufrn.carrinhodecompras.controller.dto.CheckoutResponseDTO;
import br.edu.ufrn.carrinhodecompras.exception.ItemNotFoundException;
import br.edu.ufrn.carrinhodecompras.model.Cart;
import br.edu.ufrn.carrinhodecompras.model.Item;
import br.edu.ufrn.carrinhodecompras.model.ItemType;
import br.edu.ufrn.carrinhodecompras.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    CartService cartService;

    @InjectMocks
    ItemService itemService;


    @Mock
    ItemRepository repository;


    BigDecimal valorItem;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.cartService = new CartService(repository);
        this.itemService = new ItemService(repository);

    }


    @Test
    void calcularValorAbaixoDe500(){

        double valorFinal = 0.0;

        List<Item> items = new ArrayList<>();
        items.add(new Item("detergente", "Detergente pra lavar loucas", 20, 200, ItemType.COZINHA));
        items.add(new Item("esponja", "Esponja pra lavar loucas", 20, 200, ItemType.COZINHA));
        items.add(new Item("Jaqueta", "Jqueta preta", 50, 200, ItemType.ROUPA));
        items.add(new Item("abajour", "decoracao", 20, 200, ItemType.CASA));
        items.add(new Item("escova", "Escova de dentes", 20, 200, ItemType.CASA));

        for (Item item1 : items) {
            valorFinal += item1.getPrice().doubleValue();
        }


        valorItem = cartService.calculateItemsValue(items);


        assertEquals(valorItem, BigDecimal.valueOf(valorFinal));

    }

    @Test
    void calcularValorAcimaDe500AbaixoDe1000(){

        BigDecimal valorFinal = BigDecimal.ZERO;

        List<Item> items = new ArrayList<>();
        items.add(new Item("detergente", "Detergente pra lavar loucas", 100, 200, ItemType.COZINHA));
        items.add(new Item("esponja", "Esponja pra lavar loucas", 100, 200, ItemType.COZINHA));
        items.add(new Item("Jaqueta", "Jqueta preta", 150, 200, ItemType.ROUPA));
        items.add(new Item("abajour", "decoracao", 100, 200, ItemType.CASA));
        items.add(new Item("escova", "Escova de dentes", 100, 200, ItemType.CASA));

        for (Item item : items) {
            valorFinal = valorFinal.add(item.getPrice());
        }

        BigDecimal desconto = valorFinal.multiply(BigDecimal.valueOf(0.1)); // Desconto de 10%
        BigDecimal valorFinalComDesconto = valorFinal.subtract(desconto);

        BigDecimal valorItem = cartService.calculateItemsValue(items);

        System.out.println(valorItem);
        System.out.println(valorFinalComDesconto);

        // caso eu use o assertEquals diretamentes, ele nao vai aceitar, pois o valorItem fica com mais um 0 apos a virgula
        assertEquals(0, valorItem.compareTo(valorFinalComDesconto));
    }

    @Test
    void calcularValorAcimaDe500AcimaDe1000(){

        BigDecimal valorFinal = BigDecimal.ZERO;

        List<Item> items = new ArrayList<>();
        items.add(new Item("detergente", "Detergente pra lavar loucas", 200, 200, ItemType.COZINHA));
        items.add(new Item("esponja", "Esponja pra lavar loucas", 500, 200, ItemType.COZINHA));
        items.add(new Item("Jaqueta", "Jqueta preta", 150, 200, ItemType.ROUPA));
        items.add(new Item("abajour", "decoracao", 200, 200, ItemType.CASA));
        items.add(new Item("escova", "Escova de dentes", 100, 200, ItemType.CASA));

        for (Item item : items) {
            valorFinal = valorFinal.add(item.getPrice());
        }

        BigDecimal desconto = valorFinal.multiply(BigDecimal.valueOf(0.2)); // Desconto de 20%
        BigDecimal valorFinalComDesconto = valorFinal.subtract(desconto);

        BigDecimal valorItem = cartService.calculateItemsValue(items);

        System.out.println(valorItem);
        System.out.println(valorFinalComDesconto);

        // caso eu use o assertEquals diretamentes, ele nao vai aceitar, pois o valorItem fica com mais um 0 apos a virgula
        assertEquals(0, valorItem.compareTo(valorFinalComDesconto));
    }


    @Test
    void calcularFreteAbaixoDe2KgMenosDe5Itens(){
        BigDecimal valorFinal = BigDecimal.ZERO;

        List<Item> items = new ArrayList<>();
        items.add(new Item("detergente", "Detergente pra lavar loucas", 200, 200, ItemType.COZINHA));
        items.add(new Item("esponja", "Esponja pra lavar loucas", 500, 200, ItemType.COZINHA));
        items.add(new Item("Jaqueta", "Jqueta preta", 150, 200, ItemType.ROUPA));
        items.add(new Item("abajour", "decoracao", 200, 200, ItemType.CASA));


        BigDecimal valorFrete = cartService.calculateShippingCost(items);

        // nao cobra frete
        assertEquals(BigDecimal.valueOf(0), valorFrete);


    }

    @Test
    void calcularFreteAbaixoDe2KgMaisDe5Itens(){
        BigDecimal valorFinal = BigDecimal.ZERO;

        List<Item> items = new ArrayList<>();
        items.add(new Item("detergente", "Detergente pra lavar loucas", 200, 200, ItemType.COZINHA));
        items.add(new Item("esponja", "Esponja pra lavar loucas", 500, 200, ItemType.COZINHA));
        items.add(new Item("Jaqueta", "Jqueta preta", 150, 200, ItemType.ROUPA));
        items.add(new Item("abajour", "decoracao", 200, 200, ItemType.CASA));
        items.add(new Item("escova", "Escova de dentes", 100, 200, ItemType.CASA));
        items.add(new Item("vassoura", "vassoura simples", 20, 200, ItemType.CASA));


        BigDecimal valorFrete = cartService.calculateShippingCost(items);

        BigDecimal adicional = BigDecimal.TEN;

        // nao cobra frete
        assertEquals(adicional, valorFrete);

    }


    @Test
    void calcularFreteAcimaDe2AbaixoDe10Menos5Itens(){

        BigDecimal valorFinal = BigDecimal.ZERO;
        int pesoFinal = 0;

        List<Item> items = new ArrayList<>();
        items.add(new Item("detergente", "Detergente pra lavar loucas", 200, 500, ItemType.COZINHA));
        items.add(new Item("esponja", "Esponja pra lavar loucas", 500, 500, ItemType.COZINHA));
        items.add(new Item("Jaqueta", "Jqueta preta", 150, 500, ItemType.ROUPA));
        items.add(new Item("abajour", "decoracao", 200, 800, ItemType.CASA));


        BigDecimal valorFrete = cartService.calculateShippingCost(items);

        for (Item item: items){
            pesoFinal += item.getWeight();
        }

        System.out.println(valorFrete);

        valorFinal = BigDecimal.valueOf(2 * (pesoFinal / 1000));

        assertEquals(0, valorFinal.compareTo(valorFrete));

    }

    @Test
    void calcularFreteAcimaDe2AbaixoDe10Mais5Itens(){

        BigDecimal valorFinal = BigDecimal.ZERO;
        int pesoFinal = 0;

        List<Item> items = new ArrayList<>();
        items.add(new Item("detergente", "Detergente pra lavar loucas", 200, 500, ItemType.COZINHA));
        items.add(new Item("esponja", "Esponja pra lavar loucas", 500, 500, ItemType.COZINHA));
        items.add(new Item("Jaqueta", "Jqueta preta", 150, 500, ItemType.ROUPA));
        items.add(new Item("abajour", "decoracao", 200, 800, ItemType.CASA));
        items.add(new Item("escova", "Escova de dentes", 100, 200, ItemType.CASA));
        items.add(new Item("vassoura", "vassoura simples", 20, 200, ItemType.CASA));

        BigDecimal valorFrete = cartService.calculateShippingCost(items);

        for (Item item: items){
            pesoFinal += item.getWeight();
        }

        System.out.println(valorFrete);

        valorFinal = BigDecimal.valueOf(2 * (pesoFinal / 1000));

        valorFinal = valorFinal.add(BigDecimal.TEN);


        assertEquals(0, valorFinal.compareTo(valorFrete));

    }

    @Test
    void calcularFreteAcimaDe10AbaixoDe50Menos5Itens(){

        BigDecimal valorFinal = BigDecimal.ZERO;
        int pesoFinal = 0;

        List<Item> items = new ArrayList<>();
        items.add(new Item("detergente", "Detergente pra lavar loucas", 200, 2000, ItemType.COZINHA));
        items.add(new Item("esponja", "Esponja pra lavar loucas", 500, 2000, ItemType.COZINHA));
        items.add(new Item("Jaqueta", "Jqueta preta", 150, 5000, ItemType.ROUPA));
        items.add(new Item("abajour", "decoracao", 200, 4000, ItemType.CASA));


        BigDecimal valorFrete = cartService.calculateShippingCost(items);

        for (Item item: items){
            pesoFinal += item.getWeight();
        }

        valorFinal = BigDecimal.valueOf(4 * (pesoFinal / 1000));


        assertEquals(0, valorFinal.compareTo(valorFrete));

    }


    @Test
    void calcularFreteAcimaDe10AbaixoDe50Mais5Itens(){

        BigDecimal valorFinal = BigDecimal.ZERO;
        int pesoFinal = 0;

        List<Item> items = new ArrayList<>();
        items.add(new Item("detergente", "Detergente pra lavar loucas", 200, 2000, ItemType.COZINHA));
        items.add(new Item("esponja", "Esponja pra lavar loucas", 500, 2000, ItemType.COZINHA));
        items.add(new Item("Jaqueta", "Jqueta preta", 150, 5000, ItemType.ROUPA));
        items.add(new Item("abajour", "decoracao", 200, 4000, ItemType.CASA));
        items.add(new Item("escova", "Escova de dentes", 100, 200, ItemType.CASA));
        items.add(new Item("vassoura", "vassoura simples", 20, 200, ItemType.CASA));

        BigDecimal valorFrete = cartService.calculateShippingCost(items);

        for (Item item: items){
            pesoFinal += item.getWeight();
        }

        System.out.println(valorFrete);

        valorFinal = BigDecimal.valueOf(4 * (pesoFinal / 1000));

        valorFinal = valorFinal.add(BigDecimal.TEN);


        assertEquals(0, valorFinal.compareTo(valorFrete));

    }


    @Test
    void calcularFreteAcimaDe50Menos5Itens(){

        BigDecimal valorFinal = BigDecimal.ZERO;
        int pesoFinal = 0;

        List<Item> items = new ArrayList<>();
        items.add(new Item("detergente", "Detergente pra lavar loucas", 200, 15000, ItemType.COZINHA));
        items.add(new Item("esponja", "Esponja pra lavar loucas", 500, 15000, ItemType.COZINHA));
        items.add(new Item("Jaqueta", "Jqueta preta", 150, 15000, ItemType.ROUPA));
        items.add(new Item("abajour", "decoracao", 200, 15000, ItemType.CASA));


        BigDecimal valorFrete = cartService.calculateShippingCost(items);

        for (Item item: items){
            pesoFinal += item.getWeight();
        }

        valorFinal = BigDecimal.valueOf(7 * (pesoFinal / 1000));


        assertEquals(0, valorFinal.compareTo(valorFrete));

    }

    @Test
    void calcularFreteAcimaDe50Mais5Itens(){

        BigDecimal valorFinal = BigDecimal.ZERO;
        int pesoFinal = 0;

        List<Item> items = new ArrayList<>();
        items.add(new Item("detergente", "Detergente pra lavar loucas", 200, 15000, ItemType.COZINHA));
        items.add(new Item("esponja", "Esponja pra lavar loucas", 500, 15000, ItemType.COZINHA));
        items.add(new Item("Jaqueta", "Jqueta preta", 150, 15000, ItemType.ROUPA));
        items.add(new Item("abajour", "decoracao", 200, 15000, ItemType.CASA));
        items.add(new Item("escova", "Escova de dentes", 100, 200, ItemType.CASA));
        items.add(new Item("vassoura", "vassoura simples", 20, 200, ItemType.CASA));

        BigDecimal valorFrete = cartService.calculateShippingCost(items);

        for (Item item: items){
            pesoFinal += item.getWeight();
        }


        valorFinal = BigDecimal.valueOf(7 * (pesoFinal / 1000));

        valorFinal = valorFinal.add(BigDecimal.TEN);


        assertEquals(0, valorFinal.compareTo(valorFrete));

    }


    @Test
    void testeCheckout(){


        int pesoFinal = 0;
        double valorFinal = 0.0;


        itemService.insertItem(new Item("detergente", "Detergente pra lavar loucas", 200, 15000, ItemType.COZINHA));
        itemService.insertItem(new Item("esponja", "Esponja pra lavar loucas", 500, 15000, ItemType.COZINHA));
        itemService.insertItem(new Item("Jaqueta", "Jqueta preta", 150, 15000, ItemType.ROUPA));
        itemService.insertItem(new Item("abajour", "decoracao", 200, 15000, ItemType.CASA));
        itemService.insertItem(new Item("escova", "Escova de dentes", 100, 200, ItemType.CASA));

        List<String> itemIds = new ArrayList<>();
        itemIds.add("1");
        itemIds.add("2");

        CheckoutResponseDTO checkout = cartService.checkout(itemIds);


        for(Item item: itemService.findAll()){
            valorFinal += item.getPrice().doubleValue();
        }

        double desconto = valorFinal * 0.2; // Desconto de 20%
        double valorFinalComDesconto = valorFinal - desconto;

        for (Item item: itemService.findAll()){
            pesoFinal += item.getWeight();
        }

        int shippingCost = 7 * (pesoFinal / 1000); // acima de 50kg



        CheckoutResponseDTO responseEsperada = new CheckoutResponseDTO(valorFinalComDesconto, shippingCost);

        assertEquals(responseEsperada, checkout);


    }


    @Test
    void testFindItemById() throws ItemNotFoundException {
        Item itemMock = new Item("detergente", "Detergente pra lavar loucas", 200, 15000, ItemType.COZINHA);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(itemMock));

        Item item = itemService.findById(1);
        assertEquals(itemMock, item);
    }



}