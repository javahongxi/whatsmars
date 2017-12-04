package com.whatsmars.base.nio.qing;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class Packet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7719389291885063462L;
	
	private ByteBuffer buffer;
	
	private static Charset charset = Charset.defaultCharset();
	
	private Packet(ByteBuffer buffer){
		this.buffer = buffer;
	}
	
	public String getDataAsString(){
		return charset.decode(buffer).toString();
	}
	
	public byte[] getData(){
		return buffer.array();
	}
	
	public ByteBuffer getBuffer(){
		return this.buffer;
	}
	
	
	public static Packet wrap(ByteBuffer buffer){
		return new Packet(buffer);
	}
	
	public static Packet wrap(String data){
		ByteBuffer source = charset.encode(data);
		return new Packet(source);
	}
}
