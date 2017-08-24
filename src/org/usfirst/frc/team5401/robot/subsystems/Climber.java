package org.usfirst.frc.team5401.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team5401.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;

/**
 *
 */
public class Climber extends Subsystem {
	
	private VictorSP climbMotor;
	
	private double SPEED;
	
	public Climber(){
		climbMotor = new VictorSP(RobotMap.CLIMBER_MOTOR);
		
		SPEED = .9;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void climbUp(){
    	climbMotor.set(SPEED);
    }
    
    public void climbStop(){
    	climbMotor.set(0);
    }
}

