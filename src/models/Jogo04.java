package models;

import java.util.ArrayList;
import java.util.List;

import util.Utilitarios;

public class Jogo04 extends Jogo03{
    
    private RoboInteligente roboInteligente;
    private Robo02 roboNormal;
    private List<Rocha> rochas;
    private List<Bomba> bombas;
    private static final int MAXIMO_OBSTACULOS = 6;

    public Jogo04(){
        super();
        this.rochas = new ArrayList<>();
        this.bombas = new ArrayList<>();
    }

    public void adicionarObstaculos(){

        if(this.bombas.size() + this.rochas.size() >= MAXIMO_OBSTACULOS){
            System.out.println("O limite de obstáculos que podem ser adicionados já foi atingido!");
            return;
        }

        Obstaculo obstaculo;

        System.out.println("Qual tipo de obstáculo deseja adicionar ao tabuleiro? ");
        System.out.println("1 - Rocha");
        System.out.println("2 - Bomba");
        System.out.println("Insira sua escolha: ");
        int escolha = Utilitarios.inteiroValido();

        while (escolha < 1 || escolha > 3){
            System.out.println("Opção inválida. Insira novamente: ");
            escolha = Utilitarios.inteiroValido();
        }

        System.out.println("\n\n");
        System.out.println("Defina a posição em que deseja colocar o obstaculo no tabuleiro [4x4]: ");

        System.out.println("Posição X: ");
        int posX = Utilitarios.inteiroValido();

        System.out.println("Posição Y: ");
        int posY = Utilitarios.inteiroValido();

        if((posX < 0 || posX > Tabuleiro.DIMENSAO_TABULEIRO) || (posY < 0 || posY > Tabuleiro.DIMENSAO_TABULEIRO) || (posX == 0 && posY == 0) || (posX == this.fruta.getPosX() && posY == this.fruta.getPosY())){
            System.out.println("Posição inválida!");
            return;
        }

        for(Rocha rocha : this.rochas){
            if(rocha.getPosX() == posX && rocha.getPosY() == posY){
                System.out.println("Já existe uma rocha nessa posição!");
                return;
            }
        }

        for(Bomba bomba : this.bombas){
            if(bomba.getPosX() == posX && bomba.getPosY() == posY){
                System.out.println("Já existe uma bomba nessa posição!");
                return;
            }
        }

        if(escolha == 1){

            int novoId = this.rochas.size();
            obstaculo = new Rocha(novoId);
            obstaculo.setPosX(posX);
            obstaculo.setPosY(posY);

        }else{
            
            int novoId = this.bombas.size();
            obstaculo = new Bomba(novoId);
            obstaculo.setPosX(posX);
            obstaculo.setPosY(posY);
        }
    
        if(obstaculo instanceof Rocha rocha){
            this.rochas.add(rocha);
        }else if(obstaculo instanceof Bomba bomba){
            this.bombas.add(bomba);
        }

        System.out.println("Obstáculo adicionado com sucesso!");


        }


    public void posicionarAlimento(){

        
        if(this.fruta != null){
           
            String resp = null;

            while(!resp.equalsIgnoreCase("s") && !resp.equalsIgnoreCase("n")){
                System.out.println("A fruta já foi escolhida. Deseja realizar alterações? [s/n]");
                resp = Utilitarios.stringValida();
            }

            if(resp.equalsIgnoreCase("n")){
                return;
            }
           
        }


        //abacaxi, banana, maca, uva

        System.out.println("FRUTAS DISPONÍVEIS");
        for(Fruta fruta: Fruta.values()){
            System.out.println(fruta.getNumtipoFruta() + " - " + fruta.getTipoFruta());
        }

        System.out.println("Insira o número da fruta escolhida: ");
        int escolha = Utilitarios.inteiroValido();

        switch (escolha) {
            case 1: this.fruta = Fruta.MACA; break;
            case 2: this.fruta = Fruta.BANANA; break;
            case 3: this.fruta = Fruta.ABACAXI; break;
            case 4: this.fruta = Fruta.UVA; break;
            default: System.out.println("Fruta indisponível!"); return;
        }

        System.out.println("Defina a posição em que deseja colocar a fruta no tabuleiro [4x4]: ");

        System.out.println("Posição X: ");
        int posX = Utilitarios.inteiroValido();

        System.out.println("Posição Y: ");
        int posY = Utilitarios.inteiroValido();

        if((posX < 0 || posX > Tabuleiro.DIMENSAO_TABULEIRO) || (posY < 0 || posY > Tabuleiro.DIMENSAO_TABULEIRO) || (posX == 0 && posY == 0)){
            System.out.println("Posição inválida!");
            return;
        }

        for(Rocha rocha : this.rochas){
            if(rocha.getPosX() == posX && rocha.getPosY() == posY){
                System.out.println("Já existe uma rocha nessa posição!");
                return;
            }
        }

        for(Bomba bomba : this.bombas){
            if(bomba.getPosX() == posX && bomba.getPosY() == posY){
                System.out.println("Já existe uma bomba nessa posição!");
            }
        }

        this.fruta.setPosX(posX);
        this.fruta.setPosY(posY);

        System.out.println("A fruta " + this.fruta.getTipoFruta() + " está na posição " + "[" + this.fruta.getPosX() + ", " + this.fruta.getPosY() + "]");
        System.out.println("Tente pegá-la!");

    }


