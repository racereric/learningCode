package org.usfirst.frc.team5401.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team5401.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;

/**
 *
 */
public class CompressorSubsystem extends Subsystem {

	private Compressor compressor;
	
	public CompressorSubsystem(){
		compressor = new Compressor(RobotMap.PCM_ID);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void startCompressor(){
    	compressor.setClosedLoopControl(true);
    	compressor.start();
    }
    
    public void stopCompressor(){
    	compressor.stop();
    }
    
    public boolean isEnabled(){
    	return compressor.enabled();
    }
    
    public void switchState(){
    	if(isEnabled()){
    		stopCompressor();
    	}
    	
    	else {
    		startCompressor();
    	}
    }
}

