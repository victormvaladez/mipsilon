package cs420.mipsilon.core;



public class ControlUnit {

	private int opCode;

	private boolean regDst, branch, memRead, memToReg, memWrite, ALUSrc, regWrite, jump, link;
	private int controlOp;

	ControlUnit()
	{ }

	void setOpCode(int code)
	throws CPUException
	{
		if(code > 63)  // larger than the 6-bit field
			throw new CPUException( "Invalid OpCode : " + opCode );

		opCode = code;
		// reset all
		regDst   = false;
		branch   = false;
		memRead  = false;
		memToReg = false;
		memWrite = false;
		ALUSrc   = false;
		regWrite = false;
		jump     = false;
		link  = false;
		controlOp = 0;

		switch(opCode)
		{
			case 0 : // R-Type
				regDst = true;
				regWrite = true;
				controlOp = 2;
			break;
			case 35 : // 100011 LW
				ALUSrc = true;
				memToReg = true;
				regWrite = true;
				memRead = true;
				controlOp = 0;
			break;
			case 43 : //101011 SW
				ALUSrc = true;
				memWrite = true;
				controlOp = 0;
			break;
			case 4 : // 000 100 BEQ
				branch = true;
				controlOp = 1;
			break;
			case 2 : //000 010 Jump
				jump = true;
			break;
			case 3 : // Jump and Link
				jump = true;
				link = true;
			break;
			case 8 : //addi
				regWrite = true;
				ALUSrc = true;
				controlOp = 0;
			break;
			default :
			throw new CPUException( "ControlUnit Error: Unsupported operation : " + opCode );
		}


	}

	public boolean getBranch()
	{
		return branch;
	}

	public boolean getJump()
	{
		return jump;
	}

	public boolean getLink()
	{
		return link;
	}

	public boolean getRegDst()
	{
		return regDst;
	}

	public boolean getMemRead()
	{
		return memRead;
	}

	public boolean getMemToReg()
	{
		return memToReg;
	}

	public boolean getMemWrite()
	{
		return memWrite;
	}

	public boolean getALUSrc()
	{
		return ALUSrc;
	}

	public boolean getRegWrite()
	{
		return regWrite;
	}

	public int getALUOp()
	{
		return controlOp;
	}

	public String toString()
	{

		StringBuffer str = new StringBuffer();
		str.append("( [" + opCode +"], ");
		str.append("(" + regDst  + "), ");
		str.append("(" + branch + "), ");
		str.append("(" + memRead + "), ");
		str.append("(" + memToReg + "), ");
		str.append("(" + memWrite + "), ");
		str.append("(" + ALUSrc + "), ");
		str.append("(" + regWrite + "), ");
		str.append("(" + jump + "), ");
		str.append("(" + link + "), ");

		return str.toString();
	}

}