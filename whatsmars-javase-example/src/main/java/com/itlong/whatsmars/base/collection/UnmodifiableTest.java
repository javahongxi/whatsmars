package com.itlong.whatsmars.base.collection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenhongxi on 2016/6/14.
 */
public class UnmodifiableTest {

    private Map<String, Point> startingLocations = new HashMap<String, Point>(3);

    public UnmodifiableTest(){
        startingLocations.put("LeftRook", new Point(1, 1));
        startingLocations.put("LeftKnight", new Point(1, 2));
        startingLocations.put("LeftCamel", new Point(1, 3));
        //..more locations..
    }

    public Map<String, Point> getStartingLocations(){
        return Collections.unmodifiableMap(startingLocations);
    }

    public static void main(String [] args){
        UnmodifiableTest  pieceLocations = new UnmodifiableTest();
        Map<String, Point> locations = pieceLocations.getStartingLocations();

        Point camelLoc = locations.get("LeftCamel");
        System.out.println("The LeftCamel's start is at [ " + camelLoc.getX() +  ", " + camelLoc.getY() + " ]");

        //Try 1. update elicits Exception
        try{
            locations.put("LeftCamel", new Point(0,0));
        } catch (java.lang.UnsupportedOperationException e){
            System.out.println("Try 1 - Could not update the map!");
        }

        //Try 2. Now let's try changing the contents of the object from the unmodifiable map!
        camelLoc.setLocation(0,0);

        //Now see whether we were able to update the actual map
        Point newCamelLoc = pieceLocations.getStartingLocations().get("LeftCamel");
        System.out.println("Try 2 - Map updated! The LeftCamel's start is now at [ " + newCamelLoc.getX() +  ", " + newCamelLoc.getY() + " ]");       }
}
