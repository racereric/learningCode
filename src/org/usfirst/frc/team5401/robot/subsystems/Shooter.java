package org.usfirst.frc.team5401.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 *
 */
public class Shooter extends Subsystem {

	//Declare Speed Controller
	TalonSRX _talonMaster;
	TalonSRX _talonSlave;
	
	private double VOLTAGE_MOTOR_SPEED = -7.8;
	private double PID_MOTOR_SPEED = -19900;
	private double MOTOR_SPEED = PID_MOTOR_SPEED;
	private double feedForward;
	
	private double kP, kI, kD; //PID variables
	
	private int Izone;
	private double rampRate;
	private int channel;
	private boolean compressorEnabled;
	private boolean PIDenabled;
	private int THRESH;
	
	public Shooter(){
		_talonMaster = new TalonSRX(0);
		_talonSlave = new TalonSRX(1);
		
		_talonMaster.set(ControlMode.Velocity);
		_talonSlave.changeControlMode(TalonSRX.ControlMode.Follower);
		_talonSlave.set(_talonMaster.getDeviceID());
		
		SmartDashboard.putNumber("Motor Speed", MOTOR_SPEED);
		SmartDashboard.putBoolean("Auto Targeting", false);
		
		_talonMaster.getEncPosition();
		SmartDashboard.putNumber("Position", _talonMaster.getEncPosition());
		_talonMaster.getEncVelocity();
		SmartDashboard.putNumber("Velocity", _talonMaster.getEncVelocity());
		SmartDashboard.putNumber("Velocity Raw", _talonMaster.getEncVelocity());
		_talonMaster.setEncPosition(0);
		
		
		feedForward = .033;
		
		kP = .1;
		kI = .000005;
		kD = 2;
		
		Izone = 0;
		rampRate = 10.23;
		channel = 0;
		
		PIDenabled = true;
		
		SmartDashboard.putBoolean("PID Enabled", PIDenabled);
		SmartDashboard.putNumber("P Interval", kP);
		SmartDashboard.putNumber("I Interval", kI);
		SmartDashboard.putNumber("D Interval", kD);
		SmartDashboard.putNumber("Ramp Rate", rampRate);
		
		 _talonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		 
		 THRESH = 200;
		 
		 reset();
	    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void startMotors(){
    	if(PIDenabled){
    		_talonMaster.changeControlMode(ControlMode.Speed);
    		System.out.print("Mode: Speed");
    	}
    	else {
    		_talonMaster.changeControlMode(ControlMode.Voltage);
    		System.out.print("Mode: Voltage");    		
    	}
    	
    	SmartDashboard.putNumber("Feed Forward Test", feedForward);
    	
    	
    	_talonMaster.setPID(kP, kI, kD, feedForward, Izone, rampRate, channel);
    	_talonMaster.set(MOTOR_SPEED);
    	  SmartDashboard.putNumber("Position", _talonMaster.getEncPosition());
    	  SmartDashboard.putNumber("Velocity", _talonMaster.getEncVelocity());
    	  SmartDashboard.putNumber("Velocity Raw:", _talonMaster.getEncVelocity());
    	  
    	compressorEnabled = true;
    }
    
    public void reset(){
    	stop();
    	SmartDashboard.putBoolean("Auto Targeting", false);
    	SmartDashboard.putBoolean("Shooter On/Off", false);
    }
    
    public void stop(){
    	_talonMaster.changeControlMode(ControlMode.PercentVbus);
    	_talonMaster.set(0);
    	compressorEnabled = false;
    }
    
    public double getTargetSpeed(){
    	return MOTOR_SPEED;
    }
    
    public double getVelocity(){
    	return _talonMaster.getEncVelocity();
    }
    
    public boolean isEnabled(){
    	return compressorEnabled;
    }
    
    public void switchState(){
    	if(compressorEnabled){
    		reset();
    	}
    	else{
    		startMotors();
    	}
    }
    
    public void overrideShooter(){
    	if(PIDenabled){
    		_talonMaster.changeControlMode(ControlMode.PercentOutput);
    		MOTOR_SPEED = VOLTAGE_MOTOR_SPEED;
    		PIDenabled = false;
    		System.out.println("Switched to Voltage");
    	}
    	else{
    		_talonMaster.changeControlMode(ControlMode.Velocity);
    		MOTOR_SPEED = PID_MOTOR_SPEED;
    		PIDenabled = true;
    		System.out.println("Switched to Speed");
    	}
    	
    	SmartDashboard.putBoolean("PID Enabled", PIDenabled);
    }
    	
    public void printReadyToShoot(){
    		if(_talonMaster.getQuadratureVelocity() < MOTOR_SPEED + THRESH || _talonMaster.getEncVelocity() > MOTOR_SPEED - THRESH){
    			SmartDashboard.putBoolean("Ready to Shoot", true);
    		}
    		else{
    			SmartDashboard.putBoolean("Ready to Shoot", false);
    		}
    	}
    }


