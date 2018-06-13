package org.usfirst.frc.team5401.robot.subsystems;

import org.usfirst.frc.team5401.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.VictorSP;

import edu.wpi.first.wpilibj.Encoder;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

/**
 *
 */
public class RyanBase extends Subsystem {

    /*TODO:
     * 	Create 4 VictorSP Objects & it's parts
     * 	Create 1 NavX Gyro & it's parts
     * 	There will be NO Solenoid or DoubleSolenoid Objects
     * 	Create 2 Encoder Objects.
     * 	
     */
	
	double LOW_GEAR_LEFT_DPP;
	double LOW_GEAR_RIGHT_DPP;
	double HIGH_GEAR_LEFT_DPP;
	double HIGH_GEAR_RIGHT_DPP;
	
	private VictorSP leftDriveMotor1;
	private VictorSP rightDriveMotor1;
	private VictorSP leftDriveMotor2;
	private VictorSP rightDriveMotor2;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	AHRS navxGyro;
	
	public RyanBase() {
		
		LOW_GEAR_LEFT_DPP = -.0189249;
		LOW_GEAR_RIGHT_DPP = .0189249;
		
		leftDriveMotor1 = new VictorSP(RobotMap.LEFT_DRIVE_MOTOR_1);
		rightDriveMotor1 = new VictorSP(RobotMap.RIGHT_DRIVE_MOTOR_1);
		leftDriveMotor2 = new VictorSP(RobotMap.LEFT_DRIVE_MOTOR_2);
		rightDriveMotor2 = new VictorSP(RobotMap.RIGHT_DRIVE_MOTOR_2);
		
		rightEncoder = new Encoder(RobotMap.RIGHT_ENC_1, RobotMap.RIGHT_ENC_2, true, Encoder.EncodingType.k4X);
		leftEncoder = new Encoder(RobotMap.LEFT_ENC_1, RobotMap.LEFT_ENC_2, true, Encoder.EncodingType.k4X);
		
		navxGyro = new AHRS(SerialPort.Port.kMXP);
		navxGyro.reset();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void drive(double leftDriveDesired, double rightDriveDesired){
    leftDriveMotor1.set(leftDriveDesired);
    rightDriveMotor1.set(-1 * rightDriveDesired);
    leftDriveMotor2.set(leftDriveDesired);
    rightDriveMotor2.set(-1 * rightDriveDesired);
    
    SmartDashboard.putNumber("Left Encoder Raw", leftEncoder.get());
    SmartDashboard.putNumber("Right Encoder Raw", rightEncoder.get());
    SmartDashboard.putNumber("Left Encoder Adj", leftEncoder.getDistance());
    SmartDashboard.putNumber("Right Encoder Adj", rightEncoder.getDistance());
    SmartDashboard.putNumber("Mean Encoder Adj", getEncoderDistance());
    }
    
    public void stopDriveBase() {
    	leftDriveMotor1.set(0);
    	rightDriveMotor1.set(0);
    	leftDriveMotor2.set(0);
    	rightDriveMotor2.set(0);
    	SmartDashboard.putNumber("Robot Velocity", 0);
    }
    
    public double getRobotVelocity(){
    	double velocity = (Math.abs(rightEncoder.getRate()) + (Math.abs(leftEncoder.getRate()))) / 2;
    	SmartDashboard.putNumber("Robot Velocity", velocity);
    	return velocity;
    }
    
    public double getEncoderDistance(){
    	double leftEncRaw = leftEncoder.get();
    	double rightEncRaw = rightEncoder.get();
    	SmartDashboard.putNumber("Left Encoder Raw", leftEncRaw);
    	SmartDashboard.putNumber("Right Encoder Raw", rightEncRaw);
    	double leftEncAdj = leftEncoder.getDistance();
    	double rightEncAdj = rightEncoder.getDistance();
    	SmartDashboard.putNumber("Left Encoder Adj", leftEncAdj);
    	SmartDashboard.putNumber("Right Encoder Adj", rightEncAdj);
    	double encoderDistance = (leftEncAdj) + (rightEncAdj) / 2;
    	return encoderDistance;
    }
    
    public double reportGyro(){
    	double robotAngle = navxGyro.getAngle();
    	double robotPitch = navxGyro.getPitch();
    	double robotRoll  = navxGyro.getRoll();
    	SmartDashboard.putNumber("navx Angle", robotAngle);
    	SmartDashboard.putNumber("navx Pitch", robotPitch);
    	SmartDashboard.putNumber("navx Roll", robotRoll);
    	return robotAngle;
    	
    }
    
    public void resetEncoders(){
    	rightEncoder.reset();
    	leftEncoder.reset();
    }
  
}

