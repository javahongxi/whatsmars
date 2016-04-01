package com.whatsmars.mars001.common.mongo.object;

import org.bson.types.ObjectId;


public class ObjectIdCreator{

	public static String creator(){
		return new ObjectId().toString();
	}
}
