package models;

public enum Fruta {

    MACA("maçã", 1), BANANA("banana", 2), ABACAXI("abacaxi", 3), UVA("uva", 4);

    private int numTipoFruta;
    private String tipoFruta;
    private int posX;
    private int posY;

    Fruta(String tipoFruta, int numTipoFruta){
        this.tipoFruta = tipoFruta;
        this.numTipoFruta = numTipoFruta;
        this.posX = 0;
        this.posY = 0;
    }

    public void setPosX(int posX){
        this.posX = posX;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public int getNumtipoFruta(){
        return this.numTipoFruta;
    }

    public String getTipoFruta(){
        return this.tipoFruta;
    }

    
    
}
