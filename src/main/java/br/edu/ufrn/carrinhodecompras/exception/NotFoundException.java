package br.edu.ufrn.carrinhodecompras.exception;

public abstract class NotFoundException extends Exception {

  public NotFoundException(String message) {
    super(message);
  }
}