    public void iniciarJogo(){

        if(this.rochas == null && this.bombas == null){
            System.out.println("Adicione obstáculos para iniciar o jogo!");
        } 

          if(this.roboInteligente == null || this.roboNormal == null){
            System.out.println("Adicione robôs para iniciar o jogo!");
            return;
        }

        if(this.fruta == null){
            System.out.println("Adiciona uma fruta para iniciar o jogo!");
            return;
        }

        boolean encontrouBomba1 = false;
        boolean encontrouBomba2 = false;

        while(!this.roboInteligente.encontrouAlimento(this.fruta) && !this.roboNormal.encontrouAlimento(this.fruta) 
            && encontrouBomba1 == false && encontrouBomba2 == false){

            boolean lancaExcecao1 = this.roboInteligente.mover();
            boolean lancaExcecao2 = this.roboNormal.mover();

            while(lancaExcecao1){
                
                new MovimentoInvalidoException();
                lancaExcecao1 = this.roboInteligente.mover();
                this.roboInteligente.setQtdMovimentosInvalidos(this.roboInteligente.getQtdMovimentosInvalidos() + 1);
            }

            this.roboInteligente.setQtdMovimentosValidos(this.roboInteligente.getQtdMovimentosValidos() + 1);

             if(this.rochas != null){

                Rocha busca = null;

                for(Rocha rocha : this.rochas){
                    if(rocha.getPosX() == this.roboInteligente.getPosX()
                    && rocha.getPosY() == this.roboInteligente.getPosY()){
                        busca = rocha;
                        busca.bater(this.roboInteligente);
                        break;
                    }
                }

            }

            if(this.bombas != null){

                Bomba busca = null;

                for(Bomba bomba : this.bombas){
                    if(bomba.getPosX() == this.roboInteligente.getPosX()
                    && bomba.getPosY() == this.roboInteligente.getPosY()){
                        busca = bomba;
                        busca.bater(this.roboInteligente);
                        encontrouBomba1 = true;
                        break;
                    }
                }
            }

            
            while(lancaExcecao2){
                new MovimentoInvalidoException();
                lancaExcecao2 = this.roboNormal.mover();
                this.roboNormal.setQtdMovimentosInvalidos(this.roboNormal.getQtdMovimentosInvalidos() + 1);
            }

            this.roboNormal.setQtdMovimentosValidos(this.roboNormal.getQtdMovimentosValidos() + 1);

            if(this.rochas != null){

                Rocha busca = null;

                for(Rocha rocha : this.rochas){
                    if(rocha.getPosX() == this.roboNormal.getPosX()
                    && rocha.getPosY() == this.roboNormal.getPosY()){
                        busca = rocha;
                        busca.bater(this.roboNormal);
                        break;
                    }
                }

            }

        
            if(this.bombas != null){

                Bomba busca = null;

                for(Bomba bomba : this.bombas){
                    if(bomba.getPosX() == this.roboNormal.getPosX()
                    && bomba.getPosY() == this.roboNormal.getPosY()){
                        busca = bomba;
                        busca.bater(this.roboNormal);
                        encontrouBomba2 = true;
                        break;
                    }
                }

            }

            


        }

        Robo02 vencedor = null;

        if(!encontrouBomba1 && !encontrouBomba2){
            if(this.roboInteligente.encontrouAlimento(fruta)){
                vencedor = this.roboInteligente;     
            }else{
                vencedor = this.roboNormal;
            }

        }

        if(encontrouBomba1 || encontrouBomba2){
            if(encontrouBomba1){
                vencedor = this.roboNormal;
            }else{
                vencedor = this.roboInteligente;
            }
        }

        System.out.println("Vitória do robô " + vencedor.getCor().getTipoCor());
        System.out.println("\n\n");

         System.out.println("-----------------------------");
        System.out.println();
        System.out.println("Robô Inteligente - " + this.roboInteligente.getCor().getTipoCor());
        System.out.println("Quantidade de movimentos válidos: " + this.roboInteligente.getQtdMovimentosValidos());
        System.out.println("Quantidade de movimentos inválidos: " + this.roboInteligente.getQtdMovimentosValidos());
        System.out.println("Total de movimentos: " + this.roboInteligente.getQtdMovimentosInvalidos() + this.roboInteligente.getQtdMovimentosValidos());
        System.out.println("-----------------------------");
        System.out.println("Robô Normal - " + this.roboNormal.getCor().getTipoCor());
        System.out.println("Quantidade de movimentos válidos: " + this.roboNormal.getQtdMovimentosValidos());
        System.out.println("Quantidade de movimentos inválidos: " + this.roboNormal.getQtdMovimentosValidos());
        System.out.println("Total de movimentos: " + this.roboNormal.getQtdMovimentosInvalidos() + this.roboNormal.getQtdMovimentosValidos());
        System.out.println("\n\n");
        System.out.println("FIM DE JOGO");



    }


    
    


}
