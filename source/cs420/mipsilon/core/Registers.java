package cs420.mipsilon.core;

public class Registers{

	Registers()
	{}
	
	private static final String [] register = { "$0",
						    "$at",						
		                                    "$v0", "$v1",
		                                    "$a0", "$a1", "$a2", "$a3",
		                                    "$t0", "$t1", "$t2", "$t3","$t4", "$t5", "$t6", "$t7",
		                                    "$s0", "$s1", "$s2", "$s3","$s4", "$s5", "$s6", "$s7",
		                                    "$t8", "$t9",
		                                    "$k0", "$k1",
		                                    "$gp",
		                                    "$sp",
		                                    "$fp",
		                                    "$ra"};
	
	private int [] registers = new int[32];  // the 32 general purpose registers.
	private int PC, EPC, HI,LO, status, cause;
	private int readRegister1, readRegister2, writeRegister;
	private int writeData;
	
	public int getReadRegister1()
	{
		return readRegister1;
	}
	
	public void setReadRegister1(int reg1)
	throws CPUException
	{
		// Check for proper value
		if(reg1 > 31)
			throw new CPUException( "Register : Invalid register : " + reg1 );
		
		readRegister1 = reg1;
	}
	
	public int getReadRegister2()
	{
		
		return readRegister2;
	}
	
	public void setReadRegister2(int reg2)
	throws CPUException
	{
		if(reg2 > 31)
			throw new CPUException( "Register : Invalid register : " + reg2 );
		readRegister2 = reg2;
	}
	
	public int getWriteRegister()
	{
		return writeRegister;
	}
	
	public void setWriteRegister(int writeReg)
	throws CPUException
	{
		if(writeReg > 31)
			throw new CPUException( "Register : Invalid register : " + writeReg );
		writeRegister = writeReg;
	}
	
	public static String getMnemonic(int reg)
	// thorws InvalidInstruction: invalid register
	{
		return register[reg];
	}
	
	public void setReg(int register, int value)
	throws CPUException
	{
		if(register > 31)
			throw new CPUException( "Register : Invalid register : " + register );
		if(register != 0)
			registers[register] = value;	
	}
	
	public void link()
	{
		registers[31] = (PC + 4);	
	}
	
	
	public void setWriteData(int data)
	{
		writeData = data;
	}
	
	public void write()
	{
		if(writeRegister != 0)
			registers[writeRegister] = writeData;		
	}
	
	public int getReadData1()
	{
		return registers[readRegister1];
	}
	
	public int getReadData2()
	{
		return registers[readRegister2];
	}
	
	
	public int getReg(int register)
	{
		//Check for proper value
		return registers[register];
	}
	
	public int getPC()
	{
		return PC;
	}
	
	public void setPC(int newPC)
	{
		PC = newPC;
	}
	
	public int getEPC()
	{
		return EPC;
	}
	void setEPC(int newEPC)
	{
		EPC = newEPC;
	}
	
	public int getHI()
	{
		return HI;
	}
	void setHI(int newHI)
	{
		HI = newHI;
	}
	
	public int getLO()
	{
		return LO;
	}
	void setLO(int newLO)
	{
		LO = newLO;
	}
	
	public int getStatus()
	{
		return status;
	}
	void setStatus(int newStatus)
	{
		status = newStatus;
	}
	
	public int getCause()
	{
		return cause;
	}
	void setCause(int newCause)
	{
		cause = newCause;
	}

//MIPS is the best! It is my best friend. We have hours of fun together every day! I love MIPS. I hope it never goes away.

//Katie RULES ALL!!!

//Victor thinks he's funny. He's not! Ok, back to laying down


}

