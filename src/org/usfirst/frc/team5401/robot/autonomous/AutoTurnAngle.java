package org.usfirst.frc.team5401.robot.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5401.robot.Robot;
import org.usfirst.frc.team5401.robot.commands.xboxMove;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * 
 */
public class AutoTurnAngle extends Command {
	
	private double desiredTurnAngle;
	private double currentAngle;
	private double initAngle;
	private boolean finished;
	
	//Constants
	private final double angleThreshold;
	private final double autoTurnSpeed;
	private final double autoTurnPrecision;
	private final boolean modeAuto;
	private final boolean modeAutoTarget;

    public AutoTurnAngle(double angle, boolean inAuto, boolean autoTarget) {
    	//Initialize Constants
    	angleThreshold	= 1; 		//Turn angle in degrees
    	autoTurnSpeed	= 0.95;
    	autoTurnPrecision = .5;
    	
    	modeAuto = inAuto;
    	modeAutoTarget = autoTarget;
    	
    	requires(Robot.driveBase);
    	
    	desiredTurnAngle = angle;
    	currentAngle = 0;
    	initAngle = 0;
    	finished = true;
    	System.out.println("AutoTurnAngle Constructed");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initAngle = Robot.driveBase.reportGyro();
    	currentAngle = 0;
    	
    	if (modeAutoTarget){
    		desiredTurnAngle = 90;
    	}
    	
//    	Robot.drivebase.recalibrateGyro(); 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Math.abs(desiredTurnAngle) <= angleThreshold){
    		//DesiredTurnAngle too small
    		System.out.println("AutoTurnAngle should stop1");
    		finished = true;
    	} else {
    		if (desiredTurnAngle > 0 && (currentAngle < Math.abs(desiredTurnAngle) - angleThreshold)){
    			Robot.driveBase.drive(-autoTurnSpeed * autoTurnPrecision, autoTurnSpeed * autoTurnPrecision);
    			finished = false;
    		} else if (desiredTurnAngle < 0 && (currentAngle > angleThreshold - Math.abs(desiredTurnAngle))) {
    			Robot.driveBase.drive(autoTurnSpeed * autoTurnPrecision, -autoTurnSpeed * autoTurnPrecision);
    			finished = false;
    		} else { //error or exactly 0
    			//Finished
    			finished = true;
    			System.out.println("AutoTurnAngle should stop2");
    		}
    	currentAngle = Robot.driveBase.reportGyro() - initAngle;
    	}
    	double angle = Robot.driveBase.reportGyro();
    	SmartDashboard.putNumber("Gyro Angle", currentAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (modeAuto) {	 //if in auto, stop motors
    		Robot.driveBase.Stop();
    	} else { //if in teleop, start xboxmove
    		Scheduler.getInstance().add(new xboxMove());
    	}
    	System.out.println("AutoTurnAngle end()");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveBase.Stop();
    	System.out.println("AutoTurnAngle Interrupted");
    }
}