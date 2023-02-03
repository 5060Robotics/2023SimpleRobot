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
 */
public class Constants {
    private static ShuffleboardTab controlTab = Shuffleboard.getTab("Controls");
    private static ShuffleboardTab portTab = Shuffleboard.getTab("Ports");
    private static ShuffleboardTab bindTab = Shuffleboard.getTab("Binds");

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


    // Drive constants
    // static double turnMultiplier = 0.575;
    // static double driveMultiplier = 0.900;
    // static double deadBand = 0.2;
    // static double slowModeMultiplier_Drive = 0.5;
    // static double slowModeMultiplier_Turn = 0.8;

    // Ports
    static int port_LeftFrontMotor = 8;
    static int port_LeftBackMotor = 9;
    static int port_RightFrontMotor = 7;
    static int port_RightBackMotor = 6;
    static int port_MotorReserved1 = 0;
    static int port_MotorReserved2 = 0;
    static int port_DoubleSolenoidForward1 = 0;
    static int port_DoubleSolenoidBack1 = 0;

    // Binds 
    static final Joystick controller = new Joystick(0);
    static int bind_SlowMode = 7;
    static int bind_CompressorOff = 9;


    

}
