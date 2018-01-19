package lazarusgame.gameobject;

import java.awt.*;
import java.awt.image.BufferedImage;

import lazarusgame.Sprite;

public abstract class GameObject {
  private Point coordinates;
  private int length;
  private int width;
  private boolean isShowing;
  private Sprite gameObjectSprite;
  private BufferedImage currentGameObjectImage;

  public GameObject( int xCoordinates, int yCoordinates, Sprite gameObjectSprite ) {

    this.isShowing = false;
    this.coordinates = new Point( xCoordinates, yCoordinates );
    this.gameObjectSprite = gameObjectSprite;
    this.currentGameObjectImage = gameObjectSprite.getFirstSpriteFrame();
    this.length = gameObjectSprite.getFirstSpriteFrame().getHeight();
    this.width = gameObjectSprite.getFirstSpriteFrame().getWidth();

  }

  public int getLength( ) {
    return this.length;
  }

  public int getWidth( ) {
    return this.width;
  }


  public Point getCoordinates( ) {
    return this.coordinates;
  }

  public int getXCoordinate( ) {
    return ( int ) this.coordinates.getX();
  }

  public int getYCoordinate( ) {
    return ( int ) this.coordinates.getY();
  }

  public BufferedImage getCurrentGameObjectImage( ) {
    return this.currentGameObjectImage;
  }

  public Sprite getGameObjectSprite( ) {
    return this.gameObjectSprite;
  }

  public boolean getIsShowing( ) {
    return this.isShowing;
  }

  public void setGameObjectSprite( Sprite gameObjectSprite ) {
    this.gameObjectSprite = gameObjectSprite;
  }

  public void setCurrentGameObjectImage( BufferedImage currentImage ) {
    this.currentGameObjectImage = currentImage;
  }

  public void setCoordinates( int xCoordinate, int yCoordinate ) {
    this.coordinates.setLocation( xCoordinate, yCoordinate );
  }

  public void setWidth( int width ) {
    this.width = width;
  }

  public void setLength( int length ) {
    this.length = length;
  }

  public void setIsShowing( boolean isShowing ) {
    this.isShowing = isShowing;
  }

}
