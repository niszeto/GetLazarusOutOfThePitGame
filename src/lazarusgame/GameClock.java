package lazarusgame;

import java.util.Observable;

import static java.lang.Thread.sleep;

public class GameClock extends Observable implements Runnable {

  @Override
  public void run( ) {
    while ( LazarusGameWorld.getInstance().isGameRunning() ) {
      try {
        sleep( 25 );
        setChanged();
        notifyObservers();
      } catch ( InterruptedException e ) {
        e.printStackTrace();
      }
    }
  }

}
