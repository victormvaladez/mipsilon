package cs420.mipsilon.core;

import java.util.Observable;
import java.util.Observer;

public class CPU extends Observable {
	
	// Create all the components of the CPU
	private Registers registers	= new Registers();
	private ALU alu			= new ALU();
	private ALUControl aluControl	= new ALUControl();
	private ControlUnit controlUnit = new ControlUnit();
	
	private Instruction ir; //instruction register
		
	// References to text and data memory
	private Memory textSegment, dataSegment;
	
	public CPU(Memory text, Memory data)
	{
		super();
		textSegment = text;
		dataSegment = data;		
	}
	
	public void reset()
	{			
		//set all to zero
		try{
			for(int i = 0; i < 32; i++)
				registers.setReg(i, 0);
		}
		catch(CPUException ce) { }
		
		registers.setPC(0);
		
		setChanged();
		notifyObservers(new MIPSObservation("CPU_RESET", null));
		setChanged();
		notifyObservers(new MIPSObservation("PC_CHANGE", new Integer(0)));
	}
	
	public Instruction getIR()
	{
		return ir;
	}
				
	public Registers getRegisters()
	{
		return registers;
	}
	
	public ALU getALU()
	{
		return alu;
	}
	
	public ALUControl getALUControl()
	{
		return aluControl;
	}
	
	public ControlUnit getControlUnit()
	{
		return controlUnit;
	}	
	
	public void execute()
	throws CPUException
	{
		// move copy instruction in memory location PC to the IR register
		ir = new Instruction(textSegment.read(registers.getPC()));
		
		controlUnit.setOpCode(ir.getOpCode());
		aluControl.setFunction(ir.getFUNCT());
		aluControl.setControlOp( controlUnit.getALUOp() );
		
		alu.setOperation( aluControl.getALUOperation() );
		
		registers.setReadRegister1(ir.getRS());
		registers.setReadRegister2(ir.getRT());
		
		
		if(controlUnit.getRegDst())    ///RegDst
			registers.setWriteRegister(ir.getRD());
		else
			registers.setWriteRegister(ir.getRT());
		
		alu.setInputA(registers.getReadData1() ); // read data1
		
		if(controlUnit.getALUSrc())   // ALUSrc
			alu.setInputB(signExtend(ir.getImmAddr())); // immediate value
		else
			alu.setInputB(registers.getReadData2() );  // read data2
		
		
		dataSegment.setAddress(alu.getResult());
		dataSegment.setWriteData(registers.getReadData2());
		
				
		if(controlUnit.getMemWrite())
			dataSegment.write();			
				
		if(controlUnit.getMemRead())
			dataSegment.read();
				
		//----------MUX-------------------
		if(controlUnit.getMemToReg())
			registers.setWriteData(dataSegment.getReadData());
		else
			registers.setWriteData(alu.getResult());		
		
		if(controlUnit.getRegWrite()) // Write to the register if applicable
			registers.write();
					
		// increment the PC (different on branch or jump instruxction)
		if(aluControl.getJr())
			registers.setPC(  alu.getResult() );
		else if(controlUnit.getJump()) // unconditinoal
		{
			if(controlUnit.getLink())
				registers.link();
						
			registers.setPC(  (registers.getPC() & 0xF0000000) + (ir.getAddr() << 2) );
		}
		else if(controlUnit.getBranch() && alu.getZero() )
			registers.setPC( registers.getPC() + ( ir.getImmAddr() << 2));		
		else
			registers.setPC( registers.getPC() + 4);
		
		dispatchObservations();		
	}	
	
	void dispatchObservations()
	{		
		//always?
		setChanged();
		notifyObservers(new MIPSObservation("PC_CHANGE", new Integer(registers.getPC() )));
		
		if(controlUnit.getRegWrite())
		{
			setChanged();
			notifyObservers(new MIPSObservation("REG_WRITE", new Integer(registers.getWriteRegister() )));
		}
		if(controlUnit.getLink())
		{
			setChanged();
			notifyObservers(new MIPSObservation("REG_WRITE", new Integer(31)));
		}
		if(controlUnit.getMemRead())
		{
			setChanged();
			notifyObservers(new MIPSObservation("MEM_READ", new Integer(alu.getResult())));
		}
		if(controlUnit.getMemWrite())
		{
			setChanged();
			notifyObservers(new MIPSObservation("MEM_WRITE", new Integer(alu.getResult())));
		}
		
	}
	
	
	private int signExtend(int value)
	{		
		if( (value & 0x00008000) !=0 )
			return (value | 0xFFFF0000);
		else
			return value;
	}
}
