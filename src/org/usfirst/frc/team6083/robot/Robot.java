
package org.usfirst.frc.team6083.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    Spark motor1=new Spark(0);
    Spark motor2=new Spark(1);
    SmartDashboard dash =new SmartDashboard();
    ADXRS450_Gyro Gyro=new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
    double angle=0,x=0,to=0;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        Gyro.reset();
        Gyro.calibrate();
        SmartDashboard.putNumber("angle", 0);
        SmartDashboard.putNumber("x", 0);
        SmartDashboard.putNumber("to", to);
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	to = SmartDashboard.getNumber("to");//left is - right is +
        angle=Gyro.getAngle()-to;
        x=SmartDashboard.getNumber("x");
        if(angle >=360){
        	do{
        		angle=angle-360;
        	}while(angle>0);
        }
        else if(angle <=-360){
        	do{
        		angle=angle+360;
        	}while(angle<0);
        }//make the error angle not exceed 360
        
        if(angle<=-5 && angle >=-355){
        	if(angle*x <=-0.3){
        		motor2.set(-0.3);//-  turn right
        		motor1.set(-0.3);
        	}//limit the speed
        	else{
        		motor2.set(angle*x);
            	motor1.set(angle*x);
        	}
        }
        else if(angle >=5 && angle <=355){
        	if(angle*x >= 0.3){
        		motor1.set(0.3);
        		motor2.set(0.3);
        	}//limit the speed
        	else{
        	motor1.set(angle*x);
        	motor2.set(angle*x);
        	}
        }
        else{
        	motor1.set(0);
        	motor2.set(0);
        }
        
        SmartDashboard.putNumber("angle", angle);
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
