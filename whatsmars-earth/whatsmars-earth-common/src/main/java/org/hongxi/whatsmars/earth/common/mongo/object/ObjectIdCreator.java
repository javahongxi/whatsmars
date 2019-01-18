package org.hongxi.whatsmars.earth.common.mongo.object;

import org.bson.types.ObjectId;


public class ObjectIdCreator{

	public static String creator(){
		return new ObjectId().toString();
	}
}
