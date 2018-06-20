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
	double velocitySample1;
	double velocitySample2;

    public RyanXboxMove() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.ryanbase);
    	velocitySample1 = 0;
        velocitySample2 = 0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double angle = Robot.ryanbase.reportGyro();
    	SmartDashboard.putNumber("Gyro", angle);
    	Robot.ryanbase.reportGyro();
    	
    	double slew = Robot.oi.readXboxLeftX_Driver();
    	
    	double forward = Robot.oi.readRightTrigger_Driver();
    	double reverse = Robot.oi.readLeftTrigger_Driver();
    	boolean precision = Robot.oi.getPrecision_Driver();
    	boolean turn = Robot.oi.getTurnButton_Driver();
    	

    	
    	double right = 0, left = 0, sensitivity;
    	
    	if(precision){
    		sensitivity = RobotMap.DRIVE_SENSITIVITY_PRECISE;
        }
    	else {
    		sensitivity = RobotMap.DRIVE_SENSITIVITY_DEFAULT;
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
