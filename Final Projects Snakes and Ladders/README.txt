=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D-Arrays. The board game will be represented by a 2d array(10x10). The board game represents the location of
  each number in the board game. When we want to draw snakes, draw ladders, update player position in the GUI,
  we refer to the 2D array containing numbers to finding the appropriate row and column to put the object

  2. Collections- No two snakes or ladders(game objects) can share a same point(for visual clarity like in a real snake
  and ladder game). Two objects are considered equal if they share a point(game objects go from one point to another).
  A TreeSet was meant to keep track of objects(snakes and ladders) and prevents "equal" object from joining.
  Each object is specified to be a snake or ladder.


  3. FileIO. The user can input a set of two coordinates of where they want the snake or ladder for each
   snake or ladder(the order in which they enter does not matter as snakes go from  bottom to top and ladders
   go from top to bottom). The FileWriter writes these values to a file. The FileReaders reads that from the file
   and constructs the TicToeObject aas needed.

  4. JUnit Test. We test all the methods of the TicToe Class seperate from GUI(DiceRolling, creating Objects,
  hitting snakes, hitting ladders, checking winners, going over 100). Dice Rolls are not randomized to ensure non-random
  testing.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
TicTacToe: Creates SnakesAndLadder Game. Can be run without using GUI
GameBoard: Creates GUI using numbers from two-d array.
User: Creates data for the two players- Player 1 and Player 2.
BoardObject: Creates Snakes And Ladders(objects) for the board game and stores data for each of them.




- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  No, I wrote out my design prior to coding and everything went smoothly.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  Yes, everything is seperated and everything is encapsulated(can only be accessed by getter and setter methods).
  I would maybe refactor by adding an option add multiple player and applying this method to more complicated games like
  Ludo.



========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

All images were used from PNGTree.com