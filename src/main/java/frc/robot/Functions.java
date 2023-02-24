package frc.robot;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.math.MathUtil;
import java.lang.Math;


// This file/class is intended to free Robot.java from a bunch of unnecessary functions.
// Imports in Java are kinda weird I think so this may not work. 


/**
 * Class used to clean up functions and functional variables from the main robot code.
 */
public class Functions {
  
  // MOTORS
  private static final MotorController LEFT_FRONT_MOTOR_CONTROLLER = new VictorSP(port_LeftFrontMotor);
  private static final MotorController LEFT_BACK_MOTOR_CONTROLLER = new VictorSP(port_LeftBackMotor);
  private static final MotorController RIGHT_FRONT_MOTOR_CONTROLLER = new VictorSP(port_RightFrontMotor);
  private static final MotorController RIGHT_BACK_MOTOR_CONTROLLER = new VictorSP(port_RightBackMotor);
  private static final MotorControllerGroup LEFT_MOTOR_CONTROLLERS = new MotorControllerGroup(LEFT_FRONT_MOTOR_CONTROLLER, LEFT_BACK_MOTOR_CONTROLLER);
  private static final MotorControllerGroup RIGHT_MOTOR_CONTROLLERS = new MotorControllerGroup(RIGHT_FRONT_MOTOR_CONTROLLER, RIGHT_BACK_MOTOR_CONTROLLER);
  private static final DifferentialDrive DRIVE_MOTORS = new DifferentialDrive(LEFT_MOTOR_CONTROLLERS, RIGHT_MOTOR_CONTROLLERS);

  // RESERVED MOTORS
  static final MotorController LEFT_ARM_MOTOR = new VictorSP(port_LeftArmMotor);
  static final MotorController RIGHT_ARM_MOTOR = new VictorSP(port_RightArmMotor); // Not private so it can be inversed on initialization
  // private static final MotorControllerGroup ARM_MOTORS = new MotorControllerGroup(LEFT_ARM_MOTOR, RIGHT_ARM_MOTOR);

  // PERIPHERALS
  private static final PneumaticHub  testPneumaticHub = new PneumaticHub();
  private static final PowerDistribution testPowerDistribution = new PowerDistribution();
  static final Compressor _Compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);

  // FUNCTIONAL VARIABLES
  private static boolean compressorEnabled = true;
  private static double totalArmSpeed = 0;

  /**
   * Wait for a specified amount of milliseconds. Might cause unforeseen errors, so ONLY USE IN TEST MODE. 
   * Also probably not a good idea to use it in periodic mode for anything.
   * Does not seem to work anymore without a try/catch clause.
   * @param ms The amount of milliseconds to wait.
   */
  public static void wait(int ms) {
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
   */
  public static void teleopDrive() {
    // Compressor toggle
    if (controller.getRawButtonPressed(bind_CompressorOff)) {
      if (_Compressor.isEnabled()) {_Compressor.disable();}
      else {_Compressor.enableDigital();}
    }

    // Drive with slowmode
    if (controller.getRawButton(bind_SlowMode)) {
      DRIVE_MOTORS.arcadeDrive(
      (MathUtil.applyDeadband(controller.getX(), deadBand.getDouble(0.2))
          * turnMultiplier.getDouble(0.575)) * slowModeMultiplier_Turn.getDouble(0.8), 

      (MathUtil.applyDeadband(controller.getY(), deadBand.getDouble(0.2)) 
          * driveMultiplier.getDouble(0.9)) * slowModeMultiplier_Drive.getDouble(0.5)
      );
    }
    else {
      DRIVE_MOTORS.arcadeDrive(
      MathUtil.applyDeadband(controller.getX(), deadBand.getDouble(0.2))
          * turnMultiplier.getDouble(0.575), 

      MathUtil.applyDeadband(controller.getY(), deadBand.getDouble(0.2)) 
          * driveMultiplier.getDouble(0.9)
      );
    }
  }

  /**
   * Clears all sticky faults (for the power distributor and pneumatics hub).
   * Should only be used if you know why there are sticky faults, or if you don't care.
   */
  public static void clearAllStickyFaults() {
    testPneumaticHub.clearStickyFaults();
    testPowerDistribution.clearStickyFaults();
  }

  /**
   * Toggles the automatic operation of the compressor (i.e. it turning on when psi is low).
   * <p> Use to enable and disable the compressor so that the Shuffleboard readout is accurate.
   */
  public static void compressorToggle() {
    if (_Compressor.isEnabled()) {_Compressor.disable(); compressorEnabled = false;}
    else {_Compressor.enableDigital(); compressorEnabled = true;}
  }
  
  /** 
   * Checks to see if the compressor is set to enable itself automatically when psi is low.
   */
  public static boolean isCompressorOn() {
    return compressorEnabled;
  }

  /**
   * Function used to operate the arm in its entirety. 
   * @param armDirection The direction the arm is going.
   * @param speed The speed the motors drive at.
   */
  public static void operateArm(ArmDirections armDirection, double speed) {
    // totalArmSpeed = Math.abs(LEFT_ARM_MOTOR.get()) + Math.abs(RIGHT_ARM_MOTOR.get());
    if (true) { // add limit switch check

      if (armDirection == ArmDirections.FORWARD) {
        LEFT_ARM_MOTOR.set(speed);
        RIGHT_ARM_MOTOR.set(speed);
      }
      switch(armDirection) {
      case FORWARD:
        LEFT_ARM_MOTOR.set(speed);
        RIGHT_ARM_MOTOR.set(speed);
      case BACK:
        LEFT_ARM_MOTOR.set(-speed);
        RIGHT_ARM_MOTOR.set(-speed);
      case TOGGLE:
        // figure out if this is necessary or needed
      case OFF:
        LEFT_ARM_MOTOR.set(0);
        RIGHT_ARM_MOTOR.set(0);
      }
    //} else {
      //LEFT_ARM_MOTOR.set(0);
      //RIGHT_ARM_MOTOR.set(0);
    }  
  }

}

