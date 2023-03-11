package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * Class used to store all "magic numbers" so that we don't have to go across the 7 lands to change one number.
 * Also stores port numbers (hopefully).
 * Some of these might not fit the definition of constant, but I don't care.
 */
public class Constants {
    private static ShuffleboardTab controlTab = Shuffleboard.getTab("Controls");

    // CONTROLS
    static GenericEntry turnMultiplier = 
    controlTab.addPersistent("Turn Multi", 0.575)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.1, "max", 1)).getEntry();

    static GenericEntry driveMultiplier = 
    controlTab.addPersistent("Drive Multi", 0.9)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.1, "max", 1)).getEntry();

    static GenericEntry deadBand = 
    controlTab.addPersistent("Deadband", 0.2)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();

    static GenericEntry slowModeMultiplier_Drive = 
    controlTab.addPersistent("Slow Drive Multi", 0.5)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.1, "max", 1)).getEntry();

    static GenericEntry slowModeMultiplier_Turn = 
    controlTab.addPersistent("Slow Turn Multi", 0.8)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.1, "max", 1)).getEntry();

    static GenericEntry armPivotSpeed = 
    controlTab.addPersistent("Arm Pivot Speed", 0.2)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0.1, "max", 1)).getEntry();

    static SendableChooser<autoMode> autoChooser = new SendableChooser<>();
    // static GenericEntry autoModeChooser = 
    // controlTab.add()


    // PORTS
    static int port_LeftFrontMotor = 1;
    static int port_LeftBackMotor = 0;
    static int port_RightFrontMotor = 2;
    static int port_RightBackMotor = 3;

    static int port_ArmPivotMotor = 9;

    static int port_SolenoidForward = 4;
    static int port_SolenoidBack = 2;

    // BINDS 
    static final Joystick controller = new Joystick(0);
    static int bind_SlowMode = 7;
    static int bind_CompressorToggle = 9;
    static int bind_ArmUp = 6;
    static int bind_ArmDown = 8;
    static int bind_ArmToggle = 0;
    static int bind_ArmStop = 5;

    // FUNCTIONAL VARIABLES
    enum autoMode {
        DEFAULT,
        TIMED_DRIVE_3_SECONDS,
        TIMED_DRIVE_5_SECONDS,
        TIMED_DRIVE_7_SECONDS,
        STAY_IN_PLACE,
        SCORE_AND_DRIVE,
        FOR_MIDDLE,
        
    }
}
