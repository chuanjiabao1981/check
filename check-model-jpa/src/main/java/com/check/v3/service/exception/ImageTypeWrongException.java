package com.check.v3.service.exception;

public class ImageTypeWrongException extends Exception{

	private int idx;
	
	
	public ImageTypeWrongException(int x)
	{
		this.idx =x ;
	}
	
	public int getIdx()
	{
		return idx;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -5379453588698900902L;


}
