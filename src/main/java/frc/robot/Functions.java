package frc.robot;

import static frc.robot.Constants.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.math.MathUtil;


// This file/class is intended to free Robot.java from a bunch of functions that don't need to be there like wait from last year.
// Imports in Java are kinda weird I think so this may not work. 


/**
 * Class used to clean up functions from the main robot code.
 */
public class Functions {
    
  private static final MotorController LEFT_FRONT_MOTOR_CONTROLLER = new VictorSP(port_LeftFrontMotor);
  private static final MotorController LEFT_BACK_MOTOR_CONTROLLER = new VictorSP(port_LeftBackMotor);
  private static final MotorController RIGHT_FRONT_MOTOR_CONTROLLER = new VictorSP(port_RightFrontMotor);
  private static final MotorController RIGHT_BACK_MOTOR_CONTROLLER = new VictorSP(port_RightBackMotor);


  private static final MotorControllerGroup LEFT_MOTOR_CONTROLLERS = new MotorControllerGroup(LEFT_FRONT_MOTOR_CONTROLLER, LEFT_BACK_MOTOR_CONTROLLER);
  private static final MotorControllerGroup RIGHT_MOTOR_CONTROLLERS = new MotorControllerGroup(RIGHT_FRONT_MOTOR_CONTROLLER, RIGHT_BACK_MOTOR_CONTROLLER);

  static final DifferentialDrive DRIVE_MOTORS = new DifferentialDrive(LEFT_MOTOR_CONTROLLERS, RIGHT_MOTOR_CONTROLLERS);



  /**
   * Wait for a specified amount of milliseconds. Might cause unforeseen errors, so ONLY USE IN TEST MODE. 
   * Also probably not a good idea to use it in periodic mode for anything.
   * @param ms The amount of milliseconds to wait.
   */
  public static void wait(int ms)
  {
    try
    {
        Thread.sleep(ms);
    }
    catch(InterruptedException ex)
    {
        Thread.currentThread().interrupt();
    }
  }

  /**
   * Drives all motors in the direction the joystick is pointed when called.
   * Used mainly to clean up the code in Robot.java.
   * @param DriveMode The drive mode. 0 for arcade drive, 1 for tank drive,
   */
  public static void Teleop_Drive(int DriveMode) 
  {
    DRIVE_MOTORS.arcadeDrive(
      MathUtil.applyDeadband(controller.getX(), 0.4) * -turnMultiplier, 
      driveMultiplier * controller.getY());
  }
    // if (DriveMode == 1) {
    //   DRIVE_MOTORS.tankDrive(-turnMultiplier * controller.getX(), driveMultiplier * controller.getY());
    // }
    
  }

