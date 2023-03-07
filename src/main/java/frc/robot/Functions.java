package frc.robot;

import static frc.robot.Constants.*;

import java.util.ArrayList;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import frc.robot.Constants.autoMode;

// This file/class is intended to free Robot.java from a bunch of unnecessary functions.

/**
 * Class used to clean up functions and functional variables from the main robot code.
 */
public class Functions {
  
  // DRIVE MOTORS
  private static final MotorController LEFT_FRONT_MOTOR_CONTROLLER = new VictorSP(port_LeftFrontMotor);
  private static final MotorController LEFT_BACK_MOTOR_CONTROLLER = new VictorSP(port_LeftBackMotor);
  private static final MotorController RIGHT_FRONT_MOTOR_CONTROLLER = new VictorSP(port_RightFrontMotor);
  private static final MotorController RIGHT_BACK_MOTOR_CONTROLLER = new VictorSP(port_RightBackMotor);
  private static final MotorControllerGroup LEFT_MOTOR_CONTROLLERS = new MotorControllerGroup(LEFT_FRONT_MOTOR_CONTROLLER, LEFT_BACK_MOTOR_CONTROLLER);
  private static final MotorControllerGroup RIGHT_MOTOR_CONTROLLERS = new MotorControllerGroup(RIGHT_FRONT_MOTOR_CONTROLLER, RIGHT_BACK_MOTOR_CONTROLLER);
  private static final DifferentialDrive DRIVE_MOTORS = new DifferentialDrive(LEFT_MOTOR_CONTROLLERS, RIGHT_MOTOR_CONTROLLERS);

  // PERIPHERAL MOTORS
  static final MotorController ARM_PIVOT_MOTOR = new VictorSP(port_ArmPivotMotor);

  // MISC PERIPHERALS
  private static final PneumaticHub  testPneumaticHub = new PneumaticHub();
  private static final PowerDistribution testPowerDistribution = new PowerDistribution();
  static final Compressor _compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);

  // FUNCTIONAL VARIABLES (that i don't want to put in constants)
  private static boolean compressorEnabled = true;
  private static Timer armTimer = new Timer();


  /**
   * Drives all motors in the direction the joystick is pointed when called.
   * Used mainly to clean up the code in Robot.java.
   * Also handles toggling the compressor.
   */
  public static void teleopDrive() {
    // Compressor toggle
    if (controller.getRawButtonPressed(bind_CompressorToggle)) {
      compressorToggle();
    }

   
    if (controller.getRawButton(bind_SlowMode)) {
      // Drive with slowmode multiplier applied
      DRIVE_MOTORS.arcadeDrive(
      (MathUtil.applyDeadband(controller.getX(), deadBand.getDouble(0.2))
          * turnMultiplier.getDouble(0.575)) * slowModeMultiplier_Turn.getDouble(0.8), 

      (MathUtil.applyDeadband(controller.getY(), deadBand.getDouble(0.2)) 
          * driveMultiplier.getDouble(0.9)) * slowModeMultiplier_Drive.getDouble(0.5)
      );
    } else {
      // Drive normally
      DRIVE_MOTORS.arcadeDrive(
      MathUtil.applyDeadband(controller.getX(), deadBand.getDouble(0.2))
          * turnMultiplier.getDouble(0.575), 

      MathUtil.applyDeadband(controller.getY(), deadBand.getDouble(0.2)) 
          * driveMultiplier.getDouble(0.9)
      );

    }
  }

  /**
   * Controls the robot during auto, with a selection of automatic modes. 
   * @param aMode The selected auto mode.
   */
  public static void autoDrive(autoMode aMode) {
    Timer autoTimer = new Timer();
    if (aMode == autoMode.DEFAULT || aMode == autoMode.TIMED_DRIVE ) {
      if (autoTimer.get() < 5) {
        DRIVE_MOTORS.arcadeDrive(0.35, 0);
      } else {
        DRIVE_MOTORS.stopMotor();
      }
    }
    
    if (aMode == autoMode.STAY_IN_PLACE) {
      // Do nothing
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
    if (_compressor.isEnabled()) {_compressor.disable(); compressorEnabled = false;}
    else {_compressor.enableDigital(); compressorEnabled = true;}
  }
  
  /** 
   * Checks to see if the compressor is set to enable itself automatically when psi is low.
   */
  public static boolean isCompressorOn() {
    return compressorEnabled;
  }

  /**
   * Tests each motor added to it (this needs to be done manually) to see if the motor both works and
   * gets set to the speed specified (or close to it). Be advised: this can throw a RuntimeException, so it is
   * best to use this outside of "competition code".
   * @param testSpeed The speed to set the motors to.
   * @param testTolerableDifference The tolerable difference between testSpeed and actual speed, both positive
   * and negative. 
   * @return True if motors pass. If one does not pass, it instead throws a RuntimeException.
   */
  public static boolean testMotors(double testSpeed, double testTolerableDifference) {
    // Create an ArrayList of MotorControllers, so that motors only need be added to the array to be checked
    ArrayList<MotorController> motors = new ArrayList<MotorController>();

    // List of motors checked
    motors.add(LEFT_FRONT_MOTOR_CONTROLLER);
    motors.add(LEFT_BACK_MOTOR_CONTROLLER);
    motors.add(RIGHT_FRONT_MOTOR_CONTROLLER);
    motors.add(RIGHT_BACK_MOTOR_CONTROLLER);
    motors.add(ARM_PIVOT_MOTOR);

    // Check each motor in the ArrayList
    for (int i = 0; i < motors.size(); i++) {
      motors.get(i).set(testSpeed);
      double actualSpeed = motors.get(i).get();

      // If motors are not set close to the speed we set them to, throw an error
      if (actualSpeed > (actualSpeed + testTolerableDifference) || actualSpeed < (actualSpeed - testTolerableDifference)) {
        motors.get(i).set(0);
        throw new RuntimeException("Motor number " + i + " failed during testing");
      }
      motors.get(i).set(0);
    }

    return true;
  }

  /**
   * Controls the pivot of the arm in each direction by 1 second increments.
   * @param speed The speed at which the arm pivots.
   */
  public static void pivotArm(double speed) {
    if (controller.getRawButtonPressed(bind_ArmUp)) {
      armTimer.reset();
      armTimer.start();
      ARM_PIVOT_MOTOR.set(speed);
    }
    else if (controller.getRawButtonPressed(bind_ArmDown)) {
      armTimer.reset();
      armTimer.start();
      ARM_PIVOT_MOTOR.set(speed);
    }
    if (controller.getRawButtonPressed(bind_ArmStop)) {
      ARM_PIVOT_MOTOR.stopMotor();
    }
    if (armTimer.get() > 1) {
      ARM_PIVOT_MOTOR.stopMotor();
      armTimer.reset();
    }
  }
}

