package models;

import java.util.ArrayList;
import java.util.List;

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

        
        System.out.println("Preparando o tabuleiro...");
        pausa();
        this.tabuleiro.criarTabuleiro();
        System.out.println("Comece o jogo!");

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

                }catch(MovimentoInvalidoException e){

                    System.out.println(e.getMessage());
                }  

                partida++;
                
        }

        this.tabuleiro.criarTabuleiro();
        System.out.println("Parabéns! O robô alcançou a fruta.");
        System.out.println(robo.toString());

    }

    public boolean adicionarRobo(){

        Robo novoRobo = null;

        List<Cor> coresDisponiveis = new ArrayList<>(List.of(Cor.values()));

        if(!this.robos.isEmpty()){
            
            for(Robo robo: this.robos){
                coresDisponiveis.remove(robo.getCor());
            }
        }

        System.out.println("CORES DISPONÍVEIS");
        for(Cor cor: coresDisponiveis){
            System.out.println(cor.getNumCor() + " - " + cor.getTipoCor());
        }

        System.out.println("Selecione o número da cor desejada para o robô: ");
        int escolha = Utilitarios.inteiroValido();

        while(escolha <= 0 || escolha > Cor.values().length || jaTemRoboDessaCor(escolha)){

            System.out.println("A cor escolhida não está disponível. Selecione uma cor válida: ");
            escolha = Utilitarios.inteiroValido();

        }


        switch (escolha) {

            case 1: novoRobo = new Robo(Cor.RED); break;
            case 2: novoRobo = new Robo(Cor.BLUE); break;
            case 3: novoRobo = new Robo(Cor.GREEN); break;
            case 4: novoRobo = new Robo(Cor.WHITE); break;
            case 5: novoRobo = new Robo(Cor.DARK); break;
            case 6: novoRobo = new Robo(Cor.YELLOW); break;
        }

        novoRobo.setPosX(1);
        novoRobo.setPosY(1);

        if(this.tabuleiro.adicionarRobo(novoRobo)){
            this.robos.add(novoRobo);
            System.out.println("Robô adicionado com sucesso!");
            return true;
        }

        System.out.println("Posição inválida!");
        return false;


    }



        
    }
    

