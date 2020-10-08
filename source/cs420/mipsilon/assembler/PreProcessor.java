package cs420.mipsilon.assembler;

import java.io.LineNumberReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.InputStream;

import java.util.TreeMap;
import java.util.HashMap;
import java.util.Iterator;

class PreProcessor{
	static final int NONE = 0;
	static final int TEXT = 1;
	static final int DATA = 2;

	private LineNumberReader br;
	private int segmentCounter;
	private	TreeMap textSegment;	
	private int currentSegment = NONE;
	private boolean doneText = false, doneData = false;
	private HashMap symbols;	
	private DataProcessor dp;
	
	PreProcessor()	
	{		
		textSegment = new TreeMap();		
		symbols = new HashMap();
		dp = new DataProcessor();		
	}	
	
	Iterator textIterator()
	{
		return textSegment.keySet().iterator();
	}
	
	Iterator symbolIterator()
	{
		return symbols.keySet().iterator();
	}
	
	String getTextElement(Integer lineNumber)
	{
		return (String) textSegment.get(lineNumber);
	}		
	
	Integer lookupSymbol(String symbol)
	{
		return (Integer) symbols.get(symbol);
	}
	
	Byte [] getData()
	{
		return dp.getData();
	}		
		
	void processFile(InputStream ins)
	throws 
	java.io.FileNotFoundException,
	AssemblerException,
	java.io.IOException
	{		
		String line;		
		br = new LineNumberReader( new InputStreamReader(ins) );
		
		textSegment.clear();		
		symbols.clear();
		currentSegment = NONE;
		segmentCounter = 0;
		doneText = false;
		doneData = false;
		dp.reset();
		
		while( (line = br.readLine() ) != null )
		{
			line = stripComments(line).trim();
			if(line.length() != 0)
			{	
				if( !doneText && line.startsWith(".text" ))
				{
					currentSegment = TEXT;
					doneText = true;
					segmentCounter = 0;					
				}
				else if( !doneData && line.startsWith(".data" ))
				{
					currentSegment = DATA;
					doneData = true;
					segmentCounter = 0;					
				}				
				else if(currentSegment == NONE)
				{
					throw new AssemblerException( "Preprocessor, A .text or .data expected before any instructions or other directives, Line: " + br.getLineNumber());
				}
				else
				{
					if(currentSegment == TEXT)
						processTextLine(line);
					if(currentSegment == DATA)	
						processDataLine(line);
				}
			}
		}
	br.close();
	}
	
	private String stripComments(String line)
	{
		int commentStart;
		if( ( commentStart = line.indexOf("#")) != -1 )
			line = line.substring(0, commentStart);
		line = line.toLowerCase().trim();
		return line;
	}
	
	private void processTextLine(String line)
	throws AssemblerException
	{
		String label  = getLabel(line);
		if(label.length() != 0)
		{
			addLabel(label);		
			line = stripLabel(line);
		}
		if( line.length() != 0)
		{
			//System.out.println("[0x" + Integer.toHexString(segmentCounter) +"]" + br.getLineNumber() + ": " + line);
			textSegment.put(new Integer(br.getLineNumber()), line);
			segmentCounter += 4;
		}
	}
	
	private void processDataLine(String line)
	throws AssemblerException
	{
		String label  = getLabel(line);
		if(label.length() != 0)
		{
			addLabel(label);
			line = stripLabel(line);
		}
		if( line.length() != 0)
		{
			//System.out.println("---------" + label + ": [0x" + segmentCounter + "]");			
			try {segmentCounter += dp.processLine(line); }
			catch(AssemblerException ae){throw new AssemblerException("Data processor : " + ae.getMessage() + ", line: " + br.getLineNumber());}			
		}
	}

	private void addLabel(String label)
	throws AssemblerException
	{
		if(symbols.containsKey( label ))		
			throw new AssemblerException("Preprocessor : Duplicate Label '" + label + "', line: " + br.getLineNumber());
		else
		{
			//System.out.println("-------------[0x" + Integer.toHexString(segmentCounter) + "]:" + label);
			symbols.put(label, new Integer(segmentCounter ));
		}	
	}
	
	// Return a valid label if there is one, "" if there is no label present
	private String getLabel(String line)
	throws AssemblerException
	{
		String label = "";
		int labelStart;
		if( ( labelStart = line.indexOf(":")) != -1 )
		{
			label = line.substring(0, labelStart);
			if(label.length() == 0)
				throw new AssemblerException("Preprocessor : Empty Label, line: " + br.getLineNumber());
				
			label = label.trim();			
			if(!validLabel(label) )
				throw new AssemblerException("Preprocessor : Invalid Label, line: " + br.getLineNumber());
		}

		return label;
	}

	private String stripLabel(String line)
	{		
		int labelEnd;
		labelEnd = line.indexOf(":");
		return line.substring(labelEnd + 1).trim();
	}
	
	private boolean validLabel(String label)
	{
		if(label == "")
			return false;
		return true;
	}

	
}

