package cs420.mipsilon.core;

public class ALUControl {

	int functionField;
	int controlOp;
	int ALUOperation;

	private boolean jr;

	public boolean getJr()
	{
		return jr;
	}

	void setFunction(int inFunction)
	throws CPUException
	{
		if(inFunction > 63)
			throw new CPUException( "ALU Control : Invalid function field : " + inFunction );

		functionField = inFunction;
	}

	void setControlOp(int newControlOp)
	throws CPUException
	{
		if(newControlOp > 3)
			throw new CPUException( "ALU Control : Invalid control operation : " + newControlOp );

		controlOp = newControlOp;
	}

	public int getFunction(int inFunction)
	{
		return functionField;
	}

	public int getControlOp()
	{
		return controlOp;
	}

	public int getALUOperation()
	throws CPUException
	{
		// reset
		jr = false;

		if(controlOp == 0) // loadword / storeword
			return ALU.ADD;
		if(controlOp == 1) // BranchEqual
			return ALU.SUB;
		if(controlOp == 2) // R-FORMAT
		{
			switch(functionField)
			{
				case 040 : // 100 000
					return ALU.ADD;
				case 042 : // 100 010
					return ALU.SUB;
				case 044 : // 100 100
					return ALU.AND;
				case 045 : // 100 101
					return ALU.OR;
				case 052 : // 101 010
					return ALU.SLT;
				case 010 : // 001 000  jr
					jr = true;
					return ALU.ADD;
			}

		}

		throw new CPUException( "ALU Control : Unsupported operation : " + controlOp );

	}



}
