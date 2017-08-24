package org.usfirst.frc.team5401.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import org.usfirst.frc.team5401.robot.RobotMap;

//import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;

/**
 *
 */
public class DriveBase extends Subsystem {
	
	double LOW_GEAR_LEFT_DPP;
	double LOW_GEAR_RIGHT_DPP;
	double HIGH_GEAR_LEFT_DPP;
	double HIGH_GEAR_RIGHT_DPP;
	double GYRO_OFFSET;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private VictorSP leftDrive1;
	private VictorSP leftDrive2;
	private VictorSP rightDrive1;
	private VictorSP rightDrive2;

	private DoubleSolenoid gearShift;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
//	AHRS gyro;
	
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
		
//		gyro = new AHRS(SerialPort.Port.kMXP);
//		gyro.reset();
	}
	
	public void drive(double leftDriveDesired, double rightDriveDesired){
		leftDrive1.set(leftDriveDesired);
		rightDrive1.set(-1* rightDriveDesired);
		leftDrive2.set(leftDriveDesired);
		rightDrive2.set(-1* rightDriveDesired);
	}
	
	public void Stop(){
		leftDrive1.stopMotor();
		leftDrive2.stopMotor();
		rightDrive1.stopMotor();
		rightDrive2.stopMotor();
	}
	
	public void shiftLowToHigh(){
		gearShift.set(DoubleSolenoid.Value.kForward);
		leftEncoder.setDistancePerPulse(HIGH_GEAR_LEFT_DPP);
		rightEncoder.setDistancePerPulse(HIGH_GEAR_RIGHT_DPP);
	}
	
	public void shiftHighToLow(){
		gearShift.set(DoubleSolenoid.Value.kReverse);
		leftEncoder.setDistancePerPulse(LOW_GEAR_LEFT_DPP);
		rightEncoder.setDistancePerPulse(LOW_GEAR_RIGHT_DPP);
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
		double rawDistLeft  = leftEncoder.get();
		double rawDistRight = rightEncoder.get();
		double leftDistance = leftEncoder.getDistance();
		double rightDistance = rightEncoder.getDistance();
		double encoderDistance = (leftDistance + rightDistance)/2;
		return encoderDistance;
	}
	
	public void resetEncoders(){
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
/*	public double reportGyro(){
		double currentAngle = gyro.getAngle();
	    double currentPitch = gyro.getPitch();
	    double currentRoll  = gyro.currentRoll();
	    return currentAngle;
	}
*/	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

