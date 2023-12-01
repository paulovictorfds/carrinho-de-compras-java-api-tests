package br.edu.ufrn.carrinhodecompras.exception;

public class ItemNotFoundException extends NotFoundException {

  public ItemNotFoundException() {
    super("Item não encontrado");
  }
}
