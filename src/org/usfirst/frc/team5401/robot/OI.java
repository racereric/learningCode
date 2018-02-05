package org.usfirst.frc.team5401.robot;

import edu.wpi.first.wpilibj.buttons.Button;

import org.usfirst.frc.team5401.robot.commands.*;
import org.usfirst.frc.team5401.robot.subsystems.*;
import org.usfirst.frc.team5401.robot.RobotMap;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

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
	
	Joystick driveCtrlr    = new Joystick(RobotMap.XBOX_CONTROLLER_DRIVER);
	Joystick operatorCtrlr = new Joystick(RobotMap.XBOX_CONTROLLER_OPERATOR); 
	
	//Buttons (Block1 = driver, Block2 = Operator)
	Button driverA       = new JoystickButton(driveCtrlr, 1);
	Button driverB       = new JoystickButton(driveCtrlr, 2);
	Button driverX       = new JoystickButton(driveCtrlr, 3);
	Button driverY       = new JoystickButton(driveCtrlr, 4);
	Button driveLB       = new JoystickButton(driveCtrlr, 5);
	Button driveRB       = new JoystickButton(driveCtrlr, 6);
	Button driveBack     = new JoystickButton(driveCtrlr, 7);
	Button driveStart    = new JoystickButton(driveCtrlr, 8);
	Button driveL3       = new JoystickButton(driveCtrlr, 9);
	Button driveR3       = new JoystickButton(driveCtrlr, 10);
	
	Button operatorA     = new JoystickButton(operatorCtrlr, 1);
	Button operatorB     = new JoystickButton(operatorCtrlr, 2);
	Button operatorX     = new JoystickButton(operatorCtrlr, 3);
	Button operatorY     = new JoystickButton(operatorCtrlr, 4);
	Button operatorLB    = new JoystickButton(operatorCtrlr, 5);
	Button operatorRB    = new JoystickButton(operatorCtrlr, 6);
	Button operatorBack  = new JoystickButton(operatorCtrlr, 7);
	Button operatorStart = new JoystickButton(operatorCtrlr, 8);
	Button operatorL3    = new JoystickButton(operatorCtrlr, 9);
	Button operatorR3    = new JoystickButton(operatorCtrlr, 10);
	
	
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
		driverA.whenPressed(new loadShooter());
		
		//Flywheels
		operatorB.whenPressed(new FlywheelControl());
		operatorB.whenReleased(new FlywheelControl());
		
		//Shooter %VBus Override
		operatorBack.whenPressed(new ShooterToggle());
		
		//Shooter CeaseFire
		operatorR3.whenPressed(new CeaseFire());
		
		
		//Gear Mechanism
		driverX.whenPressed(new PopGear(1));
		driverX.whenReleased(new PopGear(-1));
		
		//Unjammer
		operatorLB.whenPressed(new UnjamIn());
		
		operatorRB.whenPressed(new UnjamToggle(1));
		operatorRB.whenReleased(new UnjamToggle(-1));
		
		//Compressor Override
//		operatorStart.whenPressed(new toggleCompressor());
		
		//Climber
		operatorY.whenPressed(new Climb(1));
		operatorY.whenReleased(new Climb(0));
		
		//Xbox Move Override
		driverX.whenPressed(new XboxMove());
		
		
	}

	public double readLeftStickX_Driver(){
		return driveCtrlr.getRawAxis(RobotMap.LEFT_STICK_AXIS_X);
	}
	
	public double readLeftTrigger_Driver(){
		return driveCtrlr.getRawAxis(RobotMap.LEFT_TRIGGER_AXIS);
	}
	
	public double readRightTrigger_Driver(){
		return driveCtrlr.getRawAxis(RobotMap.RIGHT_TRIGGER_AXIS);
	}
	
	//Feeder Up/Down
	public int getXboxLeftY_Operator(){
		double value = operatorCtrlr.getRawAxis(RobotMap.LEFT_STICK_AXIS_Y);
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
		double left  = operatorCtrlr.getRawAxis(RobotMap.LEFT_TRIGGER_AXIS);
		double right = operatorCtrlr.getRawAxis(RobotMap.RIGHT_TRIGGER_AXIS);
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
		return driveCtrlr.getRawButton(5);
	}
	
	//Brake
	public boolean getBrake_Driver(){
		return driveCtrlr.getRawButton(6);
	}
	
	//Turn In Place
	public boolean getTurnButton_Driver(){
		return driveCtrlr.getRawButton(9);
	}
	
	//Invert Drive
	public boolean getDriveInvertButton_Driver() {
		return driveCtrlr.getRawButton(2);
	}
	
	//Low gear
	public boolean getBack_Driver(){
		return driveCtrlr.getRawButton(7);
	}
	//High gear
	public boolean getStart_Driver(){
		return driveCtrlr.getRawButton(8);
	}
	
	}
	
