package com.check.v3.service.exception;

import org.springframework.dao.NonTransientDataAccessException;

public class ImageTypeWrongException extends NonTransientDataAccessException{

	private int idx;
	
	
	public ImageTypeWrongException(int x)
	{
		super("image error");
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
