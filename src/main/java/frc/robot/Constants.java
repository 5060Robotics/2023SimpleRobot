package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

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

    static GenericEntry armMotorSpeed = 
    controlTab.addPersistent("Arm Motor Speed", 0.25)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();

    // DRIVE CONSTANTS
    // static double turnMultiplier = 0.575;
    // static double driveMultiplier = 0.900;
    // static double deadBand = 0.2;
    // static double slowModeMultiplier_Drive = 0.5;
    // static double slowModeMultiplier_Turn = 0.8;

    // PORTS
    static int port_LeftFrontMotor = 8;
    static int port_LeftBackMotor = 9;
    static int port_RightFrontMotor = 7;
    static int port_RightBackMotor = 6;

    static int port_LeftArmMotor = 1;
    static int port_RightArmMotor = 0;

    static int port_DoubleSolenoidForward1 = 0;
    static int port_DoubleSolenoidBack1 = 0;

    // BINDS 
    static final Joystick controller = new Joystick(0);
    static int bind_SlowMode = 7;
    static int bind_CompressorToggle = 9;
    static int bind_ArmForward = 6;
    static int bind_ArmBack = 8;
    static int bind_ArmToggle = 0;
    static int bind_ArmOff = 10;

    // FUNCTIONAL VARIABLES
    enum ArmDirections {
        FORWARD,
        BACK,
        OFF
    }
}
