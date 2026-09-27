package models;

public enum Movimento {

    UP("up", 1), DOWN("down", 2), RIGHT("right", 3), LEFT("left", 4);

    private String tipoMovimento;
    private int numTipoMovimento;

    Movimento(String tipoMovimento, int numtTipoMovimento){
        this.tipoMovimento = tipoMovimento;
        this.numTipoMovimento = numtTipoMovimento;
    }

    public int getNumTipoMovimento(){
        return this.numTipoMovimento;
    }

    public String getTipoMovimento(){
        return this.tipoMovimento;
    }
    
}
