package models;

public class Robo01 {

    protected int posX;
    protected int posY;
    protected Cor cor;
    protected Movimento movimento;
    protected boolean podeSeMover;

    public Robo01(Cor cor){
        this.posX = 0;
        this.posY = 0;
        this.cor = cor;
        this.movimento = null;
        this.podeSeMover = true;
    }

    public void setPodeseMover(boolean podeSeMover){
        this.podeSeMover = podeSeMover;
    }

    public boolean getPodeSeMover(){
        return this.podeSeMover;
    }

    public void setMovimento(Movimento movimento){
        this.movimento = movimento;
    }

    public Movimento getMovimento(){
        return this.movimento;
    }

    public void setPosX(int posX){
        this.posX = posX;
    }

    public int getPosX(){
        return this.posX;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    public int getPosY(){
        return this.posY;
    }

    public void setCor(Cor cor){
        this.cor = cor;
    }

    public Cor getCor(){
        return this.cor;
    }

    public boolean mover(String movimento){

        movimento.toLowerCase();
        movimento.trim();

        Movimento novoMovimento = null;
        int posX = this.posX;
        int posY = this.posY;

        if(movimento.equals(Movimento.UP.getTipoMovimento())){

            novoMovimento = Movimento.UP;
            posY += 1;

        }else if(movimento.equals(Movimento.DOWN.getTipoMovimento())){

            novoMovimento = Movimento.DOWN;
            posY -= 1;
            
        }else if(movimento.equals(Movimento.RIGHT.getTipoMovimento())){

            novoMovimento = Movimento.RIGHT;
            posX += 1;

        }else if(movimento.equals(Movimento.LEFT.getTipoMovimento())){
            novoMovimento = Movimento.LEFT;
            posX -= 1;
        }else{
            return false;
        }

        if((posX < 0 || posX > 4) || (posY < 0 || posY > 4)){
            return false;
        }

        this.movimento = novoMovimento;
        this.posX = posX;
        this.posY = posY;
        return true;

    }

    public boolean mover(int movimento){

        Movimento busca = null;
        Movimento novoMovimento = null;
        int posX = this.posX;
        int posY = this.posY;

        for(Movimento mov : Movimento.values()){
            if(mov.getNumTipoMovimento() == movimento){
                busca = mov;
                break;
            }
        }

        if(busca == null){
            return false;
        }


        if(movimento == Movimento.UP.getNumTipoMovimento()){

            novoMovimento = Movimento.UP;
            posY += 1;

        }else if(movimento == Movimento.DOWN.getNumTipoMovimento()){

            novoMovimento = Movimento.DOWN;
            posY -= 1;
            
        }else if(movimento == Movimento.RIGHT.getNumTipoMovimento()){

            novoMovimento = Movimento.RIGHT;
            posX += 1;

        }else if(movimento == Movimento.LEFT.getNumTipoMovimento()){

            novoMovimento = Movimento.LEFT;
            posX -= 1;
        }else{
            return false;
        }

        if((posX < 0 || posX > 4) || (posY < 0 || posY > 4)){
            return false;
        }

        this.movimento = novoMovimento;
        this.posX = posX;
        this.posY = posY;
        return true;


    }
    
    public boolean encontrouAlimento(Fruta fruta){

        if(this.posX == fruta.getPosX() && this.posY == fruta.getPosY()){
            return true;
        }

        return false;
    }

}
