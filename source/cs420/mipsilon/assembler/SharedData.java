package cs420.mipsilon.assembler;
import java.util.HashMap;
import java.util.Vector;

final class SharedData { 
 private static SharedData _instance; 
	
	private static HashMap registerMap;
	private static HashMap commands;
	
	public static final int ARITHLOG  = 0;
	public static final int ARITHLOGI = 1;
	public static final int LOADSTORE = 2;	
	public static final int JUMPR     = 3;
	public static final int JUMP      = 4;
	public static final int BRANCH    = 5;
	public static final int PSEUDO    = 6;

 private SharedData() { 
 		  		
		registerMap = new HashMap();
		commands = new HashMap();
		
		commands.put("add",  new Command(new Integer( 040 ),ARITHLOG) ); // 100 000
		
		
		commands.put("add",  new Command(new Integer( 040 ),ARITHLOG) ); // 100 000
		commands.put("sub",  new Command(new Integer( 042 ),ARITHLOG) ); // 100 010
		commands.put("and",  new Command(new Integer( 044 ),ARITHLOG) ); // 100 100
		commands.put("or",   new Command(new Integer( 045 ),ARITHLOG) ); // 100 101
		commands.put("slt",  new Command(new Integer( 052 ),ARITHLOG) ); // 101 010
		
		
		commands.put("addi", new Command(new Integer( 010 ),ARITHLOGI) ); // 001 000		

		
		commands.put("lw",   new Command(new Integer( 043 ),LOADSTORE) ); // 100 011
		commands.put("sw",   new Command(new Integer( 053 ),LOADSTORE) ); // 101 011
		
		
		commands.put("jr"  , new Command(new Integer( 010 ),JUMPR) );     // 001 000

		
		commands.put("j"  ,  new Command(new Integer( 002 ),JUMP) );      // 000 010
		commands.put("jal",  new Command(new Integer( 003 ),JUMP) );      // 000 011
		
		
		commands.put("beq",  new Command(new Integer( 004 ),BRANCH) );    // 000 100
		
		
		commands.put("la",   new Command(new Integer( 000 ),PSEUDO) );
		
			
		registerMap.put("$0", new Integer(0));
		registerMap.put("$1", new Integer(1));
		registerMap.put("$2", new Integer(2));
		registerMap.put("$3", new Integer(3));
		registerMap.put("$4", new Integer(4));
		registerMap.put("$5", new Integer(5));
		registerMap.put("$6", new Integer(6));
		registerMap.put("$7", new Integer(7));
		
		registerMap.put("$8", new Integer(8));		
		registerMap.put("$9", new Integer(9));
		registerMap.put("$10", new Integer(10));
		registerMap.put("$11", new Integer(11));
		registerMap.put("$12", new Integer(12));
		registerMap.put("$13", new Integer(13));
		registerMap.put("$14", new Integer(14));
		registerMap.put("$15", new Integer(15));
		
		registerMap.put("$16", new Integer(16));		
		registerMap.put("$17", new Integer(17));
		registerMap.put("$18", new Integer(18));
		registerMap.put("$19", new Integer(19));
		registerMap.put("$20", new Integer(20));
		registerMap.put("$21", new Integer(21));
		registerMap.put("$22", new Integer(22));
		registerMap.put("$23", new Integer(23));
		
		registerMap.put("$24", new Integer(24));
		registerMap.put("$25", new Integer(25));
		
		registerMap.put("$26", new Integer(26));
		registerMap.put("$27", new Integer(27));
		
		registerMap.put("$28", new Integer(28));
		
		registerMap.put("$29", new Integer(29));
		
		registerMap.put("$30", new Integer(30));
		
		registerMap.put("$31", new Integer(31));
		//--------------
		registerMap.put("$zero", new Integer(0));		
		registerMap.put("$at", new Integer(1));		
		registerMap.put("$v0", new Integer(2));		
		registerMap.put("$v1", new Integer(3));		
		registerMap.put("$a0", new Integer(4));	
		registerMap.put("$a1", new Integer(5));	
		registerMap.put("$a2", new Integer(6));	
		registerMap.put("$a3", new Integer(7));
		
		registerMap.put("$t0", new Integer(8));
		registerMap.put("$t1", new Integer(9));		
		registerMap.put("$t2", new Integer(10));		
		registerMap.put("$t3", new Integer(11));		
		registerMap.put("$t4", new Integer(12));		
		registerMap.put("$t5", new Integer(13));
		registerMap.put("$t6", new Integer(14));
		registerMap.put("$t7", new Integer(15));

		registerMap.put("$s0", new Integer(16));		
		registerMap.put("$s1", new Integer(17));
		registerMap.put("$s2", new Integer(18));
		registerMap.put("$s3", new Integer(19));
		registerMap.put("$s4", new Integer(20));
		registerMap.put("$s5", new Integer(21));
		registerMap.put("$s6", new Integer(22));
		registerMap.put("$s7", new Integer(23));
		
		registerMap.put("$t8", new Integer(24));
		registerMap.put("$t9", new Integer(25));
	
		registerMap.put("$k0", new Integer(26));
		registerMap.put("$k1", new Integer(27));

		registerMap.put("$gp", new Integer(28));
		
		registerMap.put("$sp", new Integer(29));
		
		registerMap.put("$fp", new Integer(30));
		
		registerMap.put("$ra", new Integer(31));
 } 

	static int getRegisterByName(String name)
	throws AssemblerException
	{
		Integer found;
		 if( (found = (Integer) registerMap.get(name)) == null )
			throw new AssemblerException("Expected register, received '" + name + "'");
		
		return found.intValue();
		
	}
	
	static int getOpFunctByName(String command)
	throws AssemblerException
	{
		Command found;
		if( (found = (Command) commands.get(command)) == null )
			throw new AssemblerException("Unrecognized instruction : '" + command + "'");
		
		return found.getOpFunct().intValue();
	}
	
	static int getCommandTypeByName(String command)
	throws AssemblerException
	{
		Command found;
		if( (found = (Command) commands.get(command)) == null )
			throw new AssemblerException("Unrecognized instruction : '" + command + "'");
		
		return found.getCommandClass();
	}
	
	
	static long parseNumber(String number)
	throws NumberFormatException
	{		
		if(number.startsWith("0x"))
			return Long.parseLong(number.substring(2, number.length()), 16);
		else if(number.startsWith("0"))
			return Long.parseLong(number, 8);
		else
			return Long.parseLong(number);
		
	}
	

 // For lazy initialization 
 public static synchronized SharedData getInstance() { 
  if (_instance==null) { 
   _instance = new SharedData();
  } 
  return _instance; 
 } 
  // Remainder of class definition . . . 
} 

class Command{
	
	Integer opFunct; 
	int commandClass;
	
	Command(Integer opFunct, int commandClass)
	{
		this.opFunct = opFunct;
		this.commandClass = commandClass;
	}
	
	Integer getOpFunct()
	{
		return opFunct;
	}
	
	int getCommandClass()
	{
		return commandClass;
	}
	
}