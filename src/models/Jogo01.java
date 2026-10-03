package models;

import util.Utilitarios;

public class Jogo01 extends Jogo{

    public Jogo01(){
        super();
    }

    public void iniciarPartida(){

        if(!autorizarPartida()){
            return;
        }

        Robo robo = this.robos.get(0);

        System.out.println("Como deseja mover o robô?\n1 - Insira o número correspondente à direção desejada\n2 - Insira o nome da direção desejada");
        int escolha = Utilitarios.inteiroValido();

        while(escolha != 1 && escolha != 2){
            System.out.println("Opção inválida. Insira novamente: ");
            escolha = Utilitarios.inteiroValido();
        }

        int partida = 1;
        while(!robo.encontrouAlimento(this.fruta)){
            System.out.println();
            System.out.println("PARTIDA " + partida);

            pausa();   
            this.tabuleiro.criarTabuleiro();
        
                for(Movimento direcao: Movimento.values()){
                    System.out.println(direcao.getNumTipoMovimento() + " - " + direcao.getTipoMovimento());
                }


                try{

                    if(escolha == 1){
                        System.out.println("Número da direção desejada: ");
                        int direcaoDesejada = Utilitarios.inteiroValido();
                            robo.mover(direcaoDesejada);
                    }else{

                        System.out.println("Insira a direção desejada: ");
                        String direcaoDesejada = Utilitarios.stringValida();
                        robo.mover(direcaoDesejada);
                    }

                    robo.setQtdMovimentosValidos(robo.getQtdMovimentosValidos() + 1);

                }catch(MovimentoInvalidoException e){

                    robo.setQtdMovimentosInvalidos(robo.getQtdMovimentosInvalidos() + 1);
                    System.out.println(e.getMessage());
                }  

                partida++;
                
        }

        this.tabuleiro.criarTabuleiro();
        System.out.println("Parabéns! O robô alcançou a fruta.");
        System.out.println(robo.toString());

    }



        
    }
    

