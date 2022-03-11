import java.util.*;
import java.lang.*;
import java.io.*;

public class Board{
    private int wordlen; //default 5 *has get()*
    private int attempts; //default 6 *has get()*
    private int mode; //default 0, debug -1, hard 1 (no yellow)
    private String[] wordlist; //list of words from imported csv file
    private String answer; //worldle word *has get()*
    private Letter[] letterstatus; //each cell contains info about response letter by letter. access thru Letter
    private Random rand = new Random(); //universal random

    //constructs board
    public Board(int wordlen, int attempts, int mode, String wordlist){
        this.wordlen = wordlen;
        this.attempts = attempts;
        this.mode = mode;
        try{
            this.wordlist = this.getFile(wordlist);
        }catch (FileNotFoundException f){
            System.out.println(f);
        }
        letterstatus = new Letter[wordlen];
        for (int i=0; i<letterstatus.length; i++){
            letterstatus[i] = new Letter(i);
        }

        //pick word, same as this(mode)
        int n = rand.nextInt(this.wordlist.length);
        answer = this.wordlist[n];
    }

    //splits file into wordlist
    public String[] getFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner fp = new Scanner(file);
        String onelongstring = "";

        while (fp.hasNext()){
            onelongstring += fp.next() + ",";
        }
        onelongstring.trim();
        onelongstring = onelongstring.replaceAll( "[^a-zA-Z,]", "");
        onelongstring = onelongstring.replaceAll( ",,,", ",");
        onelongstring = onelongstring.replaceAll(",,",",");

        wordlist = onelongstring.split(",");

        ///*the following 5 lines are literally only to be used when ur using fucking wordleans.txt
        //for some reason it puts an EMPTY space at the beginning that you cant even fucking see wtf
        String[] forsomereasonthefirstvalisfuckingempty = new String[wordlist.length-1];
        for (int i=1; i<wordlist.length; i++){
            forsomereasonthefirstvalisfuckingempty[i-1] = wordlist[i];
        }
        wordlist = forsomereasonthefirstvalisfuckingempty;
        //end random shit*/

        return wordlist;
    }

    //wordlen, attempts, answer
    public int getWordlen(){
        return wordlen;
    }
    public int getAttempts(){
        return attempts;
    }
    public String getAnswer(){
        return answer;
    }

    //prints out last guess, and corresponding green/yellow/- below
    public void displayWord(Letter[] word){
        String topr = "";
        for (int i=0; i<word.length; i++){
            topr += Character.toLowerCase(word[i].getCorrletter()) + " ";
        }
        topr += "\n";
        if (mode == 1){ //hard mode
            //for loop prints '-' instead of 'Y'
            for (int i=0; i<word.length; i++){
                if (word[i].getStatus() == 'Y'){
                    topr += "-" + " ";
                }else{
                    topr += word[i].getStatus() + " ";
                }
            }
        }else{ //debug or regular mode
            for (int i=0; i<word.length; i++){
                topr += word[i].getStatus() + " ";
            }
        }
        System.out.println(topr);
        System.out.println();
    }

    //checks if response is a word
    //TODO: change so that it doesn't check just character but takes lines into account (1)
    public boolean checkifWord(String response, String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner fp = new Scanner(file);
        return (fp.useDelimiter("\\Z").next()).contains(response);
    }

    //checks the number of occurrences of each character from the response in the answer
    public void checkOccurrences(Letter[] letters, String answer, boolean tf){
        int count;
        //for each letter in the response "letters", check how many appearances in answer
        for(int i=0; i<letters.length; i++){
            count = 0;
            for (int j=0; j<answer.length(); j++){
                if (answer.charAt(j) == letters[i].getCorrletter()){
                    count++;
                }
            }
            if (tf == false){ //not comparing to self
                letters[i].setAnscount(count);
            }else{ // comparing to self
                letters[i].setCurrcount(count);
            }
        }
    }

    //return answer correctness, and display green/yellow letters
    //TODO: (2)
    public boolean compare(String response){
        //populating "letterstatus" (represents the word guess)
        for (int i=0; i<response.length(); i++){
            letterstatus[i].setCorrletter(response.charAt(i));
            letterstatus[i].setStatus('-');
            letterstatus[i].setStatuswasjustset(false);
        }
        //if guess is exactly correct
        if (response.equalsIgnoreCase(answer)){
            for (int i=0; i<answer.length(); i++){
                letterstatus[i].setStatus('G');
            }
            displayWord(letterstatus);
            return true;
        }else{ //guess is partially wrong

            //if char matches char in answer, Green
            for (int i=0; i<response.length(); i++){
                if (Character.toUpperCase(response.charAt(i)) == Character.toUpperCase(answer.charAt(i))){
                    letterstatus[i].setStatus('G');
                    letterstatus[i].setStatuswasjustset(true);
                }
            }
            //for all remaining 'Y' and '-' chars
            for (int i=0; i<response.length(); i++){
                if (letterstatus[i].getStatuswasjustset() == false) { //not G
                    checkOccurrences(letterstatus, answer, false); //how many times the letter appears in answer
                    checkOccurrences(letterstatus, response, true); //how many times the letter appears in response

                    if (letterstatus[i].getAnscount() > 0) {//if the letter is in the answer
                        //if you guessed a letter twice
                        if (letterstatus[i].getCurrcount() > letterstatus[i].getAnscount()) {
                            //if this letter is the first occ. of itself in the answer
                            if (i == response.indexOf(response.charAt(i))) {
                                int otherchar = response.indexOf(response.charAt(i), i + 1);
                                //and if the other characters in answer aren't Green
                                if (letterstatus[otherchar].getStatus() != 'G') {
                                    letterstatus[i].setStatus('Y'); //then it's Yellow
                                } else {
                                    letterstatus[i].setStatus('-'); //doesn't count if otherchar is G or Y
                                }
                            } // otherwise, if it isn't the first occurrence, it's automatically '-'
                        } else {
                            letterstatus[i].setStatus('Y'); //guessed a letter once that's in the answer
                        }
                    }
                    letterstatus[i].setStatuswasjustset(true);
                }
            }
            displayWord(letterstatus);
            if (mode == -1){
                System.out.println("===============ANSWER:===============");
                System.out.println(answer.toUpperCase());
            }
            return false;
        }
    }



    public static void main(String[] args){
        Board b = new Board(5,6,0,"wordleans.txt");
        System.out.println(b.getAnswer());
        String let = "teaoe";
        b.compare(let);
        /*Letter[] tel = new Letter[5];
        for (int i: new int[]{0, 1, 2, 3, 4}){
            tel[i] = new Letter(0);
        }
        tel[0].setCorrletter('t');
        tel[1].setCorrletter('e');
        tel[2].setCorrletter('i');
        tel[3].setCorrletter('o');
        tel[4].setCorrletter('t');
        b.checkOccurrences(tel,let);
        for (int i: new int[]{0, 1, 2, 3, 4}){
            System.out.println(tel[i].getCurrcount());*/
    }
}