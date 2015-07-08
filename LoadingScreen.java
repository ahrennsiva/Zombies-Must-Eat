import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The loading screen before the simulation is run
 * 
 * @author Jasper Tu
 * @version Oct 2014
 */
public class LoadingScreen extends World
{
    private int counter = 0;  
    private boolean exists = true;      
    /**
     * Constructor for objects of class LoadingScreen.
     * 
     */
    public LoadingScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1);         
    }

    public void act()
    {
        if(exists == true)
        {  
            if(counter < 300)  
            {  
                counter++;  
            }  
            else 
            {  
                Greenfoot.setWorld(new ZombieWorld());  
                exists = false;  
            }  
        }  
    }
    
//     /**
//      * Starts playing the theme of the zombie simulation.
//      */
//     public void started()  
//     {  
//         theme.playLoop();  
//     }
//     
//     /**
//      * Stops playing the theme when the simulation is not running
//      */
//     public void stopped()
//     {
//         theme.pause();
//     }
}
