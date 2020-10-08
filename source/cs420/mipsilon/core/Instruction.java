package cs420.mipsilon.core;

/**
 * <code>Instruction</code> A 32-bit MIPS Instruction
 *   
 */

public class Instruction{

	private int rawInstruction = 0x00000000;
	
	/**
	* Constructs a MIPS Instruction by providing a list of words in the form of an
	* array of Strings. The list is immediately shuffled.
	* 
	* @param instruction a 32-bit integer that is the instruction.
	*/
	public Instruction(int instruction)
	{
		rawInstruction = instruction;
		
		// Validate?
	}
	
	/**
	* Constructs a nop MIPS Instruction (0x00000000)
	* 	
	*/
	public Instruction()
	{ }
	
	/**
	* Returns the opcode of the 32-bit MIPS instruction
	*
	*/
	public int getOpCode()
	{
		return  (int) ((rawInstruction & 0xFC000000) >>> 26);
	}
	
	public void setOpCode(int opCode)
	{
		rawInstruction = (rawInstruction & 0x3FFFFFFF) | ( opCode << 26 );		
	}
	
	/**
	* Returns the 32-bit MIPS instruction.
	*
	*/
	public int getRawInstruction()
	{
		return rawInstruction;
	}
	/**
	* Sets the 32-bit instruction.
	*
	* @param instruction The new MIPS instruction to set
	*/	
	public void setRawInstruction(int instruction)
	{
		rawInstruction = instruction;
		// Validate? Job of the control unit?
	}
	
	
	/**
	* Returns the RS field of the MIPS instruction.
	* For use with R and I type instructions only.
	*/
	public int getRS() // For R and I Type
	{
		return (int) ((rawInstruction & 0x3E00000) >>> 21);
	}
	
	public void setRS(int rs)
	{
		rawInstruction = (rawInstruction & 0xFC1FFFFF) | ( rs << 21 );
	}
	
	/**
	* Returns the RT field of the MIPS instruction.
	* For use with R and I type instructions only.
	*/
	public int getRT() // For R and I Type
	{
		return (int) ((rawInstruction & 0x001F0000) >>> 16);
	}
	
	public void setRT(int rt)
	{
		rawInstruction = (rawInstruction & 0xFFE0FFFF) | ( rt << 16 );
	}
	
	/**
	* Returns the RD field of the MIPS instruction.
	* For use with R instructions only.
	*/
	public int getRD() // For R Type
	{
		return (int) ((rawInstruction & 0x0000F800) >>> 11);
	}
	
	public void setRD(int rd)
	{
		rawInstruction = (rawInstruction & 0xFFFF07FF) | ( rd << 11 );
	}
	
	/**
	* Returns the SHAMT field of the MIPS instruction.
	* For use with R type instructions only.
	*/
	public int getSHAMT() // For R Type
	{
		return (int) ((rawInstruction & 0x000007C0) >>> 6);
	}
	
	public void setSHAMT(int shamt)
	{
		rawInstruction = (rawInstruction & 0xFFFFF83F) | ( shamt << 6 );
	}
	/**
	* Returns the FUNCT field of the MIPS instruction.
	* For use with R type instructions only.
	*/
	public int getFUNCT() // For R Type
	{
		return (int) (rawInstruction & 0x0000003F);
	}
	
	public void setFUNCT(int funct)
	{
		rawInstruction = (rawInstruction & 0xFFFFFFC0) | funct;
	}
	/**
	* Returns the break code field of the MIPS instruction.
	* For use with R type with FUNCT = 13 instructions only.
	*/
	public int getBreakCode()
	{
		return (int) ((rawInstruction & 0x03FFFFC0) >>> 6);
	}
	
	public void setBreakCode(int breakCode)
	{
		rawInstruction = (rawInstruction & 0xFC00003F) | ( breakCode << 6 );
	}
		
	/**
	* Returns the Immediate/Address field of the MIPS instruction.
	* For use with I type instructions only.
	*/
	public int getImmAddr() // For I Type
	{
		return (int) (rawInstruction & 0x0000FFFF);
	}
	
	public void setImmAddr(int immAddr)
	{
		immAddr = immAddr & 0x0000FFFF;
		rawInstruction = (rawInstruction & 0xFFFF0000) | immAddr;
	}
	/**
	* Returns the Address field of the MIPS instruction.
	* For use with J type instructions only.
	*/	
	public int getAddr() // For J type
	{
		return rawInstruction & 0x03FFFFFF;
	}
	
	public void setAddr(int addr)
	{
		addr = addr & 0x03FFFFFF;
		rawInstruction = (rawInstruction & 0xFC000000) | addr;	
	}
	
}