/**
 * TQTree.java
 * A Java class that supports a Binary Tree that plays the game of twenty questions
 * 
 * @author Christine Alvarado
 * @version 1.0
 * @date May 11, 2014
 */
package hw7;
import java.io.*;
import java.util.Scanner;
import java.text.ParseException;
import java.util.LinkedList;

public class TQTree {
  private TQNode root;
  
  /** Inner class that supports a node for a twenty questions tree.
    * You should not need to change this class. */
  class TQNode {
    /*  You SHOULD NOT add any instance variables to this class */
    TQNode yesChild;  // The node's right child
    TQNode noChild;   // The node's left child
    String data;      // A question (for non-leaf nodes) 
                      // or an item (for leaf nodes)
    
    int idx;        // index used for printing
     
    /** Make a new TWNode 
      * @param data The question or answer to store in the node. 
      */
    public TQNode( String data )
    {
      this.data = data;
    }    
    
    /** Setter for the noChild 
      * @param noChild The new left (no) child
      */
    public void setNoChild( TQNode noChild )
    {
      this.noChild = noChild;
    }
    
    
    /** Setter for the yesChild 
      * @param yesChild The new right (yes) child
      */
    public void setYesChild( TQNode yesChild )
    {
      this.yesChild = yesChild;
    }
    
        
    /** Getter for the yesChild 
      * @return The node's yes (right) child
      */
    public TQNode getYesChild()
    {
      return this.yesChild;
    }

    /** Getter for the noChild 
      * @return The node's no (left) child
      */
    public TQNode getNoChild()
    {
      return this.noChild;
    }
    
    /** Getter for the data
      * @return The data stored in this node
      */
    public String getData()
    {
      return this.data;
    }
    
    /** Setter for the index
      * @param idx index of this for printing 
      */
    public void setIndex(int idx) {
      this.idx = idx;
    }
    
    /** get the index
      * @return idx index of this for printing 
      */
    public int getIndex() {
      return this.idx;
    }
  }  // End TQNode

  /** Builds a new TQTree by reading from a file 
    * @param filename The file containing the tree
    * @throws FileNotFoundException if the file cannot be found or read.
    * */
  public TQTree( String filename )
  {
    File f = new File( filename );
    LineNumberReader reader;
    try {
      reader = new LineNumberReader( new FileReader( f ));
    } catch ( FileNotFoundException e ) {
      System.err.println( "Error opening file " + filename );
      System.err.println( "Building default Question Tree." );
      buildDefaultTree();
      return;
    }
    
    buildTreeFromFile( reader );
    try {
      reader.close();
    } catch ( IOException e ) {
      System.err.println( "An error occured while closing file " + filename );
    }
    
  }
  
  // Build the tree from the file that reader is reading from.
  private void buildTreeFromFile( LineNumberReader reader )
  {
    String line = null;
    try {
      line = reader.readLine();
    }
    catch ( IOException e ) {
      errorBuildTree( "File contains no tree." );
      return;
    }
    
    if ( line == null ) {
      errorBuildTree( "File contains no tree." );
      return;
    }
    String[] lineSplit = line.split( ":", 2 );
    if ( lineSplit.length < 2 ) {
      errorBuildTree( "Incorrect file format: line 1." );
      return;
    }
    String qOrA = lineSplit[0];
    String data = lineSplit[1];
      
    if ( !qOrA.equals( "Q" ) ) {
      errorBuildTree( "Incorrect file format: line 1." );
      return;
    }
    root = new TQNode( data );
    try {
      root.setNoChild( buildSubtree( reader ) );
      root.setYesChild( buildSubtree( reader ) );
    } catch ( ParseException e ) {
      errorBuildTree( e.getMessage() + ": line " +  + e.getErrorOffset() );
    }
  } 
  
  // Print an error message and then build the default tree
  private void errorBuildTree( String message )
  {
    System.err.println( message );
    System.err.println( "Buidling default Question Tree" );
    buildDefaultTree();
  }
    
  /** Recursive method to build a TQTree by reading from a file.
    * @param reader A LineNumberReader that reads from the file
    * @return The TQNode at the root of the created tree
    * @throws ParseException If the file format is incorrect
    */
  private TQNode buildSubtree( LineNumberReader reader ) throws ParseException 
  {
    
    String line;
    try {
      line = reader.readLine();
    }
    catch ( IOException e ) {
      throw new ParseException( "Error reading tree from file: " + e.getMessage(),
                               reader.getLineNumber() );
    }
    
    if ( line == null ) {
      // We should never be calling this method if we don't have any more input
      throw new ParseException( "End of file reached unexpectedly", reader.getLineNumber() );
    }
    
    String[] lineSplit = line.split( ":", 2 );
    String qOrA = lineSplit[0];
    String data = lineSplit[1];
    
    TQNode subRoot = new TQNode( data );
    if ( qOrA.equals( "Q" ) ) {
      subRoot.setNoChild( buildSubtree( reader ) );
      subRoot.setYesChild( buildSubtree( reader ) );
    }    
    return subRoot;
  }
  
