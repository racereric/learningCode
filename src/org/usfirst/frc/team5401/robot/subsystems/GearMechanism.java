package org.usfirst.frc.team5401.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.usfirst.frc.team5401.robot.RobotMap;
//import org.usfirst.frc.team5401.robot.commands.PopGear;

/**
 *
 */
public class GearMechanism extends Subsystem {

	private DoubleSolenoid gearPop;
	
	private boolean enabled;
	
	public GearMechanism(){
		gearPop = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.GEAR_DOOR_CLOSE, RobotMap.GEAR_DOOR_OPEN);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
//    	setDefaultCommand(new PopGear());
    }
    
    public void gearInOut(int direction){
    	if (direction == 1) {
    		gearPop.set(DoubleSolenoid.Value.kForward);
    		SmartDashboard.putNumber("Gear Mechanism", 1); //Gear Mechanism is in
    	} else if (direction == -1) {
    		gearPop.set(DoubleSolenoid.Value.kReverse);
    		SmartDashboard.putNumber("Gear Mechanism", -1); //Gear Mechanism is out
    	}
  
}
}

