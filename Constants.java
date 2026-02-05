package andrieste;

public class Constants {
    public static final int PX_MULTIPLIER = 4;

    public static final int SCENE_HEIGHT = 180 * PX_MULTIPLIER;
    public static final int SCENE_WIDTH = 320 *  PX_MULTIPLIER;



    public static final double ROWS = 40;
    public static final double SQ_W= SCENE_WIDTH / ROWS;
    //seconds
    public static final double DURATION = 0.016;

    public static final double ANDRIESTE_WIDTH = 32;
    public static final double ANDRIESTE_HEIGHT = 2*ANDRIESTE_WIDTH;
    //Andrieste Movement Physics
    public static final int GRAVITY = 100; // acceleration constant (UNITS: pixels/s^2)
    public static final int JUMP_Y_VELOCITY = -20; // initial jump velocity (UNITS: pixels/s)
    public static final double SLOW_DOWN_VELOCITY = .5;
    public static final double RUNNING_VELOCITY = 7;
    public static final double WALL_JUMP_X_VELOCITY = 17;
    public static final double WALL_JUMP_Y_VELOCITY = -20;
    public static final double MIN_CHANGE_DIRECTION_VELOCITY = 5;
    public static final double DASH_TIME = 0.25;
    public static final double DASH_COOLDOWN = 0.10;
    public static final double DASHING_TOTAL_VELOCITY =10;
    public static final double DASH_SIDE_X_VELOCITY = DASHING_TOTAL_VELOCITY;
    public static final double DASH_SIDE_Y_VELOCITY = 0;
    public static final double DASH_UP_Y_VELOCITY =-DASHING_TOTAL_VELOCITY;
    public static final double DASH_UP_X_VELOCITY = 0;
    public static final double DASH_DIAGONAL_Y_VELOCITY = -Math.sqrt((DASHING_TOTAL_VELOCITY *  DASHING_TOTAL_VELOCITY)/2);
    public static final double DASH_DIAGONAL_X_VELOCITY = Math.sqrt((DASHING_TOTAL_VELOCITY *  DASHING_TOTAL_VELOCITY)/2);

    //rewind logic
    public static final int STATE_QUEUE_SIZE = 90;
    public static final double REWIND_MARKER_WIDTH = 12;
    public static final double REWIND_MARKER_HEIGHT = 12;

    public static final double SLIDING_PLATFORM_X_CHANGE = SQ_W *4;
    public static final double SLIDING_PLATFORM_X_DELTA = SLIDING_PLATFORM_X_CHANGE/8;
    public static final double SLIDING_PLATFORM_VELOCITY = 20;

    public static final double JUMP_REWIND_COOLDOWN = 0.10;
    public static final double DASH_ORB_COOLDOWN = .5;

    public static final double BOUNCY_Y_VELOCITY = -22.5;

    public static final double PLATFORM_MAX_X_DISTANCE_EASY = 150;
    public static final double PLATFORM_MAX_Y_DISTANCE_EASY = -110;
    public static final double PLATFORM_MAX_X_DISTANCE_MEDIUM = 200;
    public static final double PLATFORM_MAX_Y_DISTANCE_MEDIUM = -200;
    public static final double PLATFORM_MAX_X_DISTANCE_HARD = 275;
    public static final double PLATFORM_MAX_Y_DISTANCE_HARD = -250;

    public static final double PLATFORM_MIN_X_DISTANCE_EASY = 100;
    public static final double PLATFORM_MIN_Y_DISTANCE_EASY = -100;
    public static final double PLATFORM_MIN_X_DISTANCE_MEDIUM = 150;
    public static final double PLATFORM_MIN_Y_DISTANCE_MEDIUM = -125;
    public static final double PLATFORM_MIN_X_DISTANCE_HARD = 225;
    public static final double PLATFORM_MIN_Y_DISTANCE_HARD = -200;

    public static final double DASH_ORB_MAX_X_DISTANCE_EASY = 75;
    public static final double DASH_ORB_MAX_Y_DISTANCE_EASY = -55;
    public static final double DASH_ORB_MAX_X_DISTANCE_MEDIUM = 110;
    public static final double DASH_ORB_MAX_Y_DISTANCE_MEDIUM = -85;
    public static final double DASH_ORB_MAX_X_DISTANCE_HARD = 150;
    public static final double DASH_ORB_MAX_Y_DISTANCE_HARD = -125;

    public static final double DASH_ORB_MIN_X_DISTANCE_EASY = 75;
    public static final double DASH_ORB_MIN_Y_DISTANCE_EASY = -60;
    public static final double DASH_ORB_MIN_X_DISTANCE_MEDIUM = 110;
    public static final double DASH_ORB_MIN_Y_DISTANCE_MEDIUM = -90;
    public static final double DASH_ORB_MIN_X_DISTANCE_HARD = 150;
    public static final double DASH_ORB_MIN_Y_DISTANCE_HARD = -130;

