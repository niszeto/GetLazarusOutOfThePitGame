package lazarusgame.gameobject.entity.movableentity;

import lazarusgame.Sprite;
import lazarusgame.gameobject.IDestroy;
import lazarusgame.gameobject.entity.*;

import java.util.Observable;
import java.util.Observer;

public class Lazarus extends MovableEntity implements Observer, IDestroy {
  private boolean isGettingSquashed;
  private boolean isSquashed;

  public Lazarus( int xCoordinates, int yCoordinates, Sprite gameObjectSprite ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.isGettingSquashed = false;
    this.isSquashed = false;
  }

  public Lazarus( int xCoordinates, int yCoordinates, Sprite gameObjectSprite, int speed ) {
    super( xCoordinates, yCoordinates, gameObjectSprite, speed );
    this.isGettingSquashed = false;
    this.isSquashed = false;
  }

  public boolean getIsSquashed( ) {
    return this.isSquashed;
  }

  public boolean getIsGettingSquashed( ) {
    return this.isGettingSquashed;
  }

  public void setIsSquashed( Boolean isSquashed ) {
    this.isSquashed = isSquashed;
  }

  public void setIsGettingSquashed( Boolean isGettingSquashed ) {
    this.isGettingSquashed = isGettingSquashed;
  }

  public void moveLeft( ) {
    this.setCoordinates( this.getXCoordinate() - this.getSpeed(), this.getYCoordinate() );
    updateHitBox();
  }

  public void moveRight( ) {
    this.setCoordinates( this.getXCoordinate() + this.getSpeed(), this.getYCoordinate() );
    updateHitBox();
  }

  public void jumpUpLeft( ) {
    this.setCoordinates( this.getXCoordinate() - this.getSpeed(), this.getYCoordinate() - this.getSpeed() );
    updateHitBox();
  }

  public void jumpUpRight( ) {
    this.setCoordinates( this.getXCoordinate() + this.getSpeed(), this.getYCoordinate() - this.getSpeed() );
    updateHitBox();
  }

  public void jumpDownLeft( ) {
    this.setCoordinates( this.getXCoordinate() - this.getSpeed(), this.getYCoordinate() + this.getSpeed() );
    updateHitBox();
  }

  public void jumpDownRight( ) {
    this.setCoordinates( this.getXCoordinate() + this.getSpeed(), this.getYCoordinate() + this.getSpeed() );
    updateHitBox();
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

  }

  @Override
  public void collidingEntities( Box box ) {
    this.destroy();
  }

  @Override
  public void destroy( ) {
    this.isGettingSquashed = true;
    this.setIsShowing( false );
  }

  @Override
  public void update( Observable o, Object arg ) {

  }
}
