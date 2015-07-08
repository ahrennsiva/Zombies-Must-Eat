import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The start menu of the zombie simulation.
 * 
 * @author Jasper Tu
 * @version Oct 2014
 */
public class StartScreen extends World
{   
    
    /**
     * Constructor for objects of class StartScreen.
     * 
     */
    public StartScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        // Prepares the world with the start screen button.
        prepare();
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        // Generate instance of start screen button.
        StartScreenButton startscreenbutton = new StartScreenButton();
        // Add the start screen button object to the start screen world.
        addObject(startscreenbutton, 661, 545);
        // Set the location of the start screen button wherever I want it to be. 
        startscreenbutton.setLocation(628, 554);
    }

    /**
     * Starts playing the opening theme of the zombie simulation.
     */
    public void started()  
    {  
        
    }
    
    /**
     * Stops playing the theme when the simulation is not running
     */
    public void stopped()
    {
        
    }
    
    
}