  private void buildDefaultTree()
  {
    root = new TQNode( "Is it bigger than a breadbox?" );
    root.setNoChild( new TQNode( "spam" ) );
    root.setYesChild( new TQNode( "a computer scientist" ) );
  }
  
  /** Builds a starter TQ tree with 1 question and 2 answers */
  public TQTree()
  {
    buildDefaultTree();
  }
  
  /** Play one round of the game of Twenty Questions using this game tree 
    * Augments the tree if the computer does not guess the right answer
    */ 
  public void play()
  {
    Scanner input = new Scanner( System.in );
    TQNode current = root;
    TQNode last = null;
    String response;
    while ( !(( current.getNoChild() == null ) && 
              (current.getYesChild() == null )) ) {
      last = current;
      System.out.println( current.getData() );
      response = input.next();
      if ( response.trim().equalsIgnoreCase( "y" ) || 
          response.trim().equalsIgnoreCase( "yes" ) ) {
        current = current.getYesChild();
      }
      else {
        current = current.getNoChild();
      }
    }
    // Make the guess
    System.out.println( "Is it " + current.getData() + "?");
    String guess = input.next();
    if ( guess.trim().equalsIgnoreCase( "y" ) || guess.trim().equalsIgnoreCase( "yes" ) ) {
      System.out.println( "Got it!" );
    }
    else {
      addItem( current, last );
    }
  }
  
  // Get a new item from the user and add it as a child of last. 
  // Current is the other child of last.
  private void addItem( TQNode current, TQNode last ) {
    Scanner s = new Scanner( System.in );
    System.out.println( "OK, what was it?" );
    String thing = s.nextLine();
    System.out.print( "Give me a question that would distinguish " + thing );
    System.out.println( " from " + current.getData() );
    String question = s.nextLine();
    System.out.print( "And would the answer to the question for " + thing ); 
    System.out.println( " be yes or no?" );
    String yOrN = s.nextLine();
    
    TQNode newThing = new TQNode( thing );
    TQNode newQ = new TQNode( question );
    // Hook up newThing and newQ
    if ( yOrN.trim().equalsIgnoreCase( "y" ) || yOrN.trim().equalsIgnoreCase( "yes" ) ) {
      newQ.setYesChild( newThing );
      newQ.setNoChild( current );
    }
    else {
      newQ.setYesChild( current );
      newQ.setNoChild( newThing );
    }
    // Now hook up the new subtree to the rest of the tree
    if ( last.getNoChild() == current ) {
      last.setNoChild( newQ );
    }
    else {
      last.setYesChild( newQ );
    }
  }
    
  /** Save this Twenty Questions tree to the file with the given name
    * @param filename The name of the file to save to
    * @throws FileNotFoundException If the file cannot be used to save the tree
    */
  public void save( String filename ) throws FileNotFoundException
  {
    File f = new File( filename );
    PrintWriter writer = new PrintWriter( f );
    saveTree( writer, root );
    writer.close();
  }
  
  /** Recursive helper method to do the preorder traversal of the tree.
    * @param The print writer to write to
    * @param The current root from which to write
    * */
  private void saveTree( PrintWriter writer, TQNode currentRoot )
  {
    if ( currentRoot == null ) {
      return;
    }
    String toWrite = "";
    if ( currentRoot.getNoChild() == null && currentRoot.getYesChild() == null ) {
      toWrite = "A:"+currentRoot.getData();
    }
    else {
      toWrite = "Q:"+currentRoot.getData();
    }
    writer.println( toWrite );
    saveTree( writer, currentRoot.getNoChild() );
    saveTree( writer, currentRoot.getYesChild() );
  }
  
  public void print()
  {
    PrintWriter writer = new PrintWriter( System.out );
    printTree( writer, root );
    writer.flush();
  }
 

  
  /** method for breadth-first traversal of the tree.
    * @param The print writer to write to (usually stdout)
    * @param The current root from which to write
    * */
  private void printTree( PrintWriter writer, TQNode root )
  {
    LinkedList<TQNode> q = new LinkedList<TQNode>();
    TQNode tmp;
    q.add(0,root);
    int idx = 0;
    while (q.size() > 0)
    {
      tmp = q.remove(q.size()-1);
      tmp.setIndex(idx); idx++;
      if (tmp.getNoChild() != null) q.add(0,tmp.getNoChild());
      if (tmp.getYesChild() != null) q.add(0,tmp.getYesChild());
    }
    q.add(0,root);
    while (q.size() > 0 )
    {
      tmp = q.remove(q.size()-1);
      printIt(writer, tmp);
      if (tmp.getNoChild() != null) q.add(0,tmp.getNoChild());
      if (tmp.getYesChild() != null) q.add(0,tmp.getYesChild());
    }
    
  }
  private void printIt( PrintWriter writer, TQNode node )
  {
    String left, right;
    if (node.getNoChild() == null)
      left = "(null)";
    else
      left = "".format("(%d)",node.getNoChild().getIndex());
    
    if (node.getYesChild() == null)
      right = "(null)";
    else
      right = "".format("(%d)",node.getYesChild().getIndex());
    
    writer.println( "".format("%d:  '%s'  no:%s  yes:%s",node.idx, node.getData(), left,right ));
  }
  
}
