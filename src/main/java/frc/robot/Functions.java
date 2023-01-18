package frc.robot;

// This file/class is intended to free Robot.java from a bunch of functions that don't need to be there like wait from last year.
// Imports in Java are kinda weird I think so this may not work. 


/**
 * Class used to clean up functions from the main robot code.
 */
public class Functions {
    
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










}
