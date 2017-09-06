package org.usfirst.frc.team5401.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;
	public static final int conveyors     = 0;
	public static final int meteringMotor = 1;
	
	//Global Variables
	public static final double DRIVE_SENSITIVITY_DEFAULT = 1.0;
	public static final double DRIVE_SENSITIVITY_PRECISE = 0.2;
	public static final double DRIVE_SPIN_SENSITIVITY 	 = 0.5;
	public static final double DRIVE_THRESHHOLD 		 = 0.1;
		
	//Controllers
	public static final int XBOX_CONTROLLER_DRIVER   = 0;
	public static final int XBOX_CONTROLLER_OPERATOR = 1;
	
	//Controller Axis
	public static final int LEFT_STICK_AXIS_X    = 0;
	public static final int LEFT_STICK_AXIS_Y    = 1;
	public static final int LEFT_TRIGGER_AXIS    = 2;
	public static final int RIGHT_TRIGGER_AXIS   = 3;
	public static final int RIGHT_STICK_AXIS_X	 = 4;
	public static final int RIGHT_STICK_AXIS_Y   = 5;
	
    //PWM Motors
	public static final int LEFT_DRIVE_MOTOR_1    = 0;
	public static final int RIGHT_DRIVE_MOTOR_1   = 1;
	public static final int LEFT_DRIVE_MOTOR_2    = 3;
	public static final int RIGHT_DRIVE_MOTOR_2   = 4;
	public static final int INFEEDER_BAR          = 5;
	public static final int CLIMBER_MOTOR         = 6;
	
	//Sensors
	public static final int LEFT_ENC_A  = 0;
	public static final int RIGHT_ENC_A = 1;
	public static final int LEFT_ENC_B  = 2;
	public static final int RIGHT_ENC_B = 3;
	
	//Pneumatics
	public static final int PCM_ID = 0;
	public static final int SHIFTER_IN      = 0;
	public static final int SHIFTER_OUT     = 1;
	public static final int INFEED_IN       = 2;
	public static final int INFEED_OUT      = 3;
	public static final int UNJAM_IN        = 4;
	public static final int UNJAM_OUT       = 5;
	public static final int GEAR_DOOR_CLOSE = 6;
	public static final int GEAR_DOOR_OPEN  = 7;
	//Analog
	public static final int PRESSURE_SENSOR  = 0;
	

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
