package lazarusgame.gameobject;

import lazarusgame.Sprite;

import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class Animation extends GameObject implements Observer {
  int lastImageIndex;
  int currentImageIndex;
  boolean isOneRotation;

  public Animation( int xCoordinates, int yCoordinates, Sprite gameObjectSprite ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.currentImageIndex = 0;
    this.lastImageIndex = gameObjectSprite.getNumberOfFramesIndex();
    this.isOneRotation = false;
  }

  public BufferedImage animate( ) {
    this.currentImageIndex++;
    if ( this.currentImageIndex >= this.lastImageIndex ) {
      this.isOneRotation = true;
      this.currentImageIndex = 0;
    }
    return getGameObjectSprite().getSpecificSpriteFrame( currentImageIndex );
  }

  public int getLastImageIndex( ) {
    return this.lastImageIndex;
  }

  public int getCurrentImageIndex( ) {
    return this.currentImageIndex;
  }

  public boolean getIsOneRotation( ) {
    return this.isOneRotation;
  }

  @Override
  public void update( Observable o, Object arg ) {
    if ( getIsShowing() ) {
      setCurrentGameObjectImage( animate() );
      if ( this.isOneRotation ) {
        this.setIsShowing( false );
        this.isOneRotation = false;
      }
    }
  }
}