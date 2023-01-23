package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Class used to store all "magic numbers" so that we don't have to go across the 7 lands to change one number.
 * Also stores port numbers (hopefully).
 */
public class Constants {
    static double turnMultiplier = -0.575;
    static double driveMultiplier = 0.900;

    static int port_LeftFrontMotor = 8;
    static int port_LeftBackMotor = 9;
    static int port_RightFrontMotor = 7;
    static int port_RightBackMotor = 6;

    static int port_MotorReserved1 = 0;
    static int port_MotorReserved2 = 0;
    
    static int port_DoubleSolenoidForward1 = 0;
    static int port_DoubleSolenoidBack1 = 0;

    static final Joystick controller = new Joystick(0);
    

}
