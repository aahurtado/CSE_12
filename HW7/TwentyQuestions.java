/** A game of 20 questions.
  * @author Christine Alvarado
  * @date 11 May 2014
  * */
package hw7;
import java.util.Scanner;
import java.io.*;

public class TwentyQuestions
{
  public static void saveGame( TQTree theGame )
  {
    System.out.println( "Please enter a filename to save your game" );
    Scanner input = new Scanner( System.in );
    String response = input.next();
    boolean gameSaved = false;
    while ( !gameSaved ) {
      try {
        theGame.save( response );
        gameSaved = true;
      }
      catch ( FileNotFoundException e ) {
        System.out.println( "Could not open file " + response );
        System.out.println( "Please enter a new filename or a to abort " );
        response = input.next();
        if ( response.equals( "a" ) ) {
          System.out.println( "OK, your game will not be saved." );
          return;
        }
      }
      System.out.println( "Game saved." );
    }
  }
    
  // Plays the game of twenty questions
  public static void main( String[] args )
  {
    TQTree gameTree;
    Scanner input = new Scanner( System.in );
    
    if ( args.length < 1 ) {
      gameTree = new TQTree();
    }
    else {
      gameTree = new TQTree( args[0] );
    }
    
    String response = "y";
    while ( response.equalsIgnoreCase( "y" ) || response.equalsIgnoreCase( "yes" ) )
    {
      gameTree.play();
      System.out.println( "Would you like to play again? " );
      response = input.next();
    }
    
    System.out.println( "Your final game tree was: " );
    gameTree.print();
    
    System.out.println( "Would you like to save your game tree?" );
    response = input.next();
    if ( response.equalsIgnoreCase( "y" ) || 
        response.equalsIgnoreCase( "yes" ) ) {
      saveGame( gameTree );
    }
      
    System.out.println( "Goodbye!" );
  }
  
}