package org.hongxi.whatsmars.spring.context.annotation.repository;

import org.springframework.stereotype.Repository;

/**
 * Created by shenhongxi on 2018/2/6.
 */
@Repository
public class DemoRepository {

    public DemoRepository() {
        System.out.println("DemoRepository ...");
    }
}
