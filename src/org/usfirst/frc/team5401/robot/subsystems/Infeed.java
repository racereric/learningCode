package org.usfirst.frc.team5401.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;

import org.usfirst.frc.team5401.robot.RobotMap;
//import org.usfirst.frc.team5401.robot.commands.feederControl;

/**
 *
 */
public class Infeed extends Subsystem {
	
	private DoubleSolenoid feederArm;
	private VictorSP feederBar;
	
	private double FEED_SPEED;
	
	private AnalogInput pressureSensor;
	private double inputVoltage;
	private final static double DEFAULT_VOLTS = 5.0;
//	private final int SLOPE                   = 250;
//	private final int Y_INTERCEPT             = -20;

	public Infeed(){
		feederArm = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.INFEED_IN, RobotMap.INFEED_OUT);
		feederBar = new VictorSP(RobotMap.INFEEDER_BAR);
		
		FEED_SPEED = 0.9;
		
		pressureSensor = new AnalogInput(RobotMap.PRESSURE_SENSOR);
		inputVoltage = DEFAULT_VOLTS;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//setDefaultCommand(new FeederControl());
    }
    
    public void feedDirection(int direction){
    	feederBar.set(FEED_SPEED * direction);
    }
    
    public void armUpDown(int direction){
    	if(direction == 1){
    		feederArm.set(DoubleSolenoid.Value.kForward);
    	}
    	else if(direction == -1){
    		feederArm.set(DoubleSolenoid.Value.kReverse);
    	}
    }
}

