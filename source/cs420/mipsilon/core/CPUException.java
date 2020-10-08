package cs420.mipsilon.core;

public class CPUException extends Exception
{	
	public CPUException() 
	{ }
	public CPUException(String message) 
	{ super(message); }
	public CPUException(String message, Throwable cause) 
	{super(message,cause);}
	public CPUException(Throwable cause) 
	{super(cause);}
}