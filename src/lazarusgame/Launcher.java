package lazarusgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Launcher {
  public static void main( String[] args ) {
    LazarusGameWorld lazarusGame = LazarusGameWorld.getInstance();
    JFrame gameDisplayWindow = new JFrame( "Get Lazarus out of the Pit" );
    gameDisplayWindow.add( lazarusGame );

    gameDisplayWindow.addKeyListener( lazarusGame.getPlayerOneController() );

    gameDisplayWindow.setFocusable( true );
    gameDisplayWindow.requestFocusInWindow();

    gameDisplayWindow.pack();
    gameDisplayWindow.addWindowListener( new WindowAdapter() {
      public void windowGainedFocus( WindowEvent e ) {
        lazarusGame.requestFocusInWindow();
      }
    } );
    gameDisplayWindow.setSize( new Dimension( Assets.WINDOWWIDTH, Assets.WINDOWHEIGHT ) );
    gameDisplayWindow.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    gameDisplayWindow.setVisible( true );
    gameDisplayWindow.setFocusable( true );
    gameDisplayWindow.setResizable( false );
    gameDisplayWindow.setLocationRelativeTo( null );
    lazarusGame.getThread().start();
  }
}
