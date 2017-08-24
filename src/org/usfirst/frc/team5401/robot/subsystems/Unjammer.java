package org.usfirst.frc.team5401.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.usfirst.frc.team5401.robot.RobotMap;

/**
 *
 */
public class Unjammer extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private DoubleSolenoid unjam;
	
	public Unjammer(){
		unjam = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.UNJAM_IN, RobotMap.UNJAM_OUT);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void unjamOut(){
    	unjam.set(DoubleSolenoid.Value.kForward);
    }
    
    public void unjamIn(){
    	unjam.set(DoubleSolenoid.Value.kReverse);
    }
}

