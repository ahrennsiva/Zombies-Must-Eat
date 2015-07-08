import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class for the click button of the simulation's start screen; a child class of the parent Button class.
 * 
 * @author Jaster Tu, Ahrenn Sivananthan
 * @version Oct 2014
 */
public class StartScreenButton extends Buttons
{
    // Declaration of instance variables.
    private GreenfootImage button;
    private GreenfootImage buttonHover;
    private StartScreen myWorld;

    /**
     * Constructor for the "StartScreenButton" class.
     */
    public StartScreenButton() 
    {
        // Import pictures into Greenfoot.
        button = new GreenfootImage("Start Screen Button.png");     
        buttonHover = new GreenfootImage("Start Screen Hover.png");
        button.setTransparency(200);
        setImage(button);    
    }    

    /**
     * Act - do whatever StartScreenButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // If the mouse hovered on the button, change the picture to make it look special.
        if(Greenfoot.mouseMoved(this)) 
        {      
            getWorld().getBackground().drawImage(buttonHover, 0, 0);  

        }

        // If the mouse moved off the button, restore the button to its original appearance.
        if(Greenfoot.mouseMoved(getWorld())) 
        {      
            getWorld().getBackground().drawImage(button, 442, 510);  
        }

        // If the button was clicked, variable becomes true and image is changed.
        if(Greenfoot.mouseClicked(this)) 
        {   
            clicked = true;
            myWorld = (StartScreen)getWorld();
            Greenfoot.setWorld(new TransitionFade());
        }
    }
}
