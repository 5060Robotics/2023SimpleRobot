// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;
import static frc.robot.Functions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    CameraServer.startAutomaticCapture();
    CameraServer.startAutomaticCapture();

    autoChooser.setDefaultOption("Default", autoMode.DEFAULT);
    autoChooser.addOption("Timed Drive (3 seconds)", autoMode.TIMED_DRIVE_3_SECONDS);
    autoChooser.addOption("Timed Drive (5 seconds)", autoMode.TIMED_DRIVE_5_SECONDS);
    autoChooser.addOption("Timed Drive (7 seconds)", autoMode.TIMED_DRIVE_7_SECONDS);
    autoChooser.addOption("Stay in Place", autoMode.STAY_IN_PLACE);
    autoChooser.addOption("Score and Drive Backwards", autoMode.SCORE_AND_DRIVE);
    autoChooser.addOption("middle", autoMode.FOR_MIDDLE);
    SmartDashboard.putData("Auto Chooser", autoChooser);

    
    // Janky solution for trying to find out what code is deployed - doesn't work too well.
    LocalDateTime currentTime = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
    String formattedTime = currentTime.format(dateTimeFormatter);

    SmartDashboard.putString("Build", formattedTime);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putBoolean("Compressor running?", _compressor.isEnabled());
    SmartDashboard.putBoolean("Autonomous?", isAutonomous());
    SmartDashboard.putBoolean("Compressor enabled?", isCompressorOn());

  }

  @Override
  public void autonomousInit() {
    autoStart = false;
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    autoDrive(autoChooser.getSelected());
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() 
  {
    if (controller.getRawButtonPressed(port_SolenoidBack) ) {
      if (armSolenoid.get() == Value.kOff) {
        armSolenoid.set(Value.kForward);
      } else {
        armSolenoid.toggle();
      }
    }
    pivotArm(armPivotSpeed.getDouble(0.2));
    teleopDrive();

  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    ARM_PIVOT_MOTOR.set(0.2);
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {

  }
}
