package cs420.mipsilon.core;

import java.util.TreeMap;
import java.util.Collection;
import java.util.Iterator;

import java.util.Observable;
import java.util.Observer;

public class Memory extends Observable{

	
	TreeMap mem;
	
	private int address;
	private int writeData;
	private int readData;
	
	public void write()
	{
		write(address, writeData);
	}
	
	public void read()
	{
		readData = read(address);
	}	
	
	public int getReadData()
	{
		return readData;
	}
	
	public void setAddress(int address)
	{
		this.address = address;
	}
	
	public int getAddress()
	{
		return address;
	}	
	
	public void setWriteData(int wd)
	{
		writeData = wd;
	}
		
	public Memory()
	{
		super();
		mem = new TreeMap();
	}
	
	public int read(int address)
	{
		byte [] wordBytes = new byte[4];
		Byte inByte;
		int result;
		for(int i = 0; i < 4; i++)
		{			
			if( (inByte = (Byte) mem.get( new Integer(address+i) )) != null )
				wordBytes[3-i] = inByte.byteValue();
			else
				wordBytes[3-i] = 0x00;
		}
		
		// Transform array of bytes into int value		
		result = wordBytes[3] << 24;
		result = (result | ( (wordBytes[2] << 16) & 0x00FF0000 )  );
		result = (result | ( (wordBytes[1] <<  8) & 0x0000FF00 )  );
		result = (result | ( wordBytes[0] & 0x000000FF )  );
		
		return result;
	}
	
	
	public void write(int address, int word)
	{		
		byte [] wordBytes = new byte[4];
		wordBytes[3] = (byte) ((word & 0xFF000000 ) >>> 24 );
		wordBytes[2] = (byte) ((word & 0x00FF0000 ) >>> 16 );
		wordBytes[1] = (byte) ((word & 0x0000FF00 ) >>> 8 );
		wordBytes[0] = (byte) ( word & 0x000000FF );
		mem.put(new Integer(address), new Byte(wordBytes[3]));
		mem.put(new Integer(address+1), new Byte(wordBytes[2]));
		mem.put(new Integer(address+2), new Byte(wordBytes[1]));
		mem.put(new Integer(address+3), new Byte(wordBytes[0]));
	}
	
	public void write(int address, short half)
	{
		byte lower,  upper;
		
		lower = (byte) ((half & 0xFF00 ) >>> 8 );
		upper = (byte) (half & 0x00FF );
		mem.put(new Integer(address), new Byte(lower));
		mem.put(new Integer(address+1), new Byte(upper));
	}
	
	public void write(int address, byte b)
	{
		mem.put(new Integer(address),new Byte(b));
	}
	
	public void write(int address, Byte b)
	{
		mem.put(new Integer(address),new Byte(b.byteValue()));
	}
	
	public int size()
	{
		return  mem.size();
	}
	
	public Iterator iterator()
	{
		return mem.keySet().iterator();
	}
	
	public void clear()
	{
		mem.clear();
		
		setChanged();
		notifyObservers(new MIPSObservation("MEM_CLEAR", null));
	}
	
	
	

}
