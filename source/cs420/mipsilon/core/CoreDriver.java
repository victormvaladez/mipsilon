package cs420.mipsilon.core;

import java.io.File;
import cs420.mipsilon.Util;
import java.util.Iterator;

class CoreDriver{

	public static void main(String [] args)
	{
		Instruction inst = new Instruction(0x8c090004);
		
		int op = inst.getOpCode();		
		System.out.println("0x" + Util.padZero(Integer.toHexString( inst.getRawInstruction() ),8));
		inst.setOpCode(op);
		System.out.println("0x" + Util.padZero(Integer.toHexString( inst.getRawInstruction() ),8));
		
		
		inst.setRS(inst.getRS());
		System.out.println("0x" + Util.padZero(Integer.toHexString( inst.getRawInstruction() ),8));
		
		
		inst.setRT(inst.getRT());
		System.out.println("0x" + Util.padZero(Integer.toHexString( inst.getRawInstruction() ),8));
		
		inst.setRD(inst.getRD());
		System.out.println("0x" + Util.padZero(Integer.toHexString( inst.getRawInstruction() ),8));
		
		inst.setSHAMT(inst.getSHAMT());
		System.out.println("0x" + Util.padZero(Integer.toHexString( inst.getRawInstruction() ),8));
		
		inst.setFUNCT(inst.getFUNCT());
		System.out.println("0x" + Util.padZero(Integer.toHexString( inst.getRawInstruction() ),8));
		
		inst.setImmAddr(inst.getImmAddr());
		System.out.println("0x" + Util.padZero(Integer.toHexString( inst.getRawInstruction() ),8));
		
		inst.setAddr(inst.getAddr());
		System.out.println("0x" + Util.padZero(Integer.toHexString( inst.getRawInstruction() ),8));
		
	}                       
               
        
        public static void dispCPU(CPU cpu)
        {
        	Instruction inst = cpu.getIR();
        	ControlUnit cu = cpu.getControlUnit();
        	
        	System.out.print("OP: " + inst.getOpCode() + ", " );
        	System.out.print("RS: " + inst.getRS() + ", " );
        	System.out.print("RT: " + inst.getRT() + ", " );
        	System.out.print("RD: " + inst.getRD() + ", " );
        	System.out.print("FUNCT: " + inst.getFUNCT() + ", " );
        	System.out.print("ImmAddr: 0x" + Util.padZero(Integer.toHexString(inst.getImmAddr()),8) + "\n" );
        	
        	System.out.print("RegDst: " + bin(cu.getRegDst()) + ", " );
        	System.out.print("Branch: " + bin(cu.getBranch()) + ", " );
        	System.out.print("MemRead: " + bin(cu.getMemRead()) + ", " );
        	System.out.print("MemToReg: " + bin(cu.getMemToReg()) + ", " );        	
        	System.out.print("MemWrite: " + bin(cu.getMemWrite()) + ", " );
        	System.out.print("ALUSrc: " + bin(cu.getALUSrc()) + ", \n" );
        	System.out.print("RegWrite: " + bin(cu.getRegWrite()) + ", " );
        	System.out.print("ALUOP: " + cu.getALUOp() + ", \n" );
        	
        	System.out.println(Disassembler.getDisassembly( inst ) + "\n");
        }
        
        
        public static void dispRegs(Registers regs)
        {
        	for(int i = 0; i < 8; i++)
        	{
        		for(int j = 0; j < 4; j++)
        			System.out.print(Util.padZero(Integer.toString(4*i + j),2) + "  0x" + Util.padZero(Integer.toHexString(regs.getReg(4*i + j)),8) + "  ");
        		System.out.print("\n");
        	}        	
        	System.out.println("\nPC  0x" + Util.padZero(Integer.toHexString( regs.getPC() ), 8  )  + "  \n");	
        	
        	
        }
        
        public static String bin(boolean bool)
        {
        	if(bool)
        		return "1";
        	else
        		return "0";
        }
        
        
        //Util.padZero(Integer.toHexString(i*4),8)
                        
}                               
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
                                