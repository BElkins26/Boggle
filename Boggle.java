import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Boggle
{
    public static TreeSet<String> dictionary;
    public static TreeSet<String> words;
    static String[][] matrix;
    JFrame window;
    Container content;
    JTextArea results;
    JLabel[] board;
    JButton button;
    
    public Boggle(String[][] matrix)
    {
      window = new JFrame( "Boggle");
      ButtonListener listener = new ButtonListener();
      content = window.getContentPane();
      board = new JLabel[matrix.length*matrix.length];
      content.setLayout(new GridLayout(3,1));
      results = new JTextArea();
      JPanel topPanel = new JPanel();
      JPanel midButton = new JPanel();
      JPanel bottomText = new JPanel();
      topPanel.setLayout(new GridLayout(matrix.length, matrix.length));
      midButton.setLayout(new FlowLayout());
      bottomText.setLayout(new FlowLayout());
      results.setLineWrap(true);
      results.setWrapStyleWord(true);
      results.setRows(10);
      results.setColumns(100);
      button = new JButton("Solve Board");
      button.setFont(new Font("verdana", Font.BOLD, 20 ));
      button.addActionListener(listener);
      for(int i = 0; i < matrix.length; i++) {
        for(int c = 0; c < matrix.length; c++)
        {
        board[i] = new JLabel();
        board[i].setText(matrix[i][c]);
        topPanel.add(board[i]);
        }
      }
      midButton.add(button);
      bottomText.add(results);
      content.add(topPanel);
      content.add(midButton);
      content.add(bottomText);
      window.setSize( 640,480);
      window.setVisible( true );
    }
    class ButtonListener implements ActionListener
    {
      public void actionPerformed(ActionEvent e)
      {
        results.setText("");
        String word = "";
        for(int r = 0; r < matrix.length; r++)
      {
        for(int c = 0; c < matrix[r].length; c++)
          findWords(matrix, r, c, word);
      }
          for(String i: words)
          {
             results.setText(results.getText()+i+" ");
          }
    }
        
    }
    public static void main(String[] args) throws Exception
 {
      BufferedReader reader = new BufferedReader(new FileReader("dictionary.txt"));
      matrix = loadBoggle( args[0]);
      dictionary = new TreeSet<String>();
      words = new TreeSet<String>();
      new Boggle(matrix);
      while(reader.ready())
      {
        dictionary.add(reader.readLine());
      }
    }
      
    
      private static void findWords(String[][] matrix, int r, int c, String word)
      {
        word = word+matrix[r][c];
        if(dictionary.contains(word) && word.length() > 2)
          words.add(word);
        if(dictionary.ceiling(word).equals(null) || !dictionary.ceiling(word).startsWith(word))
              return;
  //NORTH
  if( r > 0 && matrix[r-1][c].equals(matrix[r-1][c].toLowerCase()) )
  { 
   matrix[r][c] =  matrix[r][c].toUpperCase();
   findWords( matrix, r-1, c, word);
   matrix[r][c] =  matrix[r][c].toLowerCase();
  }
  //NorthEast
  if(r > 0 && c < (matrix[r].length-1 ) && matrix[r-1][c+1].equals(matrix[r-1][c+1].toLowerCase()))
  {
   matrix[r][c] =  matrix[r][c].toUpperCase();
   findWords( matrix,r-1, c+1, word);
   matrix[r][c] =  matrix[r][c].toLowerCase();
  }
  //East
  if(c < matrix.length-1 && matrix[r][c+1].equals(matrix[r][c+1].toLowerCase()))
  {
   matrix[r][c] =  matrix[r][c].toUpperCase();
   findWords(matrix, r, c+1, word);
   matrix[r][c] =  matrix[r][c].toLowerCase();
  }
  
  //SouthEast
  if(r < matrix.length-1 && c < matrix.length-1 && matrix[r+1][c+1].equals(matrix[r+1][c+1].toLowerCase()))
  {
   matrix[r][c] =  matrix[r][c].toUpperCase();
   findWords(matrix, r+1, c+1, word);
   matrix[r][c] =  matrix[r][c].toLowerCase();
  }
  
  //South
  if(r < (matrix.length - 1) && matrix[r+1][c].equals(matrix[r+1][c].toLowerCase()))
  {
   matrix[r][c] = matrix[r][c].toUpperCase();
   findWords(matrix, r+1, c, word);
   matrix[r][c] =  matrix[r][c].toLowerCase();
  }
  
  //SouthWest
  if(r < (matrix.length - 1) && c > 0 && matrix[r+1][c-1].equals(matrix[r+1][c-1].toLowerCase()))
  {
   matrix[r][c] =  matrix[r][c].toUpperCase();
   findWords(matrix, r+1, c-1, word);
   matrix[r][c] =  matrix[r][c].toLowerCase();
  }
  
  //West
  if(c > 0 && matrix[r][c-1].equals(matrix[r][c-1].toLowerCase()))
  {
   matrix[r][c] = matrix[r][c].toUpperCase();
   findWords(matrix, r, c-1, word);
   matrix[r][c] =  matrix[r][c].toLowerCase();
  }
  
  //NorthWest
  if(r > 0 && c > 0 && matrix[r-1][c-1].equals(matrix[r-1][c-1].toLowerCase()))
  {
   matrix[r][c] =  matrix[r][c].toUpperCase();
   findWords(matrix, r-1, c-1, word);
   matrix[r][c] =  matrix[r][c].toLowerCase();
  }
 }
    private static String[][] loadBoggle( String infileName) throws Exception
 {
  Scanner infile = new Scanner( new File(infileName) );
  int rows=infile.nextInt();// ASSUME A SQUARE GRID
  String[][] matrix = new String[rows][rows];
  for(int r = 0; r < rows ; r++)
   for(int c = 0; c < rows; c++)
    matrix[r][c] = infile.next();

  infile.close();
  return matrix;
    }
}