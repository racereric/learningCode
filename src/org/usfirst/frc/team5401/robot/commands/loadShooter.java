package org.usfirst.frc.team5401.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team5401.robot.Robot;
import org.usfirst.frc.team5401.robot.RobotMap;
import org.usfirst.frc.team5401.robot.subsystems.Loader;

/**
 *
 */
public class loadShooter extends Command {

    public loadShooter() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.loader);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.loader.switchState();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.loader.stopLoader();
    }
}
