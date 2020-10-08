package cs420.mipsilon.assembler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Assembler {

	PreProcessor pp;
	TextProcessor tp;

	public Assembler()	
	{
		pp = new PreProcessor();
		tp = new TextProcessor();
	}
	
	
	
	public void assemble(File file)
	throws
	AssemblerException,	
	FileNotFoundException,
	IOException	
	{
		pp.processFile(new FileInputStream(file));
		tp.process(pp);
	}	
	
	
	public void assemble(InputStream ins)
	throws
	AssemblerException,	
	FileNotFoundException,
	IOException	
	{
		pp.processFile(ins);
		tp.process(pp);
	}
	
	
	public Byte [] getData()
	{
		return pp.getData();
	}
	
	public Integer [] getText()
	{
		return tp.getText();
	}
	
}