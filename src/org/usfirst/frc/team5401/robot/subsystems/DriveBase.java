package org.usfirst.frc.team5401.robot.subsystems;

import org.usfirst.frc.team5401.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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
	double GYRO_OFFSET;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private VictorSP leftDrive1;
	private VictorSP rightDrive1;
	private VictorSP leftDrive2;
	private VictorSP rightDrive2;
	
	private DoubleSolenoid gearShifter;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	//private ADXRS450_Gyro gyro; <--- Unnecessary gyro.
	AHRS navxGyro;
	
	public DriveBase(){
		
		LOW_GEAR_LEFT_DPP  = -.0189249;
		LOW_GEAR_RIGHT_DPP = -.0189249;
		
		HIGH_GEAR_LEFT_DPP  = -.019423;
		HIGH_GEAR_RIGHT_DPP = -.019423;
		
		leftDrive1   = new VictorSP(RobotMap.DRIVE_LEFT_MOTOR_1);
		rightDrive1  = new VictorSP(RobotMap.DRIVE_RIGHT_MOTOR_1);
		leftDrive2   = new VictorSP(RobotMap.LEFT_MOTOR_2);
		rightDrive2  = new VictorSP(RobotMap.RIGHT_MOTOR_2);
		gearShifter  = new DoubleSolenoid(RobotMap.PCM_ID,RobotMap.SHIFTER_IN, RobotMap.SHIFTER_OUT);
		leftEncoder  = new Encoder(RobotMap.DRIVE_ENC_LEFT_A, RobotMap.DRIVE_ENC_LEFT_B, true, Encoder.EncodingType.k4X);
		rightEncoder = new Encoder(RobotMap.DRIVE_ENC_RIGHT_A, RobotMap.DRIVE_ENC_RIGHT_B, true, Encoder.EncodingType.k4X);
		//gyro         = new ADXRS450_Gyro();  <--- Unnecessary since we use navx gyro.
		navxGyro     = new AHRS(SerialPort.Port.kMXP);
		navxGyro.reset();
		
		SmartDashboard.putString("Trnasmission_text", "Transmission");
		SmartDashboard.putString("HighGear_text", "GREEN = High");
		SmartDashboard.putString("LowGear_text", "RED = Low");
		if ((DoubleSolenoid.Value.kForward).equals(gearShifter.get())){
			SmartDashboard.putNumber("Transmission", -1);
		} else {
			SmartDashboard.putNumber("Transmission", 1);
		}
		
		SmartDashboard.putNumber("Robot Velocity", 0);
		SmartDashboard.putNumber("Gyro", reportGyro());
		
		SmartDashboard.putNumber("Left Enc Raw", leftEncoder.get());
		SmartDashboard.putNumber("Right Enc Raw", rightEncoder.get());
		SmartDashboard.putNumber("Left Enc Adj", leftEncoder.getDistance());
		SmartDashboard.putNumber("Right Enc Adj", rightEncoder.getDistance());
		SmartDashboard.putNumber("Mean Enc Adj", getEncoderDistance());
		
	}
	
    

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    	
    	public void drive(double leftDriverDesired, double rightDriveDesired){
    		leftDrive1.set(leftDriveDesired);
    		rightDrive1.set(-1 * rightDriveDesired);
    		leftDrive2.set(leftDriveDesired);
    		rightDrive2.set(-1 * rightDriveDesired);
    		
    		SmartDashboard.putNumber("Left Enc Raw", leftEncoder.get());
    		SmartDashboard.putNumber("Right Enc Raw", rightEncoder.get());
    		SmartDashboard.putNumber("Left Enc Adj", leftEncoder.getDistance());
    		SmartDashboard.putNumber("Right Enc Adj", rightEncoder.getDistance());
    		SmartDashboard.putNumber("Mean Enc Adj", getEncoderDistance());
    		
    }
    	
    	public void stop(){
    		leftDrive1.set(0);
    		rightDrive1.set(0);
    		leftDrive2.set(0);
    		rightDrive2.set(0);
    		SmartDashboard.putNumber("Robot Velocity", 0);
    	}
    	
    	public void shiftGearLowToHigh(){
    		gearShifter.set(DoubleSolenoid.Value.kForward);
    		leftEncoder.setDistancePerPulse(HIGH_GEAR_LEFT_DPP);
    		rightEncoder.setDistancePerPulse(HIGH_GEAR_RIGHT_DPP);
    		SmartDashboard.putNumber("Transmission", -1);
    		System.out.println("Shifting Drive Gear to High Gear");
    		
    	}
    	
    	public void shiftGearHighToLow(){
    		gearShifter.set(DoubleSolenoid.Value.kReverse);
    		leftEncoder.setDistancePerPulse(LOW_GEAR_LEFT_DPP);
    		rightEncoder.setDistancePerPulse(LOW_GEAR_RIGHT_DPP);
    		SmartDashboard.putNumber("Transmission", 1);
    		System.out.println("Shifting Drive Gear to Low Gear");
    	}
    	
    	public double getVelocityOfRobot(){
    		double velocity = (Math.abs(leftEncoder.getRate()) + Math.abs(rightEncoder.getRate()))/2;
    		SmartDashboard.putNumber("Robot Velocity", velocity);
    		return velocity;
    		
    	}
    	
    	public void setDPPLowGear(){
    		leftEncoder.setDistancePerPulse(LOW_GEAR_LEFT_DPP);
    		rightEncoder.setDistancePerPulse(LOW_GEAR_RIGHT_DPP);
    	}
    	
    	
    	public void setDPPHighGear(){
    		leftEncoder.setDistancePerPulse(HIGH_GEAR_LEFT_DPP);
    		rightEncoder.setDistancePerPulse(HIGH_GEAR_RIGHT_DPP);
    	}
    	
    	public double getEncoderDistance(){
    		double leftDistanceRaw = leftEncoder.get();
    		double rightDistanceRaw = rightEncoder.get();
    		SmartDashboard.putNumber("Left Enc Raw", leftDistanceRaw);
    		SmartDashboard.putNumber("Right Enc Raw", rightDistanceRaw);
    		double leftDistance = leftEncoder.getDistance();
    		double rightDistance = rightEncoder.getDistance();
    		SmartDashboard.putNumber("Left Enc Adj", leftDistance);
    		SmartDashboard.putNumber("Right Enc Adj", rightDistance);
    		double encoderDistance = (leftDistance + rightDistance)/2;
    		return encoderDistance;
    	}
    	
    	public void encoderReset(){
    		leftEncoder.reset();
    		rightEncoder.reset();
    	}
    	    	
    	public double reportGyro () {
    		double currentAngle = navxGyro.getAngle();
    		double currentPitch = navxGyro.getPitch();
    		double currentRoll  = navxGyro.getRoll();
    		SmartDashboard.putNumber("navx Angle", currentAngle);
    		SmartDashboard.putNumber("navx Pitch", currentPitch);
    		SmartDashboard.putNumber("navx Roll", currentRoll);
    		return currentAngle;
    }
    	

    	
    		
    		
    	}


