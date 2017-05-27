package com.itlong.whatsmars.uuid.web;

import java.util.List;

/**
 * Created by shenhongxi on 2016/8/12.
 */
public interface UuidDao {

    boolean insert(UuidModel uuidModel);

    boolean update(UuidModel uuidModel);

    List<UuidModel> getAll();

    UuidModel findByName(String name);
}
