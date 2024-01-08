import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Game{
    public static void main(String[] args) throws FileNotFoundException {
        Scanner systemin = new Scanner(System.in); //scanner fromm command line
        int mode; //hard = 1; debug = -1; default = 0
        String response; //user current guess
        int turn = 0; //which turn the user is on rn
        boolean checkresp = false; //checks whether response == answer

        //create board
        System.out.println("====================MARGLE (Wordle but not bought by the NYT)====================");
        System.out.print("Enter normal mode (0) or hard mode (1): ");
        mode = systemin.nextInt();
        //catches all non-[0,1,-1] ints
        while (mode != -1 && mode != 0 && mode != 1){
            System.out.println("Try again...");
            System.out.print("Enter normal mode (0) or hard mode (1): ");
            mode = systemin.nextInt();
        }
        if (mode == -1){
            System.out.println("================================test================================");
        }


        //!!!change the wordle file here!!!
        String activewordlist = "fakewordle.txt";
        Board theboard = new Board(5,6,mode,activewordlist);

        //continue game until u guess right or run out of turns
        while (checkresp == false && turn < theboard.getAttempts()){
            System.out.print("Guess a " + theboard.getWordlen() + "-letter word: ");
            response = systemin.next();

            while (response.length() != theboard.getWordlen() || theboard.checkifWord(response,"fakewordle.txt") == false){
                System.out.println("Sorry, that wasn't an acceptable word.");
                System.out.println("=====================================");
                System.out.print("Guess a " + theboard.getWordlen() + "-letter word: ");
                response = systemin.next();
            }
            if (mode != -1){
                turn++;
            }
            System.out.println("Turn " + turn + " of " + theboard.getAttempts() + ": \n");
            checkresp = theboard.compare(response);
            if (checkresp == true || turn >= theboard.getAttempts()){
                break;
            }
            System.out.println("=====================================");
            System.out.println("Try again.");
        }



        //winning and losing messages + bonus!
        if (checkresp){
            System.out.println("Congrats, you got it!");
            System.out.println(theboard.getAnswer().toUpperCase() + " was the correct word.");
            //bonus levels (basically the same as above, just diff board)
            if (systemin.nextInt() == 6666){
                System.out.println("==============================BONUS LEVELS UNLOCKED==============================");
                System.out.println("Which version would you like to play?");
                System.out.println("*Non-word guesses allowed");
                System.out.println("Bonus levels: Presidents (-9), Cities (-8)");
                int choice = systemin.nextInt();
                String slist; //corr to .txt files in src
                String sresponse; //user current guess
                int sturn = 0; //which turn the user is on rn
                boolean scheckresp = false; //checks whether response == answer

                //secret level
                if (choice == -9 || choice == -8 || choice == -7){
                    //which secret level
                    if (choice == -9){
                        slist = "presidentdle.txt";
                    }else if (choice == -8){
                        slist = "citydle.txt";
                    }else if (choice == -7){
                        slist = "messidle.txt";
                    }else{
                        slist = "";
                    }
                    Board secretboard = new Board(6, 6, 0, slist);

                    while (scheckresp == false && sturn < secretboard.getAttempts()){
                        System.out.print("Guess a " + secretboard.getWordlen() + "-letter word: ");
                        sresponse = systemin.next();
                        while (sresponse.length() != secretboard.getWordlen()){
                            System.out.println("That wasn't a " + secretboard.getWordlen() + "-letter guess.");
                            System.out.println("=====================================");
                            System.out.print("Guess a " + secretboard.getWordlen() + "-letter word: ");
                            sresponse = systemin.next();
                        }
                        sturn++;
                        System.out.println("Turn " + sturn + " of " + secretboard.getAttempts() + ": \n");
                        scheckresp = secretboard.compare(sresponse);
                        if (scheckresp == true || sturn >= secretboard.getAttempts()){
                            break;
                        }
                        System.out.println("=====================================");
                        System.out.println("Try again.");
                    }
                    if (scheckresp){
                        System.out.println("Congrats, you got it!");
                        System.out.println(secretboard.getAnswer().toUpperCase() + " was the correct word.");
                    }else{
                        System.out.println("You lost... ");
                        System.out.println(secretboard.getAnswer().toUpperCase() + " was the correct word.");
                    }
                }
            }
        }else{
            System.out.println("You lost... ");
            System.out.println(theboard.getAnswer().toUpperCase() + " was the correct word.");
        }
        System.out.println("=====================================");






    }
}