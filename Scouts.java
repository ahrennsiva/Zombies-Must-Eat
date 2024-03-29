import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * The Scout will wander around the map and collect supplies to bring back to the base.
 * 
 * @author Tyler Zhang, Ahrenn Sivananthan
 * @version Sept 2014
 */
public class Scouts extends Humans
{
    private boolean priFood;
    private int getSupCounter;
    private boolean carryingSup;
    private boolean gettingSup;
    private boolean goFromTop;
    protected Food food;
    protected Weapon weapon;

    /**
     * Creates a scout from a certain base with a type of supply that the scout will perfer to obtain.
     * 
     * @param base The number of the base.
     * @param priFood True then the scout will prioritize food over weapon supply.
     */
    public Scouts(int base, boolean priFood)
    {
        this.baseId  = base;
        this.priFood = priFood;

        target = false; //Not tracing a target carrently.
        targetX = 0;
        targetY = 0;

        healthMax = 50;
        health = healthMax;
        speed = 3;

        carryingSup = false; //Not carrying supply currently.
        foodCarry = 0;
        weaponCarry = 0;

        gettingSup = false;
        getSupCounter = 0; //The counter for get supply delay.

        food = new Food(this); //The icon that will indicate what type of supply the scout is carrying.
        weapon = new Weapon(this);

        //Determine whether the scout will avoid obstacle by moving above it or below it.
        if (Greenfoot.getRandomNumber(2) == 1){
            goFromTop = true;
        }else{
            goFromTop = false;
        }   
    }

    /**
     * Scouts will continue to wander around the map, until a supply crate is in their site.
     * They will obtain the type of supply they need, then return to base.
     *
     */
    public void act() 
    {
        if (!gettingSup){ //Do not move if the scout is getting supply.
            if (target){ //Move towards the target.
                avoidObs(goFromTop);
                turnTowards(targetX, targetY);
                move(speed);
            }
            if (getWorld().getObjects(Supplies.class).size() != 0 && !carryingSup){ //Only search for supply is there is supply on screen.
                Supplies sup = (Supplies)getOneObjectAtOffset(speed - 1,speed - 1,Supplies.class);  //Get touching supply.
                if (sup != null){ //If there is one.
                    getSupply(sup); //Get the supply from a supply pile.
                }else{ 
                    findSupply(); //Search for the nearest supply.
                }
            }else{ //Return to base if there are no supply on the screen.
                returnToBase();
            }
        }else{
            if (getSupCounter == 0){
                gettingSup = false; //Finished collecting supply.
                if(carryingSup){ //If the scout is carrying supply return to base.
                    returnToBase();
                }
            }else{
                getSupCounter --; //Decrease the get supply counter.
            }
        }
        hasReturned();
    }

    /**
     * Returns the distance from this to another object.
     */
    private double getDistance(Actor actor) {
        return Math.hypot(actor.getX() - getX(), actor.getY() - getY());
    }

    /**
     * Returns the nearest supply to a scout.
     */
    private Supplies getNearestSup(List<Supplies> sup)
    {
        Supplies nearSup = null;
        double nearestDistance = 9999;
        for (int i = 0; i < sup.size(); i++) {
            double distance = getDistance(sup.get(i));
            if (distance < nearestDistance) {
                nearSup = sup.get(i);
                nearestDistance = distance;
            }
        }
        return nearSup;
    }

    /**
     * Checks to see if there are supplies on screen.
     * If so, set the nearest supply as the target.
     */
    private void findSupply()
    {
        List<Supplies> sup = getWorld().getObjects(Supplies.class);
        if (sup.size() == 0){
            target = false;
        }else{
            Supplies nearest = (Supplies)getNearestSup(sup);
            setTarget(nearest.getX(),nearest.getY());
            target = true;
        }
    }

    /**
     * Get a number of supplies.
     */
    private void getSupply(Supplies sup){
        gettingSup = true; //Set the status of scout to be getting supply.
        getSupCounter = 30; //It will take the scout 30 acts to get the supply.
        carryingSup = true;
        int foodLeft = sup.getFood();
        int weaponLeft = sup.getWeapons();
        if (priFood){
            if (foodLeft >= 10){ //If there are more than 5 food left, get 5.
                sup.takeFood(10);
                foodCarry += 10;
            }else if (foodLeft > 0){ //If there are less than 5 food, get all that are left.
                sup.takeFood(foodLeft); //Get whatever food is left.
                foodCarry += foodLeft;
            }else{ //If there is no food, get weapon.
                if (weaponLeft >= 3){ //If there are more than 2 weapons left, get 2.
                    sup.takeWeapons(3);
                    weaponCarry += 3;
                }else if (weaponLeft > 0){ //Get however many weapon is left.
                    sup.takeWeapons(weaponLeft); 
                    weaponCarry += weaponLeft;
                }else{ //If there is neither weapon nor food left.
                    carryingSup = false;
                }
            }
        }else{ //If the base is not in need of food. Try to get weapon first.
            if (weaponLeft >= 3){ //If there are more than 2 weapons left, get 2.
                sup.takeWeapons(3);
                weaponCarry += 3;
            }else if (weaponLeft > 0){ //Get however many weapon is left.
                sup.takeWeapons(weaponLeft); 
                weaponCarry += weaponLeft;
            }else{ //If there is no weapon left, get food.
                if (foodLeft >= 10){ //If there are more than 5 food left, get 5.
                    sup.takeFood(10);
                    foodCarry += 10;
                }else if (foodLeft > 0){ //If there are less than 5 food, get all that are left.
                    sup.takeFood(foodLeft); //Get whatever food is left.
                    foodCarry += foodLeft;
                }else{ //If there is neither weapon nor food left.
                    carryingSup = false;
                }
            }
        }
        updateImg(); //Add the food or weapon icon to the world.
    }

    /**
     * Updates the image of the scout, with the type of supply that he is carrying.
     */
    private void updateImg()
    {
        //To add the icon as a new object.
        if(foodCarry > 0){
            getWorld().addObject(food, getX(), getY() - 30);
        }else if(weaponCarry > 0){
            getWorld().addObject(weapon, getX(), getY() - 30);
        }
    }
}