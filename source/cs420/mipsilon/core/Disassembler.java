package cs420.mipsilon.core;

/**
 * <code>Disassembler</code> Disassembles a 32-bit MIPS cs420.mipsilon.core.Instruction
 *   
 */

public class Disassembler{
		                                    
	private static final String [] IJsymbols = { 
							"" , ""  , "j" , "jal" , "beq",
							"bne" , "blez"  , "bgtz" , "addi" , "addiu",
							"slti" , "sltiu"  , "andi" , "ori" , "xori",
							"lui" , ""  , "" , "" , "",
							"" , "trap"  , "" , "" , "llo",
							"lhi" , ""  , "" , "" , "lwl",
							"" , ""  , "lb" , "lh" , "",
							"lw" , "lbu"  , "lhu" , "lwr" , "",
							"sb" , "sh"  , "" , "sw" , "",
							};
						
	private static final String [] Rsymbols = { 
							"sll" , ""  , "srl" , "sra" , "sllv", "srlv",
							"srav", "", "jr", "jalr"    , ""    , ""    ,
							"syscall"    , "break"  , ""   , "", "mfhi" , "mthi",
							"mflo","mtlo", "", "", "", "",
							"mult","multu", "div", "divu", "", "",
							"","", "add", "addu", "sub", "subu",
							"and","or", "xor", "nor", "", "sltu",
							"slt"
							};
	/**
	* Returns a string representation of a disassembled 32-bit MIPS instruction.
	* 
	* @param instruction The 32-bit MIP instruction to disassemble.
	*/
	public static String getDisassembly(Instruction instruction )
	{
		return getMnemonic(instruction) + " "+ getTail(instruction, false);	
	}
	
	/**
	* Returns a string representation of a disassembled 32-bit MIPS instruction
	* 
	* @param instruction The 32-bit MIP instruction to disassemble.
	* @param registerMneumonics disassemble with or without register mneumonics.
	*/
	public static String getDisassembly(Instruction instruction, boolean registerMneumnoics)
	{
		return getMnemonic(instruction) + " "+ getTail(instruction, registerMneumnoics);
	}
	
	private static String getMnemonic(Instruction instruction)
	{
		
		if( instruction.getRawInstruction() == 0x00000000)
			return "nop";
		else if(instruction.getOpCode() == 0)
			return Rsymbols[instruction.getFUNCT()];
		else if(instruction.getOpCode() > 1)
			return IJsymbols[instruction.getOpCode()];
		else
			return "-";
	}
	
	private static String getTail(Instruction instruction, boolean regM)
	{		
		if(instruction.getRawInstruction() == 0x00000000 /*noop*/ && instruction.getFUNCT() == 12 /*syscall*/)
			return "";
		if(instruction.getRawInstruction() == 0x00000000)
			return "";
		else if(instruction.getFUNCT() == 13 && instruction.getOpCode() == 0) //break
			return ("0x" + Integer.toHexString(instruction.getBreakCode()));
		else if(instruction.getOpCode() == 0)
			return getRTail(instruction, regM);
		else if(instruction.getOpCode() > 1)
			return getIJTail(instruction, regM);
		else
			return "Unsupported Instruction";
	}
	
		
	private static String getRTail(Instruction instruction, boolean regM)
	{
		int funct = instruction.getFUNCT();
		
		
		if(funct >= 32 && funct <= 42)  //ArithLog
			return getRegName(instruction.getRD(), regM) + ", " + getRegName(instruction.getRS(), regM) + ", " + getRegName(instruction.getRT(), regM);
		else if(funct >= 24 && funct <= 27) // DivMult
			return getRegName(instruction.getRS(), regM) + ", " + getRegName(instruction.getRT(), regM);
		else if(funct >= 0 && funct <= 3) // Shift
			return  getRegName(instruction.getRD(), regM) + ", " + getRegName(instruction.getRT(), regM) + ", " + instruction.getSHAMT();
		else if(funct >= 4 && funct <=7 && !( funct == 5) ) //ShiftV
			return  getRegName(instruction.getRD(), regM) + ", " + getRegName(instruction.getRT(), regM) + ", " + getRegName(instruction.getRT(), regM);
		else if(funct == 8 || funct == 9) // JumpR
			return getRegName(instruction.getRS(), regM);
		else if(funct == 16 || funct == 18) // MoveFrom
			return getRegName(instruction.getRD(), regM);
		else if(funct == 17 || funct == 19) // MoveTo
			return getRegName(instruction.getRS(), regM);		
		else
			return "Unsupported Instruction";
	}
	
	private static String getIJTail(Instruction instruction, boolean regM)
	{
		
		int op = instruction.getOpCode();
					
		if(op >= 8 && op <= 14) // ArithLogi
			return getRegName(instruction.getRT(), regM) + ", " + getRegName(instruction.getRS(), regM) + ", " + instruction.getImmAddr();
		if(op == 24 || op == 25 || op == 15 || op == 34 || op == 38) //LoadI *** lhi, llo?
			return getRegName(instruction.getRT(), regM) + " , 0x" + Integer.toHexString(instruction.getImmAddr());
		if(op == 4 || op == 5) // Branch
			return getRegName(instruction.getRS(), regM) + ", " + getRegName(instruction.getRT(), regM) + ", " + (instruction.getImmAddr() << 2) + " [label]" ; //*** getLabel?
		if(op == 6 || op == 7) //BranchZ
			return getRegName(instruction.getRS(), regM) + ", " + (instruction.getImmAddr() << 2) + " [label]" ; //*** getLabel
		if(op == 32 || op == 33 || (op >= 35 && op <= 37) || op == 40 || op == 43) // LoadStore
			return getRegName(instruction.getRT(), regM) + ", " + instruction.getImmAddr() + "(" + getRegName(instruction.getRS(), regM) + ")";		
		if(op == 2 || op == 3) //Jump
			return "0x" + Integer.toHexString(instruction.getAddr() << 2) + " [label]";
			
		return "Unsupported Instruction";
	}
	
	private static String getRegName(int reg, boolean regM)
	{
		if(regM)
			return Registers.getMnemonic(reg);
		else
			return "$" + reg;
	}
	
	
}