package cs420.mipsilon;


public class Util{


	public static String padZero(String hexString, int pad)
	{
		StringBuffer strBuff = new StringBuffer();
		
		for(int i=0; i < (pad - hexString.length()); i++)
			strBuff.append("0");
		
		return strBuff.append(hexString).toString();
	}


}