    public static final double SLIDING_PLATFORM_MAX_X_DISTANCE_EASY = 300;
    public static final double SLIDING_PLATFORM_MAX_Y_DISTANCE_EASY = -80;   // from PLATFORM_MAX_Y_DISTANCE_EASY
    public static final double SLIDING_PLATFORM_MAX_X_DISTANCE_MEDIUM = 400;
    public static final double SLIDING_PLATFORM_MAX_Y_DISTANCE_MEDIUM = -175; // from PLATFORM_MAX_Y_DISTANCE_MEDIUM
    public static final double SLIDING_PLATFORM_MAX_X_DISTANCE_HARD = 500;
    public static final double SLIDING_PLATFORM_MAX_Y_DISTANCE_HARD = -250;  // from PLATFORM_MAX_Y_DISTANCE_HARD

    public static final double SLIDING_PLATFORM_MIN_X_DISTANCE_EASY = 250;
    public static final double SLIDING_PLATFORM_MIN_Y_DISTANCE_EASY = -60;   // from PLATFORM_MIN_Y_DISTANCE_EASY
    public static final double SLIDING_PLATFORM_MIN_X_DISTANCE_MEDIUM = 350;
    public static final double SLIDING_PLATFORM_MIN_Y_DISTANCE_MEDIUM = -125; // from PLATFORM_MIN_Y_DISTANCE_MEDIUM
    public static final double SLIDING_PLATFORM_MIN_X_DISTANCE_HARD = 450;
    public static final double SLIDING_PLATFORM_MIN_Y_DISTANCE_HARD = -200;  // from PLATFORM_MIN_Y_DISTANCE_HARD

    public static final double BOUNCY_PLATFORM_MAX_X_DISTANCE_EASY = 200;
    public static final double BOUNCY_PLATFORM_MAX_Y_DISTANCE_EASY = -175;
    public static final double BOUNCY_PLATFORM_MAX_X_DISTANCE_MEDIUM = 350;
    public static final double BOUNCY_PLATFORM_MAX_Y_DISTANCE_MEDIUM = -250;
    public static final double BOUNCY_PLATFORM_MAX_X_DISTANCE_HARD = 450;
    public static final double BOUNCY_PLATFORM_MAX_Y_DISTANCE_HARD = -350;

    public static final double BOUNCY_PLATFORM_MIN_X_DISTANCE_EASY = 175;
    public static final double BOUNCY_PLATFORM_MIN_Y_DISTANCE_EASY = -125;
    public static final double BOUNCY_PLATFORM_MIN_X_DISTANCE_MEDIUM = 300;
    public static final double BOUNCY_PLATFORM_MIN_Y_DISTANCE_MEDIUM = -200;
    public static final double BOUNCY_PLATFORM_MIN_X_DISTANCE_HARD = 375;
    public static final double BOUNCY_PLATFORM_MIN_Y_DISTANCE_HARD = -275;

    public static final double WALL_PLATFORM_MAX_X_DISTANCE_EASY = 300;
    public static final double WALL_PLATFORM_MAX_Y_DISTANCE_EASY = -125;
    public static final double WALL_PLATFORM_MAX_X_DISTANCE_MEDIUM = 400;
    public static final double WALL_PLATFORM_MAX_Y_DISTANCE_MEDIUM = -150;
    public static final double WALL_PLATFORM_MAX_X_DISTANCE_HARD = 500;
    public static final double WALL_PLATFORM_MAX_Y_DISTANCE_HARD = -175;

    public static final double WALL_PLATFORM_MIN_X_DISTANCE_EASY = 250;
    public static final double WALL_PLATFORM_MIN_Y_DISTANCE_EASY = -100;
    public static final double WALL_PLATFORM_MIN_X_DISTANCE_MEDIUM = 350;
    public static final double WALL_PLATFORM_MIN_Y_DISTANCE_MEDIUM = -15;
    public static final double WALL_PLATFORM_MIN_X_DISTANCE_HARD = 475;
    public static final double WALL_PLATFORM_MIN_Y_DISTANCE_HARD = -150;


    public static final double WINNING_PLATFORM_WIDTH = 100;
    public static final double WINNING_PLATFORM_HEIGHT = 25;
    public static final double STARTING_PLATFORM_WIDTH = 100;
    public static final double STARTING_PLATFORM_HEIGHT = 50;


    public static final double RANDOM_DIRECTION_DEGREES = 180;

