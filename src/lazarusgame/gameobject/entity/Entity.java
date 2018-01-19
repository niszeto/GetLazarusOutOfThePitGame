package lazarusgame.gameobject.entity;

import lazarusgame.Sprite;
import lazarusgame.gameobject.*;
import lazarusgame.gameobject.entity.movableentity.*;

import java.awt.*;

public abstract class Entity extends GameObject {

  private Rectangle hitBox;

  public Entity( int xCoordinates, int yCoordinates, Sprite gameObjectSprite ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.hitBox = new Rectangle( xCoordinates, yCoordinates, this.getWidth(), this.getLength() );
  }


  public Rectangle getHitBox( ) {
    return this.hitBox;
  }

  public void updateHitBox( ) {
    this.hitBox.setLocation( getXCoordinate(), getYCoordinate() );
  }

  public abstract void collidingEntities( Entity entityObject );

  public abstract void collidingEntities( StopButton stopButton );

  public abstract void collidingEntities( Wall wall );

  public abstract void collidingEntities( Lazarus lazarus );

  public abstract void collidingEntities( Box box );
}
