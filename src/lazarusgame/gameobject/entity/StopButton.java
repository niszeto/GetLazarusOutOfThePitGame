package lazarusgame.gameobject.entity;

import lazarusgame.Sprite;
import lazarusgame.gameobject.entity.movableentity.*;

public class StopButton extends Entity {
  boolean isPressed;

  public StopButton( int xCoordinates, int yCoordinates, Sprite gameObjectSprite ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.isPressed = false;
  }

  public boolean getIsPressed( ) {
    return this.isPressed;
  }

  @Override
  public void collidingEntities( Entity entityObject ) {

  }

  @Override
  public void collidingEntities( StopButton stopButton ) {

  }

  @Override
  public void collidingEntities( Wall wall ) {

  }

  @Override
  public void collidingEntities( Lazarus lazarus ) {
    this.isPressed = true;
    lazarus.setCoordinates( this.getXCoordinate(), this.getYCoordinate() );
    lazarus.setIsShowing( true );
  }

  @Override
  public void collidingEntities( Box box ) {

  }
}
