package org.hongxi.whatsmars.boot.sample.beans.factory;

import org.hongxi.whatsmars.boot.sample.beans.Planet;

/**
 * Created by shenhongxi on 2020/6/23.
 */
public class PlanetFactory {

    public static Planet createPlanet(String name) {
        Planet planet = new Planet();
        planet.setName(name);
        return planet;
    }
}
