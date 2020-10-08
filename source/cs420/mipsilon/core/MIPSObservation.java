package cs420.mipsilon.core;




public class MIPSObservation{

	
	String event;
	Object data;
	

	MIPSObservation(String event, Object data)
	{
		this.event = event;
		this.data = data;
	}
	

	public String getEvent()
	{
		return event;		
	}
	
	public Object getData()
	{
		return data;
	}
	

}