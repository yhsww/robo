package models;

import util.Utilitarios;

public class Jogo01 {

    protected Tabuleiro tabuleiro;
    private Robo01 robo;
    protected Fruta fruta;

    public Jogo01(){
        this.tabuleiro.criarTabuleiro();
    }

    public void adicionarRobo(){

        if(this.robo != null){
           
            String resp = null;

            while(!resp.equalsIgnoreCase("s") && !resp.equalsIgnoreCase("n")){
                System.out.println("O robô já foi configurado. Deseja realizar alterações? [s/n]");
                resp = Utilitarios.stringValida();
            }

            if(resp.equalsIgnoreCase("n")){
                return;
            }
           
        }

        Cor corNova = null;
        
        System.out.println("CORES DISPONÍVEIS");
        for(Cor cor: Cor.values()){
            System.out.println(cor.getNumCor() + " - " + cor.getTipoCor());
        }
      
        System.out.println("Insira o número da cor do robô: ");
        int escolha = Utilitarios.inteiroValido();

        switch (escolha) {
            case 1: corNova = Cor.VERMELHO; break;
            case 2: corNova = Cor.AZUL; break;
            case 3: corNova = Cor.VERDE; break;
            case 4: corNova = Cor.BRANCO; break;
            case 5: corNova = Cor.PRETO; break;
            case 6: corNova = Cor.AMARELO; break;
            default: System.out.println("Cor indisponível!"); return;
        }

        this.robo = new Robo01(corNova);

        System.out.println("Robô adicionado com sucesso!");

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

        this.fruta.setPosX(posX);
        this.fruta.setPosY(posY);

        System.out.println("A fruta " + this.fruta.getTipoFruta() + " está na posição " + "[" + this.fruta.getPosX() + ", " + this.fruta.getPosY() + "]");
        System.out.println("Tente pegá-la!");

    }

    public void iniciarJogo(){

        if(this.robo == null){
            System.out.println("Adicione um robô para iniciar o jogo!");
            return;
        }

        if(this.fruta == null){
            System.out.println("Adicione uma fruta para iniciar o jogo!");
            return;
        }

        while(!this.robo.encontrouAlimento(this.fruta)){

            System.out.println("Mover robô [ 1 - número/ 2 - texto]?");
            int numMovimento = Utilitarios.inteiroValido();

            while (numMovimento != 1 && numMovimento != 2) {
                System.out.println("Opção inválida. Insira novamente: ");
                numMovimento = Utilitarios.inteiroValido();

            }

            System.out.println("Escolha uma direção para mover o robô!");

            for(Movimento direcao : Movimento.values()){
            System.out.println(direcao.getNumTipoMovimento() + " - " + direcao.getTipoMovimento());
            }

            boolean lancaExcecao = false;

           if(numMovimento == 1){

                int numDirecao = Utilitarios.inteiroValido();
                lancaExcecao = robo.mover(numDirecao);

                while(lancaExcecao){
                    new MovimentoInvalidoException();

                    System.out.println("Insira uma posição válida: ");
                    numDirecao = Utilitarios.inteiroValido();
                    lancaExcecao = robo.mover(numMovimento);

                }
                
            }else{

                String tipoDirecao = Utilitarios.stringValida();
                lancaExcecao = robo.mover(tipoDirecao);

                while(lancaExcecao){

                    new MovimentoInvalidoException(tipoDirecao);

                    System.out.println("Insira uma posição válida: ");
                    tipoDirecao = Utilitarios.stringValida();
                    lancaExcecao = robo.mover(tipoDirecao);
                }

            }

        }

        System.out.println("\n\n");
        System.out.println("Parabéns! O robô conseguiu alcançar a fruta!");
        System.out.println("FIM DE JOGO");

    }
    
}
