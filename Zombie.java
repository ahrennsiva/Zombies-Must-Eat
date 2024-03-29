import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * The zombie that will wander around the map, however when encounters a human, it will chase after it.
 * And attack the human once caught up.
 * 
 * @author Tyler Zhang
 * @version Oct 2014
 */
public class Zombie extends Char
{
    //Field of instance variables
    private Humans target;
    private int hungerLevel;
    private int dmg;
    private int attackCounter;
    private int hungerCounter;    
    public Zombie()
    {
        health = 100;
        speed = 2;
        target = null;
        hungerLevel = 10;
        dmg = 10;
        attackCounter = 0;
        hungerCounter = 0;        
    }

    public void act()
    {
        if(target == null){
            avoidObs(true);
            wander();
        }else{
            chaseHuman(); //Chase after the human and attack is caughtII
        }

        if (hungerCounter >= 250){
            hungerLevel--;
            if(hungerLevel < 0){
                hungerLevel = 0;
                health -= 10;
            }
        }else{
            hungerCounter++;   
        }

        try{
            getTarget();
        }catch(Exception e){
        }
    }

    /**
     * Chase after the targeted human.
     */
    private void chaseHuman(){
        if (target != null){
            avoidObs(true);
            turnTowards(target.getX(),target.getY());
            move(speed);
            if(caughtHuman()){
                if (attackCounter > 10){
                    attack();
                    attackCounter = 0;
                }else{
                    attackCounter++;
                }
            }
        }
    }

    /**
     * Return the reference to the target that the zombie is currently tracking.
     */
    public Humans returnTarget()
    {
        return target;
    }

    /**
     * Set the target as null.
     */
    public void clearTarget()
    {
        target = null;
    }

    /**
     * attack the human object in contact.
     */
    private void attack()
    {
        myWorld = (ZombieWorld)getWorld();
        if (hungerLevel < 8){
            if (target.getHit(dmg, false)){ //If the human is killed.
                myWorld.humanKilled();
                hungerLevel += 5;               
                target = null;
                if (hungerLevel > 10){
                    hungerLevel = 10;   
                }
            }
        }else{
            if (target.getHit(dmg, true)){ // Convert human to zombie.
                myWorld.humanConverted();                
                target = null;
            }
        }
    }

    /**
     * Check if the zombie is in contact with a human.
     * 
     * @return boolean True, if caught a human, else return false.
     */
    private boolean caughtHuman()
    {
        return this.intersects(target);
    }

    /**
     * Set the nearest human in a range as the target.
     */
    private void getTarget()
    {
        List<Humans> men = getObjectsInRange(150, Humans.class);
        if (men.size() == 0){
            target = null;
        }else{
            target = (Humans)getNearestHuman(men);
        }
    }

    /**
     * Return the nearest human.
     * 
     * @Param men A list of human objects within a range of the zombie.
     */
    private Humans getNearestHuman(List<Humans> men)
    {
        Humans nearMan = null;
        double nearestDistance = 9999;
        for (int i = 0; i < men.size(); i++) {
            double distance = getDistance(men.get(i));
            if (distance < nearestDistance) {
                nearMan = men.get(i);
                nearestDistance = distance;
            }
        }
        return nearMan;
    }

    /**
     * Return the distance from this to another object.
     */
    private double getDistance(Actor actor) {
        return Math.hypot(actor.getX() - getX(), actor.getY() - getY());
    }

    protected void loseHealth(int damage){
        if (damage < health){
            health -= damage;
        }else{
            myWorld = (ZombieWorld)getWorld();
            myWorld.oneZombieDie();
            myWorld.zombieKilled();
            getWorld().removeObject(this);
        }
    }
}