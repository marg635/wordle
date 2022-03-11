public class Letter{
    private char status; //green 'g', yellow 'y', not guessed '-'
    private boolean statuswasjustset; //used when looping to check for yellows after green
    private char corrletter; //letter that's part of the response
    private int x; //xpos
    private int anscount; //count of how many times this letter appears in the answer
    private int currcount; //count of how many times this letter appears in its own word

    public Letter(int x){
        this.x = x;
        status = '-';
        statuswasjustset = false;
        anscount = 0;
    }

    //status
    public char getStatus(){
        return status;
    }
    public void setStatus(char status){
        this.status = status;
    }

    //statuswasjustset
    public boolean getStatuswasjustset(){
        return statuswasjustset;
    }
    public void setStatuswasjustset(boolean tf){
        statuswasjustset = tf;
    }

    //corrletter
    public char getCorrletter(){
        return corrletter;
    }
    public void setCorrletter(char letter){
        corrletter = letter;
    }

    //x
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }

    //anscount
    public int getAnscount(){
        return anscount;
    }
    public void setAnscount(int anscount){
        this.anscount = anscount;
    }

    //currcount
    public int getCurrcount(){
        return currcount;
    }
    public void setCurrcount(int currcount){
        this.currcount = currcount;
    }


    public static void main(String[] args){}
}