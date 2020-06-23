package org.hongxi.whatsmars.boot.sample.beans.factory;

import org.hongxi.whatsmars.boot.sample.beans.Planet;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by shenhongxi on 2020/6/23.
 */
public class PlanetFactoryBean implements FactoryBean<Planet> {

    private String name;

    public PlanetFactoryBean(String name) {
        this.name = name;
    }

    @Override
    public Planet getObject() throws Exception {
        Planet planet = new Planet();
        planet.setName(name);
        return planet;
    }

    @Override
    public Class<?> getObjectType() {
        return Planet.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
