package com.sfy.common;

import java.io.*;


public class LasSerializer {
     public static  byte[] serialize(Object obj) throws IOException{
    	    ByteArrayOutputStream bos = new ByteArrayOutputStream();  
    	    ObjectOutputStream oos = new ObjectOutputStream(bos);  
    	    oos.writeObject(obj);  
    	    byte[] bytes = bos.toByteArray();  
    	    bos.close();  
    	    oos.close();
			return bytes;  
     }
    
     public static Object deSerialize(byte[] buf) throws Exception{	
	    	ByteArrayInputStream bos = null;
			ObjectInputStream ios = null;
			Object obj = null;
			try {
				 bos = new ByteArrayInputStream(buf);
				 ios = new ObjectInputStream(bos);
				 obj = ios.readObject();
			} catch (Exception e) {
				throw e;
			} finally {
				if(null != bos){
					bos.close();  
				}
				if(null != ios){
					ios.close();  
				}
			}
	    	return obj;
     }
}
