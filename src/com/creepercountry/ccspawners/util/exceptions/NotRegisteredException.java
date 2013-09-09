package com.creepercountry.ccspawners.util.exceptions;

public class NotRegisteredException extends Exception
{
	private static final long serialVersionUID = 375945223391669305L;

	public NotRegisteredException()
	{
		super("Not registered.");
	}

	public NotRegisteredException(String message)
	{
		super(message);
	}
}