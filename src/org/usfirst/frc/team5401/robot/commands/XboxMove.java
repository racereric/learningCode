package org.usfirst.frc.team5401.robot.commands;

import org.usfirst.frc.team5401.robot.Robot;
import org.usfirst.frc.team5401.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class XboxMove extends Command {
	
	private final double MINIMUM_VELOCITY_FOR_HIGH_GEAR; //Experimentally Determined, REMEMBER inches per second
	private final double MAXIMUM_VELOCITY_FOR_LOW_GEAR;
	
	double velocitySample1;
	double velocitySample2;

    public XboxMove() {
    	
		MINIMUM_VELOCITY_FOR_HIGH_GEAR 	= 35;// REMEMBER inches per second
		MAXIMUM_VELOCITY_FOR_LOW_GEAR 	= 45;
		
		velocitySample1 = 0;
		velocitySample2 = 0;
		
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		requires(Robot.driveBase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveBase.shiftHighToLow();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double slew = Robot.oi.readLeftStickX_Driver() * -1;
    	
    	double throttle = Robot.oi.readRightTrigger_Driver();
    	double reverse  = Robot.oi.readLeftTrigger_Driver();
        boolean turn    = Robot.oi.getTurnButton_Driver();
        boolean precision = Robot.oi.getPrecision_Driver();
    	boolean brake	  = Robot.oi.getBrake_Driver();
    	boolean invert	  = Robot.oi.getDriveInvertButton_Driver();
        
        boolean shiftLow = Robot.oi.getBack_Driver();
        boolean shiftHigh = Robot.oi.getStart_Driver();
        
        //Manual Gearing
        if(shiftHigh){
        	Robot.driveBase.shiftLowToHigh();
        }
        else if(shiftLow){
        	Robot.driveBase.shiftHighToLow();
        }
        	double right = 0, left = 0, sensitivity = 1;
        	
        	if (brake){
        		left  = 0;
        		right = 0;
        	} else if(!turn){
        		if (slew > RobotMap.DRIVE_THRESHHOLD){ //If Slew is positive (Thumbstick pushed right), go Right
        			left  = (throttle - reverse) * sensitivity;					//Send Left full power
        			right = (throttle - reverse) * sensitivity * (1 - slew);	//Send Right partial power, tempered by how hard the thumbstick is being pushed
        		} else if (slew < (-1 * RobotMap.DRIVE_THRESHHOLD)){ //If Slew is negative (Thumbstick pushed left), go Left
        			left  = (throttle - reverse) * sensitivity * (1 + slew); //Send Left partial power, tempered by how hard thumbstick is being pushed left
        			right = (throttle - reverse) * sensitivity; 			//Send right full power
        		} else { //Drive forward/back normally
        			left  = (throttle - reverse) * sensitivity;
        			right = (throttle - reverse) * sensitivity;
        		}
        	} else {
        		if (invert){
        			slew *= -1;
        		}
        		if (Math.abs(slew) > RobotMap.DRIVE_THRESHHOLD){
        			left  = RobotMap.DRIVE_SPIN_SENSITIVITY * slew;
        			right = RobotMap.DRIVE_SPIN_SENSITIVITY * slew * -1;
        		}
        	}
        	
        	
        	Robot.driveBase.drive(left, right);
        
        	
        	
        	
    /*****Shifting Gear Code*********/
        	Robot.driveBase.getEncDist();

        	//Gets new final velocity
        	velocitySample2 = Robot.driveBase.getVelocity();
        }
        
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveBase.Stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveBase.Stop();
    }
}
