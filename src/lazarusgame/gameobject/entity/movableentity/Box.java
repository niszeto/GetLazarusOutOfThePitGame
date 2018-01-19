package lazarusgame.gameobject.entity.movableentity;

import lazarusgame.Sprite;
import lazarusgame.gameobject.IDestroy;
import lazarusgame.gameobject.entity.*;

import java.util.Observable;
import java.util.Observer;

public class Box extends MovableEntity implements Observer, IDestroy {
  private int boxWeight;
  private boolean isDropping;
  private boolean isStacked;

  public Box( int xCoordinates, int yCoordinates, Sprite gameObjectSprite ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.boxWeight = 0;
    this.isStacked = false;
    this.isDropping = false;
  }

  public Box( int xCoordinates, int yCoordinates, Sprite gameObjectSprite, int weight ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.boxWeight = weight;
    this.isStacked = false;
    this.isDropping = false;
  }

  public void setBoxWeight( int boxWeight ) {
    this.boxWeight = boxWeight;
  }

  public void setIsStacked( Boolean isStacked ) {
    this.isStacked = isStacked;
  }

  public void setIsDropping( Boolean isDropping ) {
    this.isDropping = isDropping;
  }


  public boolean getIsDropping( ) {
    return this.isDropping;
  }

  public boolean getIsStacked( ) {
    return this.isStacked;
  }

  public int getBoxWeight( ) {
    return this.boxWeight;
  }


  public void moveDown( ) {
    this.setCoordinates( this.getXCoordinate(), this.getYCoordinate() + this.getSpeed() );
    this.updateHitBox();
  }

  @Override
  public void collidingEntities( Entity entityObject ) {

  }

  @Override
  public void collidingEntities( StopButton stopButton ) {

  }

  @Override
  public void collidingEntities( Wall wall ) {
    this.setCoordinates( wall.getXCoordinate(), wall.getYCoordinate() - wall.getLength() );
    this.updateHitBox();
    this.setIsStacked( true );
    this.setIsDropping( false );
  }

  @Override
  public void collidingEntities( Lazarus lazarus ) {

  }

  @Override
  public void collidingEntities( Box box ) {
    if ( this.boxWeight >= box.getBoxWeight() ) {
      box.setCoordinates( this.getXCoordinate(), this.getYCoordinate() - this.getLength() );
      box.updateHitBox();
      box.setIsStacked( true );
      box.setIsDropping( false );
    } else if ( this.boxWeight < box.getBoxWeight() ) {
      this.destroy();
    }

  }

  @Override
  public void destroy( ) {
    this.setIsShowing( false );
  }

  @Override
  public void update( Observable o, Object arg ) {
    if ( this.getIsShowing() && !this.isStacked ) {
      this.moveDown();
    }
  }
}
