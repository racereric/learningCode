package org.usfirst.frc.team5401.robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team5401.robot.commands.*;
import org.usfirst.frc.team5401.robot.autonomous.*;
import org.usfirst.frc.team5401.robot.RobotMap;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);
	
	Joystick xboxController_Driver    = new Joystick(RobotMap.XBOX_CONTROLLER_DRIVER);
	Joystick xboxController_Operator  = new Joystick(RobotMap.XBOX_CONTROLLER_OPERATOR); 
	
	//Buttons (Block1 = driver, Block2 = Operator)
	Button xboxA_Driver			  = new JoystickButton(xboxController_Driver, 1);
	Button xboxB_Driver			  = new JoystickButton(xboxController_Driver, 2);
	Button xboxX_Driver			  = new JoystickButton(xboxController_Driver, 3);
	Button xboxY_Driver			  = new JoystickButton(xboxController_Driver, 4);
	Button xboxLeftBumper_Driver  = new JoystickButton(xboxController_Driver, 5);
	Button xboxRightBumper_Driver = new JoystickButton(xboxController_Driver, 6);
	Button xboxBack_Driver		  = new JoystickButton(xboxController_Driver, 7);
	Button xboxStart_Driver		  = new JoystickButton(xboxController_Driver, 8);
	Button xboxL3_Driver		  = new JoystickButton(xboxController_Driver, 9);
	Button xboxR3_Driver		  = new JoystickButton(xboxController_Driver, 10);
	
	Button xboxA_Operator			= new JoystickButton(xboxController_Operator, 1);
	Button xboxB_Operator			= new JoystickButton(xboxController_Operator, 2);
	Button xboxX_Operator			= new JoystickButton(xboxController_Operator, 3);
	Button xboxY_Operator			= new JoystickButton(xboxController_Operator, 4);
	Button xboxLeftBumper_Operator  = new JoystickButton(xboxController_Operator, 5);
	Button xboxRightBumper_Operator = new JoystickButton(xboxController_Operator, 6);
	Button xboxBack_Operator		= new JoystickButton(xboxController_Operator, 7);
	Button xboxStart_Operator		= new JoystickButton(xboxController_Operator, 8);
	Button xboxL3_Operator		  	= new JoystickButton(xboxController_Operator, 9);
	Button xboxR3_Operator		  	= new JoystickButton(xboxController_Operator, 10);
	
	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());

	public OI(){

		//Loader
		xboxA_Driver.whenPressed(new loadShooter());
		
		//Gear Mechanism
		xboxX_Driver.whenPressed(new PopGear(1));
		xboxX_Driver.whenReleased(new PopGear(-1));
		
		//Unjammer
		xboxLeftBumper_Operator.whenPressed(new unjamIn());
		
		xboxRightBumper_Operator.whenPressed(new unjamToggle(1));
		xboxRightBumper_Operator.whenReleased(new unjamToggle(-1));

		//Climber Button
		xboxY_Operator.whenPressed(new Climb(1));
		xboxY_Operator.whenReleased(new Climb(0));

		//Compressor
		xboxStart_Operator.whenPressed(new CompressorToggle());
		
		//Override for starting the XboxMove command
		xboxX_Driver.whenPressed(new XboxMove());
		
	}
	
	/**Method Naming: 'read' = Analog; 'get' = Digital **/
	
	public double readXboxLeftX_Driver(){
		return xboxController_Driver.getRawAxis(RobotMap.LEFT_STICK_AXIS_X);
	}
	
	public double readLeftTrigger_Driver(){
		return xboxController_Driver.getRawAxis(RobotMap.LEFT_TRIGGER_AXIS);
	}
	
	public double readRightTrigger_Driver(){
		return xboxController_Driver.getRawAxis(RobotMap.RIGHT_TRIGGER_AXIS);
	}
	
	//Feeder Up/Down
	public int getXboxLeftY_Operator(){
		double value = xboxController_Operator.getRawAxis(RobotMap.LEFT_STICK_AXIS_Y);
		if (value > .5){
			return -1;
		}
		else if (value < -.5){
			return 1;
		}
		else {
			return 0;
		}
	}

	//Feeder In/Out
	public int getTriggers_Operator(){
		double left  = xboxController_Operator.getRawAxis(RobotMap.LEFT_TRIGGER_AXIS);
		double right = xboxController_Operator.getRawAxis(RobotMap.RIGHT_TRIGGER_AXIS);
		if (right > .1){
			return 1;
		}
		else if (left > .1){
			return -1;
		}
		else {
			return  0;
		}
	}
	
	//Precision
	public boolean getPrecision_Driver(){
		return xboxController_Driver.getRawButton(5);
	}
	
	//Brake
	public boolean getBrake_Driver(){
		return xboxController_Driver.getRawButton(6);
	}
	
	public boolean getTurnButton_Driver(){
		return xboxController_Driver.getRawButton(9);
	}
	
	public boolean getDriveInvertButton_Driver() {
		return xboxController_Driver.getRawButton(2);
	}
	
	//Low gear
	public boolean getBack_Driver(){
		return xboxController_Driver.getRawButton(RobotMap.XBOX_BACK_DRIVER);
	}
	//High gear
	public boolean getStart_Driver(){
		return xboxController_Driver.getRawButton(RobotMap.XBOX_START_DRIVER);
	}
}
