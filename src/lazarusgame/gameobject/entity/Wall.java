package lazarusgame.gameobject.entity;

import lazarusgame.Sprite;
import lazarusgame.gameobject.entity.movableentity.*;

public class Wall extends Entity {
  public Wall( int xCoordinates, int yCoordinates, Sprite gameObjectSprite ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
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
    box.setCoordinates( this.getXCoordinate(), this.getYCoordinate() - this.getLength() );
    box.updateHitBox();
    box.setIsStacked( true );
    box.setIsDropping( false );
  }
}
