package com.creepercountry.ccspawners.util.exceptions;

public class AlreadyRegisteredException extends Exception
{
	private static final long serialVersionUID = -1852623378052842131L;

	public AlreadyRegisteredException()
	{
		super("Already registered.");
	}

	public AlreadyRegisteredException(String message)
	{
		super(message);
	}
}