package org.usfirst.frc.team6083.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class core_code {
    static Spark motor1 = new Spark(0);
    static Spark motor2 = new Spark(1);
    static SmartDashboard dash = new SmartDashboard();
    static ADXRS450_Gyro Gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
    static double angle=0,x=0.01,curr=0;
    static double error_range=3,max_speed=0.25;
    
    public static void init(){
        Gyro.reset();
        Gyro.calibrate();
        SmartDashboard.putNumber("angle", 0);
        SmartDashboard.putNumber("x", x);
        SmartDashboard.putNumber("error_range", error_range);
        SmartDashboard.putNumber("max_speed",max_speed);
    }
    public static void rotate(double to){
    	error_range = SmartDashboard.getNumber("error_range");
    	max_speed = SmartDashboard.getNumber("max_speed");
    	to = curr+to;
    	do{
    		
    	}while(loop(to));
    	
    	curr = to;
    	
    }
    private static boolean loop(double to){
    	//left is - right is +
    	boolean re = true;
        angle = Gyro.getAngle()-to;
        x = SmartDashboard.getNumber("x");
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
        
        if(angle<=-error_range && angle >=-(360-error_range)){
        	if(angle*x <=-max_speed){
        		motor2.set(-max_speed);//-  turn right
        		motor1.set(-max_speed);
        	}//limit the speed
        	else{
        		motor2.set(angle*x);
            	motor1.set(angle*x);
        	}
        }
        else if(angle >=error_range && angle <=(360-error_range)){
        	if(angle*x >= max_speed){
        		motor1.set(max_speed);
        		motor2.set(max_speed);
        	}//limit the speed
        	else{
        	motor1.set(angle*x);
        	motor2.set(angle*x);
        	}
        }
        else{
        	motor1.set(0);
        	motor2.set(0);
        	re = false;
        }
        
        SmartDashboard.putNumber("angle", angle);
        return re;
    }
    
    private static void dashbaord(){
    	
    }
}