    public static final double EASY_PLATFORM_MAX_WIDTH = 140;
    public static final double MEDIUM_PLATFORM_MAX_WIDTH = 115;
    public static final double HARD_PLATFORM_MAX_WIDTH = 90;

    public static final double EASY_PLATFORM_MIN_WIDTH = 100;
    public static final double MEDIUM_PLATFORM_MIN_WIDTH = 85;
    public static final double HARD_PLATFORM_MIN_WIDTH = 60;

    public static final double EASY_WALL_MAX_HEIGHT = 160;
    public static final double MEDIUM_WALL_MAX_HEIGHT = 130;
    public static final double HARD_WALL_MAX_HEIGHT = 90;

    public static final double EASY_WALL_MIN_HEIGHT = 130;
    public static final double MEDIUM_WALL_MIN_HEIGHT = 100;
    public static final double HARD_WALL_MIN_HEIGHT = 70;

    public static final double WALL_WIDTH = 50;
    public static final double DASH_ORB_WIDTH = 25;



    public static final double EASY_RAND_SLOPE = .5;
    public static final double MEDIUM_RAND_SLOPE = 1;
    public static final double HARD_RAND_SLOPE = 1;

    public static final double MIN_PLATFORM_HEIGHT = 200;
    public static final double MAX_PLATFORM_HEIGHT = SCENE_HEIGHT - 100;

    public static final double NORMAL_PLATFORM_HEIGHT = 20;

    public static final double TITLE_Y = 0;
    public static final double TITLE_WIDTH = 1280;
    public static final double TITLE_X = 0;

    public static final double TITLE_BUTTON_Y = 400;
    public static final double TITLE_BUTTON_WIDTH = 300;
    public static final double TITLE_BUTTON_X = SCENE_WIDTH / 2 -150;

    public static final double LEVEL_BUTTON_Y = 400;
    public static final double LEVEL_BUTTON_WIDTH = 200;
    public static final double LEVEL_BUTTON_X = (SCENE_WIDTH / 2) - 100;

    public static final double RANDOM_LEVEL_BUTTON_Y = 400;
    public static final double RANDOM_LEVEL_BUTTON_WIDTH = 200;
    public static final double RANDOM_LEVEL_BUTTON_X = (SCENE_WIDTH / 2) - 300 - RANDOM_LEVEL_BUTTON_WIDTH/2;

    public static final double TUTORIAL_LEVEL_BUTTON_Y = 400;
    public static final double TUTORIAL_LEVEL_BUTTON_WIDTH = 200;
    public static final double TUTORIAL_LEVEL_BUTTON_X = (SCENE_WIDTH / 2) + 300 - TUTORIAL_LEVEL_BUTTON_WIDTH/2;

    public static final double EASY_RANDOM_LEVEL_BUTTON_Y = 400;
    public static final double EASY_RANDOM_LEVEL_BUTTON_WIDTH = 200;
    public static final double EASY_RANDOM_LEVEL_BUTTON_X = (SCENE_WIDTH / 2) - 300 - EASY_RANDOM_LEVEL_BUTTON_WIDTH / 2;

    public static final double MEDIUM_RANDOM_LEVEL_BUTTON_Y = 400;
    public static final double MEDIUM_RANDOM_LEVEL_BUTTON_WIDTH = 200;
    public static final double MEDIUM_RANDOM_LEVEL_BUTTON_X = (SCENE_WIDTH / 2) - 100;

    public static final double HARD_RANDOM_LEVEL_BUTTON_Y = 400;
    public static final double HARD_RANDOM_LEVEL_BUTTON_WIDTH = 200;
    public static final double HARD_RANDOM_LEVEL_BUTTON_X = (SCENE_WIDTH / 2) + 300 - HARD_RANDOM_LEVEL_BUTTON_WIDTH / 2;

    public static final double ONE_LEVEL_BUTTON_Y = 400;
    public static final double ONE_LEVEL_BUTTON_WIDTH = 75;
    public static final double ONE_LEVEL_BUTTON_X = (SCENE_WIDTH / 2) - 400 - ONE_LEVEL_BUTTON_WIDTH / 2;

    public static final double TWO_LEVEL_BUTTON_Y = 400;
    public static final double TWO_LEVEL_BUTTON_WIDTH = ONE_LEVEL_BUTTON_WIDTH;
    public static final double TWO_LEVEL_BUTTON_X = (SCENE_WIDTH / 2) - 200 - TWO_LEVEL_BUTTON_WIDTH / 2;

    public static final double THREE_LEVEL_BUTTON_Y = 400;
    public static final double THREE_LEVEL_BUTTON_WIDTH = ONE_LEVEL_BUTTON_WIDTH;
    public static final double THREE_LEVEL_BUTTON_X = (SCENE_WIDTH / 2) - THREE_LEVEL_BUTTON_WIDTH / 2;

