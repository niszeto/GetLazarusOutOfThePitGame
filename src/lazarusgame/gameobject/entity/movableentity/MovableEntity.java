package lazarusgame.gameobject.entity.movableentity;

import lazarusgame.Sprite;
import lazarusgame.gameobject.entity.*;

public abstract class MovableEntity extends Entity {
  private int speed;


  public MovableEntity( int xCoordinates, int yCoordinates, Sprite gameObjectSprite ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.speed = 5;

  }

  public MovableEntity( int xCoordinates, int yCoordinates, Sprite gameObjectSprite, int speed ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.speed = speed;

  }

  public int getSpeed( ) {
    return this.speed;
  }

  public void setSpeed( int newSpeed ) {
    this.speed = newSpeed;
  }

}
