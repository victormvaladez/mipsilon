package cs420.mipsilon.core;

public class Loader {

	private Memory textMem;
	private Memory dataMem;
	
	public Loader(Memory text, Memory data)
	{ 
		textMem = text;
		dataMem = data;
	}
	
	public void loadText(int [] text, int offset)
	{
		load(textMem, text, offset);
	}
	
	public void loadText(Integer [] text, int offset)
	{
		load(textMem, text, offset);
	}
	
	public void loadData(int [] data, int offset)
	{
		load(dataMem, data, offset);
	}
	
	public void loadData(Byte [] data, int offset)
	{
		load(dataMem, data, offset);
	}
	
	private void load(Memory mem, int [] array, int offset)
	{
		for(int i = 0; i < array.length; i++)
		{
			mem.write((i*4) + offset, array[i]);
		}
		
	}
	
	private void load(Memory mem, Integer [] array, int offset)
	{
		for(int i = 0; i < array.length; i++)
		{
			mem.write((i*4) + offset, array[i].intValue());
		}
		
	}
	
	private void load(Memory mem, Byte [] array, int offset)
	{
		for(int i = 0; i < array.length; i++)
		{
			mem.write(i + offset, array[i]);
		}
	}
	
	

}
