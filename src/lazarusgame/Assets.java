package lazarusgame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Assets {

  public static final int WINDOWWIDTH = 640;
  public static final int WINDOWHEIGHT = 510;

  public static int[][] levelFileArray;

  public static Sprite stopButton;
  public static Sprite cardboardBox;
  public static Sprite lazarusAfraid;
  public static Sprite lazarusJumpLeft;
  public static Sprite lazarusJumpRight;
  public static Sprite lazarusMoveLeft;
  public static Sprite lazarusMoveRight;
  public static Sprite lazarusSquished;
  public static Sprite lazarusStanding;
  public static Sprite mesh;
  public static Sprite metalBox;
  public static Sprite rock;
  public static Sprite stoneBox;
  public static Sprite wall;
  public static Sprite woodBox;

  public static BufferedImage background;
  public static BufferedImage title;


  public static final String BACKGROUNDIMAGE = "Lazarus/Resources/Lazarus Graphics/Background.png";
  public static final String TITLE = "Lazarus/Resources/Lazarus Graphics/Title.png";

  public static final String STOPBUTTON = "Lazarus/Resources/Lazarus Graphics/Button.png";
  public static final String CARDBOARDBOX = "Lazarus/Resources/Lazarus Graphics/CardBox.png";
  public static final String LAZARUSICON = "Lazarus/Resources/Lazarus Graphics/lazarus.ico";
  public static final String LAZARUSAFRAID = "Lazarus/Resources/Lazarus Graphics/Lazarus_afraid_strip10.png";
  public static final String LAZARUSJUMPLEFT = "Lazarus/Resources/Lazarus Graphics/Lazarus_jump_left_strip7.png";
  public static final String LAZARUSJUMPRIGHT = "Lazarus/Resources/Lazarus Graphics/Lazarus_jump_right_strip7.png";
  public static final String LAZARUSMOVELEFT = "Lazarus/Resources/Lazarus Graphics/Lazarus_left_strip7.png";
  public static final String LAZARUSMOVERIGHT = "Lazarus/Resources/Lazarus Graphics/Lazarus_right_strip7.png";
  public static final String LAZARUSSQUISHED = "Lazarus/Resources/Lazarus Graphics/Lazarus_squished_strip11.png";
  public static final String LAZARUSSTANDING = "Lazarus/Resources/Lazarus Graphics/Lazarus_stand.png";
  public static final String MESH = "Lazarus/Resources/Lazarus Graphics/Mesh.png";
  public static final String METALBOX = "Lazarus/Resources/Lazarus Graphics/MetalBox.png";
  public static final String ROCK = "Lazarus/Resources/Lazarus Graphics/Rock.png";
  public static final String STONEBOX = "Lazarus/Resources/Lazarus Graphics/StoneBox.png";
  public static final String WALL = "Lazarus/Resources/Lazarus Graphics/Wall.png";
  public static final String WOODBOX = "Lazarus/Resources/Lazarus Graphics/WoodBox.png";

  public static final String BUTTONSOUND = "Lazarus/Resources/Lazarus Sounds/Button.wav";
  public static final String CRUSHSOUND = "Lazarus/Resources/Lazarus Sounds/Crush.wav";
  public static final String MOVESOUND = "Lazarus/Resources/Lazarus Sounds/Move.wav";
  public static final String MUSIC = "Lazarus/Resources/Lazarus Sounds/Music.mp3";
  public static final String SQUISHEDSOUND = "Lazarus/Resources/Lazarus Sounds/Squished.wav";
  public static final String WALLSOUND = "Lazarus/Resources/Lazarus Sounds/Wall.wav";

  public static final String LEVELFILE = "Lazarus/Resources/level.txt";


  public static void initialize( ) {
    try {
      background = ImageIO.read( new File( BACKGROUNDIMAGE ) );
      title = ImageIO.read( new File( TITLE ) );
      stopButton = new Sprite( STOPBUTTON, 40 );
      cardboardBox = new Sprite( CARDBOARDBOX, 40 );
      lazarusAfraid = new Sprite( LAZARUSAFRAID, 40 );
      lazarusJumpLeft = new Sprite( LAZARUSJUMPLEFT, 80 );
      lazarusJumpRight = new Sprite( LAZARUSJUMPRIGHT, 80 );
      lazarusMoveLeft = new Sprite( LAZARUSMOVELEFT, 80 );
      lazarusMoveRight = new Sprite( LAZARUSMOVERIGHT, 80 );
      lazarusSquished = new Sprite( LAZARUSSQUISHED, 40 );
      lazarusStanding = new Sprite( LAZARUSSTANDING, 40 );
      mesh = new Sprite( MESH, 40 );
      metalBox = new Sprite( METALBOX, 40 );
      rock = new Sprite( ROCK, 40 );
      stoneBox = new Sprite( STONEBOX, 40 );
      wall = new Sprite( WALL, 40 );
      woodBox = new Sprite( WOODBOX, 40 );

      levelFileArray = parseLevelFile( LEVELFILE );
    } catch ( IOException exception ) {
      exception.printStackTrace();
    }
  }

  public static int[][] parseLevelFile( String resourceLocation ) {
    Path path = FileSystems.getDefault().getPath( resourceLocation );
    int[][] levelMap = new int[0][0];

    try {
      java.util.List<String> levelRows = Files.readAllLines( path );
      levelMap = new int[levelRows.size()][levelRows.get( 0 ).length()];

      for ( int i = 0; i < levelRows.size(); i++ ) {
        String row = levelRows.get( i );

        for ( int j = 0; j < row.length(); j++ ) {
          String currentCharacter = String.valueOf( row.charAt( j ) );
          int value;

          if ( currentCharacter.matches( "\\d+" ) ) {
            value = Integer.parseInt( currentCharacter );
          } else {
            value = 0;
          }
          levelMap[i][j] = value;
        }
      }
    } catch ( IOException e ) {
      e.printStackTrace();
    }

    return levelMap;
  }

  public enum PlayerControls {
    a( 65 ),
    d( 68 );

    private final int KEYCODE;

    PlayerControls( int keyCode ) {
      KEYCODE = keyCode;
    }

    public int getKEYCODE( ) {
      return KEYCODE;
    }

  }

  public static PlayerControls[] playerOneControls = new PlayerControls[]{
      PlayerControls.a,
      PlayerControls.d
  };

}
