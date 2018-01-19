package lazarusgame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

public class KeyboardControl extends Observable implements KeyListener {
  private Assets.PlayerControls[] playerControls;
  private boolean[] keyCodes;
  private boolean isLeft;
  private boolean isRight;


  public KeyboardControl( Assets.PlayerControls[] playerControls ) {
    this.playerControls = playerControls;
    this.keyCodes = new boolean[256];
  }

  @Override
  public void keyTyped( KeyEvent e ) {

  }

  @Override
  public void keyPressed( KeyEvent e ) {
    keyCodes[e.getKeyCode()] = true;
    this.isLeft = keyCodes[playerControls[0].getKEYCODE()];
    this.isRight = keyCodes[playerControls[1].getKEYCODE()];

    setChanged();
    notifyObservers();
  }

  @Override
  public void keyReleased( KeyEvent e ) {
    keyCodes[e.getKeyCode()] = false;
    this.isLeft = keyCodes[playerControls[0].getKEYCODE()];
    this.isRight = keyCodes[playerControls[1].getKEYCODE()];

    setChanged();
    notifyObservers();
  }

  public boolean isLeft( ) {
    return this.isLeft;
  }

  public boolean isRight( ) {
    return this.isRight;
  }

  public void setIsLeft(boolean isLeft){
    this.isLeft = isLeft;
  }

  public void setIsRight(boolean isRight){
    this.isRight = isRight;
  }

}