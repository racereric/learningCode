package org.usfirst.frc.team5401.robot.commands;

import org.usfirst.frc.team5401.robot.Robot;
import org.usfirst.frc.team5401.robot.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;

/*
 * TODO:
 * 	Fill out the command.
 */
public class RyanXboxMove extends Command {
	private final double MINIMUM_VELOCITY_FOR_HIGH_GEAR;
	private final double MAXIMUM_VELOCITY_FOR_LOW_GEAR;
	
	double velocitySample1;
	double velocitySample2;

    public RyanXboxMove() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.ryanbase);
    	velocitySample1 = 0;
        velocitySample2 = 0;
        
        MINIMUM_VELOCITY_FOR_HIGH_GEAR = 35;
        MAXIMUM_VELOCITY_FOR_LOW_GEAR = 45;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ryanbase.shiftGearHighToLow();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double angle = Robot.ryanbase.reportGyro();
    	SmartDashboard.putNumber("Gyro", angle);
    	Robot.ryanbase.reportGyro();
    	
    	double slew = Robot.oi.readXboxLeftX_Driver() * -1;
    	
    	double forward = Robot.oi.readRightTrigger_Driver();
    	double reverse = Robot.oi.readLeftTrigger_Driver();
    	boolean precision = Robot.oi.getPrecision_Driver();
    	boolean turn = Robot.oi.getTurnButton_Driver();
    	
    	boolean gearShiftLow = Robot.oi.getXboxBack_Driver();
    	boolean gearShiftHigh = Robot.oi.getXboxStart_Driver();
    	

    	
    	double right = 0, left = 0, sensitivity;
    	
    	if(precision){
    		sensitivity = RobotMap.DRIVE_SENSITIVITY_PRECISE;
        }
    	else {
    		sensitivity = RobotMap.DRIVE_SENSITIVITY_DEFAULT;
    	}
        if(!turn){
    		if (slew > RobotMap.DRIVE_THRESHHOLD){ //If Slew is positive (Thumbstick pushed right), go Right
    			left  = (forward - reverse) * sensitivity;					//Send Left full power
    			right = (forward - reverse) * sensitivity * (1 - slew);	//Send Right partial power, tempered by how hard the thumbstick is being pushed
    		} 
    		else if (slew < (-1 * RobotMap.DRIVE_THRESHHOLD)){ //If Slew is negative (Thumbstick pushed left), go Left
    			left  = (forward - reverse) * sensitivity * (1 + slew); //Send Left partial power, tempered by how hard thumbstick is being pushed left
    			right = (forward - reverse) * sensitivity; 			//Send right full power
    		} 
    		else { //Drive forward/back normally
    			left  = (forward - reverse) * sensitivity;
    			right = (forward - reverse) * sensitivity;
    		} 
        }
        else {
        		if (Math.abs(slew) > RobotMap.DRIVE_THRESHHOLD){
        			left  = RobotMap.DRIVE_SPIN_SENSITIVITY * slew;
        			right = RobotMap.DRIVE_SPIN_SENSITIVITY * slew * -1;
        		}
        }
    	
    	Robot.ryanbase.drive(left, right);
    	
    	Robot.ryanbase.getEncoderDistance();
    	
    	velocitySample2 = Robot.ryanbase.getRobotVelocity();
    }  
     

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ryanbase.stopDriveBase();
    	System.out.println("RyanXboxMove end()");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.ryanbase.stopDriveBase();
    	System.out.println("RyanXboxMove Interrupted");
    }
}
