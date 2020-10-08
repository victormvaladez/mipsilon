package cs420.mipsilon.assembler;

import cs420.mipsilon.core.Instruction;
import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import cs420.mipsilon.Util;

public class TextProcessor{
			
	Vector instructions;
	PreProcessor preProcessor;
	Iterator program;
	int currentLineNumber;
	int currentPC = 0;

	TextProcessor(PreProcessor pp)
	throws AssemblerException
	{		
		instructions = new Vector();
		process(pp);
	}
	
	TextProcessor()
	{
		instructions = new Vector();
	}	

	void process(PreProcessor pp)
	throws AssemblerException
	{			
		preProcessor = pp;
		program = preProcessor.textIterator();
		String line;
		instructions.clear();
		currentPC = 0;
		try
		{
			while(program.hasNext())
			{
				currentLineNumber = ((Integer)program.next()).intValue();
				line = preProcessor.getTextElement(new Integer(currentLineNumber));
				line = line.replace(',', ' ');
				String command [] = line.split("\\s+");
				Instruction inst = processCommand(command);				
				instructions.add(new Integer(inst.getRawInstruction()));
				
				if(inst == null)
					throw new AssemblerException("Unknown error, line " + currentLineNumber );
										
				currentPC += 4;
			}
		}
		catch(AssemblerException ae)
		{ throw new AssemblerException( ae.getMessage() + ", line " + currentLineNumber );} // line num   }
	}
	
	Integer [] getText()
	{
		Integer [] arr = new Integer[1];
		
		return (Integer [ ])instructions.toArray(arr);
	}	
	
	private Instruction processCommand(String [] command)
	throws AssemblerException
	{		
		switch(commandType(command[0]))
		{
			case SharedData.ARITHLOG:
				return processArithLog(command);
			case SharedData.ARITHLOGI:
				return processArithLogI(command);
			case SharedData.LOADSTORE:
				return processLoadStore(command);				
			case SharedData.JUMPR:
				return processJumpR(command);
			case SharedData.JUMP:
				return processJump(command);
			case SharedData.BRANCH:
				return processBranch(command);
			case SharedData.PSEUDO :
				return processPseudo(command);
		}
		
		throw new AssemblerException("Unknown error");		
	}	
	
	private Instruction processPseudo(String [] command)
	throws AssemblerException
	{ // la $a0, array
		if(command.length != 3)
			throw new AssemblerException("Unexpected tokens");
		
		Instruction inst = new Instruction();
		inst.setOpCode( lookupOpFunct("addi") );		
		inst.setRT(lookupRegister(command[1]));
		inst.setRS(0);
		
		inst.setImmAddr( parseLabel(command[2]));
		
		return inst;
	}
	
	
	private Instruction processArithLog(String [] command)
	throws AssemblerException
	{// DST
		// command + tail eg: slt $3, $4, $5 
		
		if(command.length != 4)
			throw new AssemblerException("Unexpected tokens");
		
		Instruction inst = new Instruction();
		inst.setOpCode(0);
		inst.setFUNCT( lookupOpFunct(command[0]) );
		inst.setRD(lookupRegister(command[1]));
		inst.setRS(lookupRegister(command[2]));
		inst.setRT(lookupRegister(command[3]));
		return inst;
	}
	
	private Instruction processArithLogI(String [] command)
	throws AssemblerException
	{// TSI
		// command + tail eg: addi $3, $4, 6 
		
		if(command.length != 4)
			throw new AssemblerException("Unexpected tokens");
		
		Instruction inst = new Instruction();
		inst.setOpCode( lookupOpFunct(command[0]) );		
		inst.setRT(lookupRegister(command[1]));
		inst.setRS(lookupRegister(command[2]));
		try{
			inst.setImmAddr( (new Long (parseNumber(command[3]))).intValue() );			
		}
		catch (NumberFormatException nfe)
		{ throw new AssemblerException( nfe.getMessage() ); }
		return inst;
	}	
		
	private Instruction processJumpR(String [] command)
	throws AssemblerException
	{// S
		// command + tail eg: jr $ra
		
		if(command.length != 2)
			throw new AssemblerException("Unexpected tokens");
		
		Instruction inst = new Instruction();
		inst.setOpCode(0);
		inst.setFUNCT(lookupOpFunct(command[0]));		
		inst.setRS(lookupRegister(command[1]));		
		return inst;
	}
	
	private Instruction processJump(String [] command)
	throws AssemblerException
	{// label
		// command + tail eg: j loop
		
		if(command.length != 2)
			throw new AssemblerException("Unexpected tokens");
		
		Instruction inst = new Instruction();
		inst.setOpCode( lookupOpFunct(command[0]) );		
		inst.setAddr( parseLabel(command[1]) >>> 2 );		
		return inst;
	}
	
	private Instruction processBranch(String [] command)
	throws AssemblerException
	{// ST label
		// command + tail eg: beq $0 $s0 ExitLoop
		
		if(command.length != 4)
			throw new AssemblerException("Unexpected tokens");
		
		Instruction inst = new Instruction();
		inst.setOpCode( lookupOpFunct(command[0]) );		
		inst.setRS(lookupRegister(command[1]));
		inst.setRT(lookupRegister(command[2]));
		inst.setImmAddr( (parseLabel(command[3]) - currentPC) >>> 2 );		
		return inst;
	}
	
	
	private Instruction processLoadStore(String [] command)
	throws AssemblerException
	{// T i(S)
		// command + tail eg: lw $t0 test1 or lw $t0 5($t1)
		
		if(command.length != 3)
			throw new AssemblerException("Unexpected tokens");
		
		Instruction inst = new Instruction();
		inst.setOpCode( lookupOpFunct(command[0]) );		
		inst.setRT(lookupRegister(command[1]));
		
		parseLoadStoreImm(inst, command[2]);
		
		return inst;
	}
	
	private void parseLoadStoreImm(Instruction inst, String token)
	throws AssemblerException
	{
		Pattern p = Pattern.compile("\\d");
		Matcher m = p.matcher(token.substring(0,1)); 		
		
		if(m.matches())
		{
			String [] tokens = token.split("[( )]");
			if(tokens.length != 2)
				throw new AssemblerException("Expected label or offset(register) received '" + token + "'");
				
			try{
				inst.setImmAddr(  new Long(parseNumber(tokens[0])).intValue()  );
			}
			catch (NumberFormatException nfe)
			{ throw new AssemblerException( nfe.getMessage() ); }
			
			inst.setRS(lookupRegister(tokens[1]));
		}
		else
		{
			inst.setRS(0);
			inst.setImmAddr( parseLabel(token));
		}
		
	}	
	
	private int lookupRegister(String reg)
	throws AssemblerException
	{		
		return SharedData.getInstance().getRegisterByName(reg);		
		
	}
	
	private int lookupOpFunct(String command)
	throws AssemblerException
	{
		return SharedData.getInstance().getOpFunctByName(command);
	}
	
	private int commandType(String command)
	throws AssemblerException
	{		
		return SharedData.getInstance().getCommandTypeByName(command);
	}

	private long parseNumber(String number)
	throws NumberFormatException
	{			
		return SharedData.getInstance().parseNumber(number);		
	}
	
	private int parseLabel(String label)
	throws AssemblerException
	{
		Integer absolute;
		if( (absolute = preProcessor.lookupSymbol(label)) == null )
			throw new AssemblerException("no such symbol '" + label + "'");
		
		return absolute.intValue();
	}	
}