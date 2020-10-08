package cs420.mipsilon.core;


public class ALU{

int inputA = 0;
int inputB = 0;
int operation = 0;
int result = 0;
boolean zero = false;
boolean overflow = false;
boolean carry = false;

// ALU Control Inputs
public static final int AND = 0; // 000
public static final int OR  = 1; // 001
public static final int ADD = 2; // 010
public static final int SUB = 6; // 110
public static final int SLT = 7; // 111

ALU()
{ }

void setInputA(int input)
throws CPUException
{
	inputA = input;	
	updateResult();
}

void setInputB(int input)
throws CPUException
{
	inputB = input;
	updateResult();	
}

void setOperation(int op)
throws CPUException
{
	if(op > 7)
		throw new CPUException( "ALU : Invalid control operation : " + op );	
	operation = op;
	updateResult();
}

public int getInputA()
{
	return inputA;
}

public int getInputB()
{
	return inputB;
}

public int getOperation()
{
	return operation;
}

public boolean getZero()
{
	return zero;
}

public boolean getCarry()
{
	return carry;
}

public int getResult()
{
	return result;
}

private void updateResult()
throws CPUException
{
	zero = false;
	carry = false;
	overflow = false;
	
	switch(operation)
	{
		case AND:
			result = (inputA & inputB);
		break;
		case OR :
			result = (inputA | inputB);
		break;
		case ADD :
			result = (inputA + inputB); //check for carry, etc?
		break;
		case SUB :
			result = (inputA - inputB); //check overflow etc?			
		break;	
		case SLT :
			if((inputA - inputB) > 0)  // ********* verify?
				result = 0;
			else
				result = 1;
		break;
		default :		
		throw new CPUException( "ALU : Unsupported operation : " + operation );
		
	}
	if( result == 0)
		zero = true;
	
}


}
