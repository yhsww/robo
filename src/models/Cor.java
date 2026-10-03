package models;

    public enum Cor {

    //red, blue, green, white, dark, yellow
    
    RED("red", 1), BLUE("blue", 2), 
    GREEN("green", 3), WHITE("white", 4), 
    DARK( "dark", 5), YELLOW("yellow", 6);

    private String tipoCor;
    private int numCor;

    Cor(String tipoCor, int numCor){
        this.tipoCor = tipoCor;
        this.numCor = numCor;
    }

    public int getNumCor(){
        return this.numCor;
    }

    public String getTipoCor(){
        return this.tipoCor;
    }
    
}
    