    public static final double FOUR_LEVEL_BUTTON_Y = 400;
    public static final double FOUR_LEVEL_BUTTON_WIDTH = ONE_LEVEL_BUTTON_WIDTH;
    public static final double FOUR_LEVEL_BUTTON_X = (SCENE_WIDTH / 2) + 200 - FOUR_LEVEL_BUTTON_WIDTH / 2;

    public static final double FIVE_LEVEL_BUTTON_Y = 400;
    public static final double FIVE_LEVEL_BUTTON_WIDTH = ONE_LEVEL_BUTTON_WIDTH;
    public static final double FIVE_LEVEL_BUTTON_X = (SCENE_WIDTH / 2) + 400 - FIVE_LEVEL_BUTTON_WIDTH / 2;

    public static final double BACK_BUTTON_Y = 10;
    public static final double BACK_BUTTON_WIDTH = 100;
    public static final double BACK_BUTTON_X = 10;

    public static final double HOW_TO_PLAY_X = 0;
    public static final double HOW_TO_PLAY_WIDTH = SCENE_WIDTH;
    public static final double HOW_TO_PLAY_Y = 0;

    public static final double QUIT_Y = 550;
    public static final double QUIT_WIDTH = 200;
    public static final double QUIT_X = SCENE_WIDTH / 2 -QUIT_WIDTH /2;

    public static final double PAUSE_AREA_Y = 200;
    public static final double PAUSE_AREA_WIDTH = 500;
    public static final double PAUSE_AREA_X = (SCENE_WIDTH / 2) - PAUSE_AREA_WIDTH / 2;;

    public static final double HOME_BUTTON_Y = 288;
    public static final double HOME_BUTTON_WIDTH = 150;
    public static final double HOME_BUTTON_X = (SCENE_WIDTH / 2) + 100- HOME_BUTTON_WIDTH / 2;

    public static final double RESUME_BUTTON_Y = 288;
    public static final double RESUME_BUTTON_WIDTH = HOME_BUTTON_WIDTH;
    public static final double RESUME_BUTTON_X = (SCENE_WIDTH / 2) - 100 - RESUME_BUTTON_WIDTH / 2;

    public static final double PAUSE_COOLDOWN = .25;

    public static final double ONE_CHECKMARK_X =250;
    public static final double ONE_CHECKMARK_Y =400;
    public static final double ONE_CHECKMARK_WIDTH = 40;

    public static final double TWO_CHECKMARK_X = ONE_CHECKMARK_X + 200;
    public static final double TWO_CHECKMARK_Y =ONE_CHECKMARK_Y;
    public static final double TWO_CHECKMARK_WIDTH = ONE_CHECKMARK_WIDTH;

    public static final double THREE_CHECKMARK_X = ONE_CHECKMARK_X + 400;
    public static final double THREE_CHECKMARK_Y =ONE_CHECKMARK_Y;
    public static final double THREE_CHECKMARK_WIDTH = ONE_CHECKMARK_WIDTH;

    public static final double FOUR_CHECKMARK_X = ONE_CHECKMARK_X + 600;
    public static final double FOUR_CHECKMARK_Y =ONE_CHECKMARK_Y;
    public static final double FOUR_CHECKMARK_WIDTH = ONE_CHECKMARK_WIDTH;

    public static final double FIVE_CHECKMARK_X = ONE_CHECKMARK_X + 800;
    public static final double FIVE_CHECKMARK_Y =ONE_CHECKMARK_Y;
    public static final double FIVE_CHECKMARK_WIDTH = ONE_CHECKMARK_WIDTH;

    public static final double TIME_LABEL_X = 222.5;
    public static final int TIME_LABEL_Y = 480;
    public static final int TIME_LABEL_X_OFFSET = 200;

    public static final double EASY_LABEL_X = 317;
    public static final double EASY_LABEL_Y = 465;

    public static final double MEDIUM_LABEL_X = EASY_LABEL_X + 300;
    public static final double MEDIUM_LABEL_Y = EASY_LABEL_Y;

    public static final double HARD_LABEL_X = EASY_LABEL_X + 600;
    public static final double HARD_LABEL_Y = EASY_LABEL_Y;

    public static final double DURATION_LABEL_X = SCENE_WIDTH-100;

    public static final double RUNNING_ANIMATION_FRAME_UPDATE = 4;

    public static final double UP_DIRECTION =  Math.PI/2 - .1;

    public static final double SMALL_SLIDING_PLATFORM_WIDTH = 60;
    public static final double MEDIUM_SLIDING_PLATFORM_WIDTH =85;














}
