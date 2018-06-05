package org.usfirst.frc.team5401.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team5401.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;

/**
 *
 */
public class PracticeClimber extends Subsystem {
	private VictorSP climberMotor;
	
	private double SPEED; 
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public PracticeClimber( ) {
		climberMotor = new VictorSP(RobotMap.PRACTICE_CLIMBER_MOTOR);
		SPEED = .9;
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void climbUp() {
    	climberMotor.set(SPEED);
    }
    public void climbStop() {
    	climberMotor.set(0);
    }
}

