package models;

public class MovimentoInvalidoException extends RuntimeException{

    public MovimentoInvalidoException(String movimento){

        super(movimento + " é inválido!");
    }


    public MovimentoInvalidoException(){

        super("O movimento requisitado é inválido!");
    }

    
}
