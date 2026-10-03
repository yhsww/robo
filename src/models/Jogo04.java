package models;

import java.util.ArrayList;
import java.util.List;

import util.Utilitarios;

public class Jogo04 extends Jogo{

    private List<Obstaculo> obstaculos;
    public Jogo04(){
        super();
        this.obstaculos = new ArrayList<>();
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

            case 1: {

                if(this.robos.isEmpty()){
                    novoRobo = new Robo(Cor.RED);
                }else{
                    novoRobo = new RoboInteligente(Cor.RED);
                }
                 break;
                }
            case 2: {

                if(this.robos.isEmpty()){
                    novoRobo = new Robo(Cor.BLUE);
                }else{
                    novoRobo = new RoboInteligente(Cor.BLUE);
                }
                 break;
                }
            case 3: {

                if(this.robos.isEmpty()){
                    novoRobo = new Robo(Cor.GREEN);
                }else{
                    novoRobo = new RoboInteligente(Cor.GREEN);
                }
                 break;
                }
            case 4: {

                if(this.robos.isEmpty()){
                    novoRobo = new Robo(Cor.WHITE);
                }else{
                    novoRobo = new RoboInteligente(Cor.WHITE);
                }
                 break;
                }
            case 5: {

                if(this.robos.isEmpty()){
                    novoRobo = new Robo(Cor.DARK);
                }else{
                    novoRobo = new RoboInteligente(Cor.DARK);
                }
                 break;
                }
            case 6: {

                if(this.robos.isEmpty()){
                    novoRobo = new Robo(Cor.YELLOW);
                }else{
                    novoRobo = new RoboInteligente(Cor.YELLOW);
                }
                 break;
                }
        }
        
         System.out.println("Coordenada X");
        int posX = tabuleiro.definirCoordenada();
         System.out.println("Coordenada Y");
        int posY = tabuleiro.definirCoordenada();
        novoRobo.setPosX(posX);
        novoRobo.setPosY(posY);

        if(this.tabuleiro.adicionarRobo(novoRobo)){
            this.robos.add(novoRobo);
            System.out.println("Robô adicionado com sucesso!");
            return true;
        }

        System.out.println("Posição inválida!");
        return false;
        
    }

    public boolean autorizarPartida(){

          if(this.robos.isEmpty()){
            System.out.println("Adicione robôs para iniciar a partida!");
            return false;
        }

        if(this.fruta == null){
            System.out.println("Adicione uma fruta para iniciar a partida!");
            return false;
        }

        if(this.obstaculos.isEmpty()){
            System.out.println("Adicione obstáculos para iniciar a partida!");
            return false;
        }

        return true;
       
    }

    public boolean adicionarObstaculo(){

        Obstaculo obstaculo = null;

        System.out.println("Escolha um obstáculo para inserir no tabuleiro: 1 - rocha | 2 - bomba");
        int escolha = Utilitarios.inteiroValido();
        
        int posicao = this.obstaculos.size();

        switch (escolha) {
            case 1: obstaculo = new Rocha(posicao); break;
            case 2: obstaculo = new Bomba(posicao); break;
            default: System.out.println("Opção indisponível!"); return false;
        }

        
        System.out.println("Coordenada X");
        int posX = tabuleiro.definirCoordenada();
        System.out.println("Coordenada Y");
        int posY = tabuleiro.definirCoordenada();
        obstaculo.setPosX(posX);
        obstaculo.setPosY(posY);

        if(this.tabuleiro.adicionarObstaculos(obstaculo)){
            this.obstaculos.add(obstaculo);
            System.out.println("Obstáculo adicionado com sucesso!");
            return true;
        }

        System.out.println("Posição inválida!");
        return false;

    }

    private void encontrouObstaculo(Robo robo){

        for(Obstaculo obstaculo: this.obstaculos){

            if(obstaculo.getPosX() == robo.getPosX() && obstaculo.getPosY() == robo.getPosY()){
                
                obstaculo.bater(robo);
                if(obstaculo instanceof Bomba){
                    break;
                }
            }

        }

    }

    public void iniciarPartida(){

        if(!autorizarPartida()){
            return;
        }

        Robo vencedor = null;

        int partida = 1;

        System.out.println("Preparando o tabuleiro...");
        pausa();
        this.tabuleiro.criarTabuleiro();
        System.out.println("Comece o jogo!");   


        while(temRoboVivo() && vencedor == null){
            System.out.println();
            System.out.println("PARTIDA " + partida);
            

            for(Robo robo: this.robos){

                pausa();
                this.tabuleiro.criarTabuleiro();

                 if(this.robos.get(0).equals(robo)){
                    System.out.println("\nRobô normal - " + robo.getCor().getTipoCor());
                }else{
                    System.out.println("\nRobô inteligente - " + robo.getCor().getTipoCor());
                }

                if(!robo.getExplodiu()){

                    moverAleatorio(robo);
                    encontrouObstaculo(robo);

                    if(!robo.getExplodiu() && robo.encontrouAlimento(fruta)){
                        vencedor = robo;
                        break;
                    }

                }

                if(robo.getExplodiu()){

                    int indice = 0;
                    if(this.robos.indexOf(robo) == 0){
                        indice = 1;
                    }else{
                        indice = 0;
                    }

                    vencedor = this.robos.get(indice);
                    break;
                }


                pausa();
                this.tabuleiro.criarTabuleiro();
                
            }

            partida++;     
            
        }

        

        for(Robo robo : this.robos){
            System.out.println("-----------");
            System.out.println(robo.toString());
            if(robo.getExplodiu() == true){
                System.out.println("Explodiu: sim");
            }else{
                System.out.println("Explodiu: não");
            }
        }

        System.out.println("Vencedor: " + vencedor.getCor().getTipoCor());
       
    }


    private boolean temRoboVivo(){

        return this.robos.stream().allMatch(robo -> robo.getExplodiu() == true) ? false: true;
    }
    
   
}
