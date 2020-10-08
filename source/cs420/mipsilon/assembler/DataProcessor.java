package cs420.mipsilon.assembler;

import java.util.StringTokenizer;
import java.util.Vector;

class DataProcessor {
	
	private static final int BYTE = 1;
	private	static final int HALF = 2;
	private	static final int WORD = 4;
	
	private Vector data;	
	DataProcessor()
	{data = new Vector(); }
	
	// returns number of bytes
	int processLine(String line )
	throws AssemblerException
	{
		line = line.trim();
		int typeEnd;
		if( (typeEnd = line.indexOf(" ")) == -1 )
			throw new AssemblerException("Unexpected");
		
		String type = line.substring(0, typeEnd).trim();
		String tail = line.substring(typeEnd).trim();
		
		if(type.equals(".word"))
			return processNumberTail(tail, WORD);
		else if(type.equals(".half"))
			return processNumberTail(tail, HALF);
		else if(type.equals(".space"))
			return processSpaceTail(tail);
		else if(type.equals(".asciiz"))
			return processAsciiTail(tail, true);
		else if(type.equals(".ascii"))
			return processAsciiTail(tail, false);
		else if(type.equals(".byte"))
			return processNumberTail(tail, BYTE);
		else
			throw new AssemblerException("invalid or unsupported data directive: " + type);
			
	}
	
	Byte [] getData()
	{
		Byte [] arr = new Byte[1];
		return (Byte [] )data.toArray(arr);
	}
	
	void reset()
	{
		data.clear();
	}
	
	
	private int processNumberTail(String tail, int size)
	throws AssemblerException
	{
		int i;		
		tail = tail.replace(',', ' ').trim();
		try{
			String numberString;
			long value;			
			StringTokenizer st = new StringTokenizer(tail, " ");
			for(i = 0; st.hasMoreTokens(); i++)
			{
				numberString = st.nextToken();
				value = parseNumber(numberString);
				if(value >= Math.pow(2 ,(8*size)) )
					throw new AssemblerException("number is too large for the data type");
				else
				{
					stuffNumber(value, size);
					//System.out.println("num: " + "0x" + Util.padZero(Long.toHexString(wordValue),size*2 )  );
				}
			}
			return i*size;	
				
		}
		catch(NumberFormatException nfe)
		{
			throw new AssemblerException("invalid number: " + nfe.getMessage());
		}
	}
			
	private int processAsciiTail(String tail, boolean asciiz)
	throws AssemblerException
	{
		tail = tail.trim();
		if( !(tail.startsWith("\"") && tail.endsWith("\"") ) )
			throw new AssemblerException("invalid string");
		tail = tail.substring(1, tail.length() - 1 );
		
		stuffString(tail, asciiz);
		
		if(asciiz)
			return tail.length() + 1;
		else
			return tail.length();
				
	}
	
	private int processSpaceTail(String tail)
	throws AssemblerException
	{
		try{
			String numberString;
			long spaces;			
			StringTokenizer st = new StringTokenizer(tail, " ");
			if(st.hasMoreTokens())
				numberString = st.nextToken();
			else
				throw new AssemblerException("number expected" );
			if(st.hasMoreTokens())
				throw new AssemblerException("unexpected token: '" + st.nextToken() + "'");
			else
			{
				spaces = parseNumber(numberString);
				if(spaces >= Math.pow(2 ,(8*4)) )
					throw new AssemblerException("number is too large for the data type");				
								
				stuffSpaces(spaces);
				return new Long(spaces).intValue();
			}
					
		}
		catch(NumberFormatException nfe)
		{
			throw new AssemblerException("invalid number: "+ nfe.getMessage());
		}
		
	}
	
	private long parseNumber(String number)
	throws NumberFormatException
	{		
		return SharedData.getInstance().parseNumber(number);
		
	}
	
	private void stuffSpaces(long spaces)	
	{
		for(int i = 0; i < spaces; i++)
			data.add(new Byte((byte) 0));
	}
	
	
	private void stuffNumber(long rawData, int size)
	{					
		for(int i = size-1; i >= 0; i--)
		{
			Byte b = new Byte(new Long(rawData >>> (i*8) & 0xFF).byteValue());			
			data.add(b);
		}			
	}
	 
	private void stuffString(String string, boolean asciiz)
	{
		char [] charArray = string.toCharArray();
		int i;	
		
		for(i = 0; i < charArray.length; i++)
		{
			Byte b = new Byte( (byte) charArray[i] );			
			data.add(b);
		}
		
		if(asciiz)
			data.add(new Byte((byte) 0)); // add null

	}
	
	
}	