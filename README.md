# 2048
My CIS 1200 Final Project: 2048 game


=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: jfrem
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays:

  My 2048 game board is represented by a int 2D array. Each integer cell in the array is a tile on my game board.
  If a cell has a zero in it, that cell on the board is empty. If it is not empty, its value will indicate the
  tile's number.

  I made the design choice of representing my game board as a 2-D array because it allowed me to easily
  visualize and manipulate the board's state by indexing the array using row and column values. Additionally,
  I found that using a 2D array allowed me to efficiently create methods for my game board (i.e. checking for
  occupied tiles, retrieving tile values, and shifting all the tiles in a specific direction).

  For example, using a 2d int array allowed me to efficently add two initial tiles to a random row and column on
  my game board. My addRandomTiles() method first loops twice to add two random tiles by generating random row and
  column indexes through the use of Math.random(). Next, the code checks if the randomly selected tile is already
  occupied. Using a 2d array made this easy to check, for I just needed to see if the cell had a non-zero value
  using a while loop. If the randomly selected row and col index is already occupied, the method continues
  to generate new random row and column indexes until it finds an unoccupied tile.

  Additionally, using a 2d array let me easily check if the user has won or lost. If a 2048 tile is on the board,
  the user won, and a winning message is displayed in the JPanel at the bottom of the screen instead of the score.
  If the board is filled and there are no more valid moves left, the user lost, and a losing message is displayed
  in the JPanel at the bottom of the screen instead of the score.

  These are just a few examples of why choosing to use a 2d array to represent my 2048 game board was a
  good design choice.

  2. Collections:

  I utilized a linked list of arrays in order to implement an undo button in my 2048 game. I instantiate and initialize
  a linked list called moves in my Game2048 class and add the initial board to it. Within this same class,
  I created an undo() function that checks if any moves have been made (the linked list length is greater than 1). If
  so, undo() will remove the last int array from the linked list then get the last element of the linked list, which
  removes the user's last move and will display on the board what the board looked like before that last move was made.
  Using a linked list of arrays was a good design choice because it allowed me to easily take the last move off of the
  accumulation of int array boards in the moves linked list.

  3. File I/O:

  I utilized File I/O to save and load the game state.

  In the save() method, I convert the board to a string and write
  it to a file using a BufferedWriter object. To convert the board to a string, I traversed through the 2d array
  representing the game state and appended each element in the array to a StringBuilder object. I then write the
  StringBuilder to the text file 2048.txt using a BufferedWriter. The file path and name are hard coded in.

  In the load() method, I load the game state from the file and convert it back into a 2d array. I used a
  BufferedReader to read in the file back in line by line. I split each line with a comma separator and convert each
  line of strings back to integers, restoring the in 2d array.

  This restored 2d array is the initial game board that the user opted to save. File I/O allowed me to save and
  load the game state with ease!

  4. JUnit Testable Component

  I use JUnit testing to test my model NOT the GUI. This is something I made sure to do after getting feedback on
  my proposal. I initially wanted to test both the model and the GUI. However, after feedback, I realized that
  JUnit testing is mainly for unit testing, meaning that JUnit tests are testing individual methods and classes
  in isolation from the rest of my overall 2048 game. I made sure to include edge cases in my tests. Some examples
  of the edge cases are:
   - if there is a 2048 object on the board but no moves left -> the user should still win.
   - if all the tiles are in the 0 index column and the user presses the left arrow -> a random tile should not appear
   on the board since no changes were made to the board and no tiles were shifted.
   - if the board is full, but you can still merge cells  -> the user does not lose
   ... and more!

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  1. Board.java
  This class represents the game board for my 2048 game. This is the class that handles the game logic, such as
  moving tiles, merging tiles, generating random tiles, keeping track of the score, checking for if the user wins/loses,
  saving/loading game state, resetting the game, etc.

  2. Game2048.java
  This class represents the main model for my 2048 game. This is the class that stores the game state. Within this
  class resides methods for updating the game state based on user input, getting the game state.

  3. Run2048.java
  This class represents the main entry point for my 2048 game. This is the class that sets up the UI, the game board,
  the control panel, and initializes the Board and Game2048 classes in order to manage game logic. This class has
  event listeners for the control panel buttons. Overall, this class can be thought of as the bond that ties both
  game logic and UI together.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  My greatest roadblock when implementing my game was the undo button. For so long, the undo button just would not
  change my board and I could not figure out why. In office hours, I learned that I was having an issue with
  aliasing. In order to fix this issue, I iterated through the 2d array int board, made a copy of it rather than
  simply referencing the array.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  The private state is well encapsulated in my classes. In my Board.java class, all instance variables are private and
  can only be accessed through public methods. This ensures that external classes can not edit the values of these
  private variables and mess up the state of my Board. In my Game2048 class, all instance variables are also private
  and only accessible through public getter and setter methods. This class has a few public static variables that are
  not encapsulated, but they are constants and promote accessibility and readability. If give the chance, I would
  try and implement interfaces for classes. For example, I could create an interface for my Game2048 class that only
  releases methods needed to interact with the game state. This way, my implementation is hidden, and I can change
  internal state without affecting other methods.

