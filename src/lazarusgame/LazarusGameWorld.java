package lazarusgame;

import lazarusgame.gameobject.*;
import lazarusgame.gameobject.entity.*;
import lazarusgame.gameobject.entity.movableentity.Box;
import lazarusgame.gameobject.entity.movableentity.Lazarus;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class LazarusGameWorld extends JPanel implements Observer {
  private static LazarusGameWorld lazarusGameWorld;
  private Thread thread;

  private int[][] levelArray;
  private boolean isRunning;
  private GameClock clock = new GameClock();

  private Random randomNumberGenerator;
  private int playerControlIdleCounter;

  private ArrayList<Deque<Entity>> mapRows;

  private KeyboardControl playerOneController;
  private Lazarus lazarus;
  private int lazarusCurrentRowIndex;
  private Animation lazarusAfraidAnimation;
  private Animation lazarusMoveLeftAnimation;
  private Animation lazarusMoveRightAnimation;
  private Animation lazarusJumpUpLeftAnimation;
  private Animation lazarusJumpUpRightAnimation;
  private Animation lazarusSquishedAnimation;
  private int lazarusSquishedFrame;

  private Box nextBox;
  private Box droppingBox;

  private StopButton leftStopButton;
  private StopButton rightStopButton;

  private BufferedImage mapImage;
  private Graphics2D mapGraphics;


  private LazarusGameWorld( ) {
    super();

    this.loadFiles();

    this.setUpGameMapLists();

    this.randomNumberGenerator = new Random();
    this.playerControlIdleCounter = 0;
    this.lazarusSquishedFrame = 0;

    this.createGameObjects();

    this.setUpGameClock();
  }

  private void loadFiles( ) {
    Assets.initialize();
    this.levelArray = Assets.levelFileArray;
  }

  private void setUpGameClock( ) {
    this.isRunning = true;
    this.clock.addObserver( this );
    this.thread = new Thread( this.clock );
  }

  private void setUpGameMapLists( ) {
    this.mapRows = new ArrayList<Deque<Entity>>();
    for ( int numberOfColumns = 0; numberOfColumns < 16; numberOfColumns++ ) {
      this.mapRows.add( new LinkedList<Entity>() );
    }
  }

  private void createGameObjects( ) {
    Wall wall;
    int yCoordinateOffset;
    for ( int mapWidthIndex = 0; mapWidthIndex < this.levelArray[0].length; mapWidthIndex++ ) {
      for ( int mapLengthIndex = 0; mapLengthIndex < this.levelArray.length; mapLengthIndex++ ) {
        yCoordinateOffset = 11 - mapLengthIndex;
        if ( this.levelArray[mapLengthIndex][mapWidthIndex] == 1 ) {
          this.nextBox = new Box( mapWidthIndex * 40, yCoordinateOffset * 40, Assets.cardboardBox, 0 );
          this.mapRows.get( mapWidthIndex ).push( this.nextBox );

        } else if ( levelArray[mapLengthIndex][mapWidthIndex] == 2 ) {
          wall = new Wall( mapWidthIndex * 40, yCoordinateOffset * 40, Assets.wall );
          wall.setIsShowing( true );
          this.mapRows.get( mapWidthIndex ).push( wall );

        } else if ( this.levelArray[mapLengthIndex][mapWidthIndex] == 3 ) {
          this.lazarus = new Lazarus( mapWidthIndex * 40, yCoordinateOffset * 40, Assets.lazarusStanding, 40 );
          this.lazarus.setIsShowing( true );
          this.mapRows.get( mapWidthIndex ).push( this.lazarus );
          this.lazarusCurrentRowIndex = mapWidthIndex;

        } else if ( this.levelArray[mapLengthIndex][mapWidthIndex] == 4 ) {
          this.leftStopButton = new StopButton( mapWidthIndex * 40, yCoordinateOffset * 40, Assets.stopButton );
          this.leftStopButton.setIsShowing( true );
          this.mapRows.get( mapWidthIndex ).push( this.leftStopButton );

        } else if ( this.levelArray[mapLengthIndex][mapWidthIndex] == 5 ) {
          this.rightStopButton = new StopButton( mapWidthIndex * 40, yCoordinateOffset * 40, Assets.stopButton );
          this.rightStopButton.setIsShowing( true );
          this.mapRows.get( mapWidthIndex ).push( this.rightStopButton );
        }
      }
    }
    this.mapImage = new BufferedImage( Assets.WINDOWWIDTH, Assets.WINDOWHEIGHT, BufferedImage.TYPE_INT_ARGB );
    createGameController();
    createAnimationObjects();
    setUpNextDroppingBox();
  }

  private void createAnimationObjects( ) {
    this.lazarusAfraidAnimation = new Animation( 0, 0, Assets.lazarusAfraid );
    this.clock.addObserver( this.lazarusAfraidAnimation );

    this.lazarusMoveLeftAnimation = new Animation( 0, 0, Assets.lazarusMoveLeft );
    this.clock.addObserver( this.lazarusMoveLeftAnimation );

    this.lazarusMoveRightAnimation = new Animation( 0, 0, Assets.lazarusMoveRight );
    this.clock.addObserver( this.lazarusMoveRightAnimation );

    this.lazarusJumpUpLeftAnimation = new Animation( 0, 0, Assets.lazarusJumpLeft );
    this.clock.addObserver( this.lazarusJumpUpLeftAnimation );

    this.lazarusJumpUpRightAnimation = new Animation( 0, 0, Assets.lazarusJumpRight );
    this.clock.addObserver( this.lazarusJumpUpRightAnimation );

    this.lazarusSquishedAnimation = new Animation( 0, 0, Assets.lazarusSquished );
    this.clock.addObserver( this.lazarusSquishedAnimation );

  }

  private void createGameController( ) {
    this.playerOneController = new KeyboardControl( Assets.playerOneControls );
    this.playerOneController.addObserver( this );
  }

  public static LazarusGameWorld getInstance( ) {
    if ( lazarusGameWorld == null ) {
      lazarusGameWorld = new LazarusGameWorld();
    }
    return lazarusGameWorld;
  }

  public boolean isGameRunning( ) {
    return this.isRunning;
  }

  public Thread getThread( ) {
    return this.thread;
  }

  public KeyboardControl getPlayerOneController( ) {
    return this.playerOneController;
  }

  private void checkPlayerKeyPress( ) {
    if ( this.playerOneController.isLeft() ) {
      this.lazarusAfraidAnimation.setIsShowing( false );
      this.playerMoveLeft();
    } else if ( this.playerOneController.isRight() ) {
      this.lazarusAfraidAnimation.setIsShowing( false );
      this.playerMoveRight();
    } else {
      this.playerControlIdleCounter++;
    }
  }

  private void playerMoveLeft( ) {
    this.mapRows.get( this.lazarusCurrentRowIndex ).pop();
    if ( this.mapRows.get( this.lazarusCurrentRowIndex - 1 ).size() ==
        this.mapRows.get( this.lazarusCurrentRowIndex ).size() ) {
      this.lazarus.moveLeft();
      this.lazarusMoveLeftAnimation.setCoordinates(
          this.lazarus.getXCoordinate(),
          this.lazarus.getYCoordinate() - this.lazarus.getLength() );
      this.lazarusMoveLeftAnimation.setIsShowing( true );
      this.lazarusCurrentRowIndex--;
    } else if ( this.mapRows.get( this.lazarusCurrentRowIndex - 1 ).size() ==
        ( this.mapRows.get( this.lazarusCurrentRowIndex ).size() + 1 ) ) {
      if ( this.mapRows.get( this.lazarusCurrentRowIndex - 1 ).peek().equals( this.leftStopButton ) ) {
        this.lazarus.setCoordinates(
            this.lazarus.getXCoordinate(),
            this.lazarus.getYCoordinate() + this.lazarus.getLength()
        );
        this.lazarus.updateHitBox();
      }
      this.lazarus.jumpUpLeft();
      this.lazarusJumpUpLeftAnimation.setCoordinates(
          this.lazarus.getXCoordinate(),
          this.lazarus.getYCoordinate() - this.lazarus.getLength()
      );
      this.lazarusJumpUpLeftAnimation.setIsShowing( true );
      this.lazarusCurrentRowIndex--;
    } else if ( ( this.mapRows.get( this.lazarusCurrentRowIndex - 1 ).size() ) ==
        ( this.mapRows.get( this.lazarusCurrentRowIndex ).size() - 1 ) ) {
      this.lazarus.jumpDownLeft();
      this.lazarusMoveLeftAnimation.setCoordinates(
          this.lazarus.getXCoordinate(),
          this.lazarus.getYCoordinate() - 2 * this.lazarus.getLength() );
      this.lazarusMoveLeftAnimation.setIsShowing( true );
      this.lazarusCurrentRowIndex--;
    }
    this.mapRows.get( this.lazarusCurrentRowIndex ).push( lazarus );
    this.playerOneController.setIsLeft( false );
  }

  private void playerMoveRight( ) {
    this.mapRows.get( this.lazarusCurrentRowIndex ).pop();
    if ( this.mapRows.get( this.lazarusCurrentRowIndex + 1 ).size() ==
        this.mapRows.get( this.lazarusCurrentRowIndex ).size() ) {
      this.lazarus.moveRight();
      this.lazarusMoveRightAnimation.setCoordinates(
          this.lazarus.getXCoordinate() - this.lazarus.getWidth(),
          this.lazarus.getYCoordinate() - this.lazarus.getLength()
      );
      this.lazarusMoveRightAnimation.setIsShowing( true );
      this.lazarusCurrentRowIndex++;
    } else if ( this.mapRows.get( this.lazarusCurrentRowIndex + 1 ).size() ==
        ( this.mapRows.get( this.lazarusCurrentRowIndex ).size() + 1 ) ) {
      if ( this.mapRows.get( this.lazarusCurrentRowIndex + 1 ).peek().equals( this.rightStopButton ) ) {
        this.lazarus.setCoordinates(
            this.lazarus.getXCoordinate(),
            this.lazarus.getYCoordinate() + this.lazarus.getLength()
        );
        this.lazarus.updateHitBox();
      }
      this.lazarus.jumpUpRight();
      this.lazarusJumpUpRightAnimation.setCoordinates(
          this.lazarus.getXCoordinate() - this.lazarus.getWidth(),
          this.lazarus.getYCoordinate() - this.lazarus.getLength()
      );
      this.lazarusJumpUpRightAnimation.setIsShowing( true );
      this.lazarusCurrentRowIndex++;
    } else if ( this.mapRows.get( this.lazarusCurrentRowIndex + 1 ).size() ==
        ( this.mapRows.get( this.lazarusCurrentRowIndex ).size() - 1 ) ) {
      this.lazarus.jumpDownRight();
      this.lazarusMoveRightAnimation.setCoordinates(
          this.lazarus.getXCoordinate() - this.lazarus.getWidth(),
          this.lazarus.getYCoordinate() - 2 * this.lazarus.getLength() );
      this.lazarusMoveRightAnimation.setIsShowing( true );
      this.lazarusCurrentRowIndex++;
    }
    this.mapRows.get( this.lazarusCurrentRowIndex ).push( lazarus );
    this.playerOneController.setIsRight( false );
  }

  public synchronized void stop( ) {
    if ( !this.isRunning ) {
      return;
    }
    this.isRunning = false;
    this.clock.deleteObservers();
    this.playerOneController.deleteObservers();
  }

  private void drawGameMap( ) {
    this.mapGraphics = this.mapImage.createGraphics();
    this.mapGraphics.drawImage( Assets.background, 0, 0, null );
    for ( Deque<Entity> mapRow : this.mapRows ) {
      for ( Entity mapColumn : mapRow ) {
        if ( mapColumn.getIsShowing() ) {
          this.mapGraphics.drawImage(
              mapColumn.getCurrentGameObjectImage(),
              mapColumn.getXCoordinate(),
              mapColumn.getYCoordinate(),
              null
          );
        }
      }
    }
    if ( this.droppingBox.getIsDropping() ) {
      mapGraphics.drawImage(
          this.droppingBox.getCurrentGameObjectImage(),
          this.droppingBox.getXCoordinate(),
          this.droppingBox.getYCoordinate(),
          null
      );
    }
    this.drawAnimation();
    this.mapGraphics.dispose();
  }

  private void drawAnimation( ) {
    if ( this.lazarusAfraidAnimation.getIsShowing() ) {
      this.mapGraphics.drawImage(
          this.lazarusAfraidAnimation.getCurrentGameObjectImage(),
          this.lazarusAfraidAnimation.getXCoordinate(),
          this.lazarusAfraidAnimation.getYCoordinate(),
          null
      );
    } else if ( this.lazarusMoveLeftAnimation.getIsShowing() ) {
      this.mapGraphics.drawImage(
          this.lazarusMoveLeftAnimation.getCurrentGameObjectImage(),
          this.lazarusMoveLeftAnimation.getXCoordinate(),
          this.lazarusMoveLeftAnimation.getYCoordinate(),
          null
      );
    } else if ( this.lazarusMoveRightAnimation.getIsShowing() ) {
      this.mapGraphics.drawImage(
          this.lazarusMoveRightAnimation.getCurrentGameObjectImage(),
          this.lazarusMoveRightAnimation.getXCoordinate(),
          this.lazarusMoveRightAnimation.getYCoordinate(),
          null
      );
    } else if ( this.lazarusJumpUpLeftAnimation.getIsShowing() ) {
      this.mapGraphics.drawImage(
          this.lazarusJumpUpLeftAnimation.getCurrentGameObjectImage(),
          this.lazarusJumpUpLeftAnimation.getXCoordinate(),
          this.lazarusJumpUpLeftAnimation.getYCoordinate(),
          null
      );
    } else if ( this.lazarusJumpUpRightAnimation.getIsShowing() ) {
      this.mapGraphics.drawImage(
          this.lazarusJumpUpRightAnimation.getCurrentGameObjectImage(),
          this.lazarusJumpUpRightAnimation.getXCoordinate(),
          this.lazarusJumpUpRightAnimation.getYCoordinate(),
          null
      );
    } else if ( this.lazarusSquishedAnimation.getIsShowing() ) {
      this.mapGraphics.drawImage(
          this.lazarusSquishedAnimation.getCurrentGameObjectImage(),
          this.lazarusSquishedAnimation.getXCoordinate(),
          this.lazarusSquishedAnimation.getYCoordinate(),
          null
      );
    }

  }

  private void checkPlayerIdleTime( ) {
    if ( this.playerControlIdleCounter > 100 &&
        this.playerOneController.isLeft() == false &&
        this.playerOneController.isRight() == false ) {
      this.lazarus.setIsShowing( false );
      this.lazarusAfraidAnimation.setCoordinates(
          this.lazarus.getXCoordinate(),
          this.lazarus.getYCoordinate()
      );
      this.lazarusAfraidAnimation.setIsShowing( true );
      this.playerControlIdleCounter = 0;
    } else {
      this.lazarus.setIsShowing( true );
    }
  }

  private void setUpNextDroppingBox( ) {
    int weight = getRandomBoxWeight();
    this.droppingBox = new Box(
        this.lazarus.getXCoordinate(),
        -40,
        getBoxSpriteFromWeight( weight ),
        weight
    );
    this.nextBox.setCurrentGameObjectImage( this.droppingBox.getCurrentGameObjectImage() );
    this.nextBox.setIsShowing( true );
    this.droppingBox.setIsShowing( true );
    this.droppingBox.setIsDropping( true );
    this.clock.addObserver( this.droppingBox );
  }

  private int getRandomBoxWeight( ) {
    return this.randomNumberGenerator.nextInt( 4 ) + 1;
  }

  private Sprite getBoxSpriteFromWeight( int weight ) {
    Sprite returnSprite = Assets.wall;
    switch ( weight ) {
      case 1:
        returnSprite = Assets.cardboardBox;
        break;
      case 2:
        returnSprite = Assets.woodBox;
        break;
      case 3:
        returnSprite = Assets.metalBox;
        break;
      case 4:
        returnSprite = Assets.stoneBox;
        break;
      default:
        break;
    }
    return returnSprite;
  }

  private void checkLazarusAnimation( ) {
    if ( this.lazarusAfraidAnimation.getIsShowing() ) {
      this.lazarus.setIsShowing( false );
    }

    if ( this.lazarusMoveLeftAnimation.getIsShowing() ) {
      this.lazarus.setIsShowing( false );
    }

    if ( this.lazarusMoveRightAnimation.getIsShowing() ) {
      this.lazarus.setIsShowing( false );
    }

    if ( this.lazarusJumpUpLeftAnimation.getIsShowing() ) {
      this.lazarus.setIsShowing( false );
    }

    if ( this.lazarusJumpUpRightAnimation.getIsShowing() ) {
      this.lazarus.setIsShowing( false );
    }

    if ( this.lazarusSquishedAnimation.getIsShowing() ) {
      this.lazarus.setIsShowing( false );
    }

  }

  private void disableLazarusAnimation( ) {
    this.lazarusAfraidAnimation.setIsShowing( false );
    this.lazarusMoveLeftAnimation.setIsShowing( false );
    this.lazarusMoveRightAnimation.setIsShowing( false );
    this.lazarusJumpUpLeftAnimation.setIsShowing( false );
    this.lazarusJumpUpRightAnimation.setIsShowing( false );
    this.lazarusSquishedAnimation.setIsShowing( false );
    this.lazarus.setIsShowing( true );
  }

  private void checkCollision( ) {
    if ( this.leftStopButton.getHitBox().intersects( this.lazarus.getHitBox() ) ) {
      this.leftStopButton.collidingEntities( this.lazarus );
      disableLazarusAnimation();
    }

    if ( this.rightStopButton.getHitBox().intersects( this.lazarus.getHitBox() ) ) {
      this.rightStopButton.collidingEntities( this.lazarus );
      disableLazarusAnimation();
    }

    if ( !this.droppingBox.getIsStacked() || this.droppingBox.getIsDropping() ) {
      for ( Deque<Entity> mapRowTop : this.mapRows ) {
        if ( mapRowTop.peek().getHitBox().intersects( this.droppingBox.getHitBox() ) ) {
          mapRowTop.peek().collidingEntities( this.droppingBox );
          if ( !mapRowTop.peek().getIsShowing() ) {
            mapRowTop.pop();
          } else {
            mapRowTop.push( this.droppingBox );
          }
        }
      }
    }
  }

  private void drawLazarusGettingSquished( Graphics graphics ) {
    int endScreenXCoordinate = ( Assets.background.getWidth() -
        Assets.title.getWidth() ) / 2;
    int endScreenYCoordinate = ( Assets.background.getHeight() -
        Assets.title.getHeight() ) / 2;
    graphics.drawImage(
        Assets.title,
        endScreenXCoordinate,
        endScreenYCoordinate,
        null
    );

    if ( this.lazarusSquishedFrame != this.lazarusSquishedAnimation.getLastImageIndex() ) {
      this.lazarusSquishedAnimation.setCoordinates(
          this.lazarus.getXCoordinate(),
          this.lazarus.getYCoordinate()
      );
      this.lazarusSquishedAnimation.setIsShowing( true );
      this.lazarusSquishedFrame++;
    } else {
      this.lazarus.setIsSquashed( true );
    }
  }

  private void drawLazarusPressingStopButton( Graphics graphics ) {
    int endScreenXCoordinate = ( Assets.background.getWidth() -
        Assets.title.getWidth() ) / 2;
    int endScreenYCoordinate = ( Assets.background.getHeight() -
        Assets.title.getHeight() ) / 2;
    graphics.drawImage( Assets.title, endScreenXCoordinate, endScreenYCoordinate, null );
    graphics.drawImage(
        this.lazarus.getCurrentGameObjectImage(),
        this.lazarus.getXCoordinate(),
        this.lazarus.getYCoordinate(),
        null
    );
  }

  @Override
  public void paintComponent( Graphics graphics ) {
    super.paintComponent( graphics );
    this.drawGameMap();
    graphics.drawImage( mapImage, 0, 0, null );
    if ( this.lazarus.getIsGettingSquashed() || this.lazarus.getIsSquashed() ) {
      drawLazarusGettingSquished( graphics );
    } else if ( this.leftStopButton.getIsPressed() ||
        this.rightStopButton.getIsPressed() ) {
      drawLazarusPressingStopButton( graphics );
    }
  }

  @Override
  public void update( Observable o, Object arg ) {
    if ( this.lazarus.getIsGettingSquashed() || this.leftStopButton.getIsPressed() || this.rightStopButton.getIsPressed() ) {
      repaint();
      if ( this.lazarus.getIsSquashed() || this.leftStopButton.getIsPressed() || this.rightStopButton.getIsPressed() ) {
        this.lazarus.setIsGettingSquashed( false );
        stop();
      }
    }

    if ( !this.droppingBox.getIsDropping() && this.droppingBox.getIsStacked() ) {
      this.setUpNextDroppingBox();
    }

    this.checkPlayerKeyPress();
    this.checkPlayerIdleTime();
    this.checkLazarusAnimation();
    this.checkCollision();
    this.repaint();
  }

}
