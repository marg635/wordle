User input allowed:

Game.java
Calling the board (combo of predetermined settings + user input):
    * Wordlen and Attempts: set as 5, 6 in normal game and 6, 6 in bonus
    * Mode: user inputs any integer (catches non-0/1/-1). Mode defaults to 0 for bonus
    * Wordlist: predetermined (see Board.java)
User-allowed guesses:
    * anything- catches words of the incorrect length, and non-words
    * //TODO: check if the current response is in the dict. not just a word part (1)
    //TODO: *currently, "ases" is allowed if "gases" is a part of the dict
    * (bonus: access 6666)
Bonus mode user-allowed input:
    * stops if anything except acceptable level numbers are input (-9, -8, etc.)
    * in bonus mode, all guesses are allowed (including non-words)
    * all else is exactly the same as the regular game

Board.java
1. Wordlen and Attempts can be changed to any integer with no problems
2. Mode is technically fine as anything (set thru Game), but keep at 0, 1, or -1
to avoid confusion
3. Wordlist must be a list of words of the same length.. possibly needs to be separated
by returns? (\n) it's fine if it has non-alpha characters

Letter.java
N/A
========================================
//TODO: (2) changes to compare()
    (1) doesn't work if you guess more than 3 of the same letter and there are 2 of the letter
    in that word. e.g.
    word is abccd
    guess is ccccc
    result: --g-- only shows one g despite two c's in abccd
    (2) doesn't work if ttbcd you guess bbbbb
    result should be --g--. result is y-g--
    problem is , when you guess more than 3 of the same letter , it says index 0 is Y if the
    (3) ttbcd guess: titty
    result should be g-y--
    result is g----

    basically everything messes up if you guess more than three of the same letter but the answer only has
    2 of that letter
    words like nanny, sissy won't mess it up tho bc they have three letters so the curr count = answer count


