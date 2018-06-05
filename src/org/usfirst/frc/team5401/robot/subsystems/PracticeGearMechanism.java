package org.usfirst.frc.team5401.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team5401.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 *
 */
public class PracticeGearMechanism extends Subsystem {
	private DoubleSolenoid openClose;
	public PracticeGearMechanism(){
		openClose = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.GEAR_DOOR_CLOSE, RobotMap.GEAR_DOOR_OPEN);
		
	}
	

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void gearInOut(int direction) {
    	if (direction == 1) {
    		openClose.set(DoubleSolenoid.Value.kForward);
    	}
    	else if (direction == 0) {
    		openClose.set(DoubleSolenoid.Value.kReverse);
    	}
    }
}

