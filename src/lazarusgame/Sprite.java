package lazarusgame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {
  private int spriteSize;
  private String spriteFileName;
  private BufferedImage[] spriteFrames;

  public Sprite( String spriteFileName, int spriteSize ) throws IOException {
    this.spriteFileName = spriteFileName;
    this.spriteSize = spriteSize;

    loadSpriteFrames();
  }

  public Sprite( BufferedImage[] spriteFrames, int spriteSize ) {
    this.spriteFrames = spriteFrames;
    this.spriteSize = spriteSize;
  }

  public int getNumberOfFramesIndex( ) {
    return this.spriteFrames.length;
  }

  public BufferedImage getFirstSpriteFrame( ) {
    return this.spriteFrames[0];
  }

  public BufferedImage getSpecificSpriteFrame( int index ) {
    if ( index >= this.spriteFrames.length ) {
      return this.spriteFrames[0];
    } else {
      return this.spriteFrames[index];
    }
  }

  public BufferedImage[] getSpriteFrames( ) {
    return this.spriteFrames;
  }

  public void setSpriteFrames( BufferedImage[] spriteFrames ) {
    this.spriteFrames = spriteFrames;
  }

  private void loadSpriteFrames( ) throws IOException {
    BufferedImage spriteSheet = null;
    try {
      spriteSheet = ImageIO.read( new File( spriteFileName ) );
    } catch ( IOException e ) {
      e.printStackTrace();
    }
    this.spriteFrames = new BufferedImage[spriteSheet.getWidth() / spriteSize];

    for ( int spriteIndex = 0; spriteIndex < this.spriteFrames.length; spriteIndex++ ) {
      this.spriteFrames[spriteIndex] = spriteSheet.getSubimage(
          spriteIndex * this.spriteSize, 0, this.spriteSize, this.spriteSize
      );
    }

  }
}
