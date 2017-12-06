package org.usfirst.frc.team5401.robot.subsystems;

import org.usfirst.frc.team5401.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
//import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveBase extends Subsystem {
	
	double LOW_GEAR_LEFT_DPP;
	double LOW_GEAR_RIGHT_DPP;
	double HIGH_GEAR_LEFT_DPP;
	double HIGH_GEAR_RIGHT_DPP;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private VictorSP leftDrive1;
	private VictorSP leftDrive2;
	private VictorSP rightDrive1;
	private VictorSP rightDrive2;

	private DoubleSolenoid gearShift;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	AHRS navxGyro;
	
	public DriveBase(){
		LOW_GEAR_LEFT_DPP  = -.0189249;
		LOW_GEAR_RIGHT_DPP = -.0189249;
		
		
		leftDrive1  = new VictorSP(RobotMap.LEFT_DRIVE_MOTOR_1);
		leftDrive2  = new VictorSP(RobotMap.LEFT_DRIVE_MOTOR_2);
		rightDrive1 = new VictorSP(RobotMap.RIGHT_DRIVE_MOTOR_1);
		rightDrive2 = new VictorSP(RobotMap.RIGHT_DRIVE_MOTOR_2);
		gearShift   = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.SHIFTER_IN, RobotMap.SHIFTER_OUT);
		leftEncoder = new Encoder(RobotMap.LEFT_ENC_A, RobotMap.LEFT_ENC_B, true, Encoder.EncodingType.k4X);
		rightEncoder = new Encoder(RobotMap.RIGHT_ENC_A, RobotMap.RIGHT_ENC_B, true, Encoder.EncodingType.k4X);
		
		navxGyro = new AHRS(SerialPort.Port.kMXP);
		navxGyro.reset();
		
		SmartDashboard.putString("Transmission_text", "Transmission");
		SmartDashboard.putString("HighGear_text", "GREEN = High");
		SmartDashboard.putString("LowGear_text" , "RED = Low");
		if ((DoubleSolenoid.Value.kForward).equals(gearShift.get())){
			SmartDashboard.putNumber("Transmission", -1); //Transmission is High
		} else {
			SmartDashboard.putNumber("Transmission", 1); //Transmission is Low
		}
		
		SmartDashboard.putNumber("Robot Velocity", 0);
		SmartDashboard.putNumber("Gyro", reportGyro());
		
		SmartDashboard.putNumber("Left Enc Raw" , leftEncoder.get());
		SmartDashboard.putNumber("Right Enc Raw", rightEncoder.get());
		SmartDashboard.putNumber("Left Enc Adj" , leftEncoder.getDistance());
		SmartDashboard.putNumber("Right Enc Adj", rightEncoder.getDistance());
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	public void drive(double leftDriveDesired, double rightDriveDesired){
		leftDrive1.set(leftDriveDesired);
		rightDrive1.set(-1* rightDriveDesired);
		leftDrive2.set(leftDriveDesired);
		rightDrive2.set(-1* rightDriveDesired);
		
    	SmartDashboard.putNumber("Left Enc Raw" , leftEncoder.get());
		SmartDashboard.putNumber("Right Enc Raw", rightEncoder.get());
		SmartDashboard.putNumber("Left Enc Adj" , leftEncoder.getDistance());
		SmartDashboard.putNumber("Right Enc Adj", rightEncoder.getDistance());
	}
	
	public void Stop(){
		leftDrive1.set(0);
		leftDrive2.set(0);
		rightDrive1.set(0);
		rightDrive2.set(0);
	}
	
	public void shiftLowToHigh(){
		gearShift.set(DoubleSolenoid.Value.kForward);
		leftEncoder.setDistancePerPulse(HIGH_GEAR_LEFT_DPP);
		rightEncoder.setDistancePerPulse(HIGH_GEAR_RIGHT_DPP);
    	SmartDashboard.putNumber("Transmission", -1);
	}
	
	public void shiftHighToLow(){
		gearShift.set(DoubleSolenoid.Value.kReverse);
		leftEncoder.setDistancePerPulse(LOW_GEAR_LEFT_DPP);
		rightEncoder.setDistancePerPulse(LOW_GEAR_RIGHT_DPP);
    	SmartDashboard.putNumber("Transmission", 1);
	}
	
	public double getVelocity(){
		double velocity = (Math.abs(leftEncoder.getRate()) + Math.abs(rightEncoder.getRate()))/2;
		return velocity;
	}
	
	public void setDPPLow(){
		leftEncoder.setDistancePerPulse(LOW_GEAR_LEFT_DPP);
		rightEncoder.setDistancePerPulse(LOW_GEAR_RIGHT_DPP);
	}
	
	public void setDPPHigh(){
		leftEncoder.setDistancePerPulse(HIGH_GEAR_LEFT_DPP);
		rightEncoder.setDistancePerPulse(HIGH_GEAR_RIGHT_DPP);
	}
	
	public double getEncDist(){
    	double leftDistanceRaw = -1* leftEncoder.get();
    	double rightDistanceRaw = rightEncoder.get();
    	SmartDashboard.putNumber("Left Enc Raw", leftDistanceRaw);
    	SmartDashboard.putNumber("Right Enc Raw", rightDistanceRaw);
    	double leftDistance = leftEncoder.getDistance();
    	double rightDistance = -1* rightEncoder.getDistance();
    	SmartDashboard.putNumber("Left Enc Adj", leftDistance);
    	SmartDashboard.putNumber("Right Enc Adj", rightDistance);
    	double encoderDistance = (leftDistance + rightDistance)/2;
    	return encoderDistance;
	}
	
	public void resetEncoders(){
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public double reportGyro(){
		double currentAngle = navxGyro.getAngle();
	    double currentPitch = navxGyro.getPitch();
	    double currentRoll  = navxGyro.getRoll();
	    return currentAngle;
	}
	
}

