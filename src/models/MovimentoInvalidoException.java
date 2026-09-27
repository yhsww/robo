package models;

public class MovimentoInvalidoException extends Exception{

    public MovimentoInvalidoException(String movimento){

        System.out.println(movimento + " é inválido! O robô não pode sair do tabuleiro.");
    }


    public MovimentoInvalidoException(){

        System.out.println("Movimento inválido! O robô não pode acessar a casa desejada.");
    }
    
}
