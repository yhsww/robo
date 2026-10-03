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

        while(escolha <= 0 || escolha > Cor.values().length){

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
            default: System.out.println("Posição inválida!"); return false;
        }

         System.out.println("Coordenada X");
        int posX = this.tabuleiro.definirCoordenada();
         System.out.println("Coordenada Y");
        int posY = this.tabuleiro.definirCoordenada();
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

    public void encontrouObstaculo(Robo robo){

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

        while(temRoboVivo() && vencedor == null){
            System.out.println();
            System.out.println("PARTIDA " + partida);

            for(Robo robo: this.robos){

                if(!robo.getExplodiu()){

                    moverAleatorio(robo);
                    encontrouObstaculo(robo);

                    if(!robo.getExplodiu() && robo.encontrouAlimento(fruta)){
                        vencedor = robo;
                        break;
                    }

                }

                pausa();
                this.tabuleiro.criarTabuleiro();
                
            }

            partida++;
            
        }

        pausa();
        this.tabuleiro.criarTabuleiro();
                

        for(Robo robo : this.robos){
            System.out.println("-----------");
            System.out.println(robo.toString());
            if(robo.getExplodiu() == true){
                System.out.println("Explodiu: sim");
            }else{
                System.out.println("Explodiu: não");
            }
        }

        if(vencedor == null){
            System.out.println("Ambos os robôs explodiram!");
        }else{
            System.out.println("Vencedor: " + vencedor.getCor().getTipoCor());
        }



    }


    private boolean temRoboVivo(){

        return this.robos.stream().allMatch(robo -> robo.getExplodiu() == true) ? false: true;
    }
    
}
