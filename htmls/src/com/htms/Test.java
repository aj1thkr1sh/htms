package com.htms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {
    static String tagName="";
    static String etagName="";
    static String s = "";
    static int i=0;
	static int index = 0;
	static int n;
	static char ch[];
	static TagCounter<String,Integer> tagCounter = new TagCounter<String,Integer>(0);
	public static void main(String[] args) throws IOException {
		


        File file = new File("D:\\AllWorks\\Works\\htmls\\htmls\\src\\com\\htms\\file.htms");
        FileInputStream fis = new FileInputStream(file);

        while(fis.available()>0){
        	s+=(char)fis.read();
        }
        System.out.println(s);
        s=s.replaceAll("\n","").replaceAll("\r","");
        System.out.println(s);
        n=s.length();

        //System.out.println(s);
        ch=s.toCharArray();
        System.out.println("Enter");
        int start = 0;

        process(0);

        


	}
	
	static void process(int start){
        for(i=start;i<n;++i){
	    	if((i+1)<n && ch[i]=='<' && ch[i+1]!='/'){
	    		//System.out.println("1");
	    		if(tagCounter.get(tagName)==0){
	    			index = i;
	    		}
	    		//tagName="";
	    		while(ch[i]!='>'){
	    			if(ch[i]=='<'){
	    				i++;
	    				continue;
	    			}
	    			tagName+=ch[i++];
	    		}
	    		System.out.println("Tag Name  : "+tagName);
	    		tagCounter.put(tagName,tagCounter.get(tagName)+1);
	    		//System.out.println("char is  "+ch[i]+" "+ch[i+1]);
	    		//System.out.println("Tags  : "+tags);
	    	}else if((i+1)<n && ch[i]=='<' && ch[i+1]=='/'){
	    		i+=2;
	    		//etagName="";
	    		while(ch[i]!='>'){
	    			if(tagCounter.get(tagName)==1){
	    				etagName+=ch[i++];
	    			}else{
	    				i++;
	    			}
	    		}
	    		System.out.println("ETag Name : "+etagName);
	    	}
	    	if(etagName.equals(tagName)){
	    		tagCounter.put(tagName,tagCounter.get(tagName)-1);
	    		if(tagCounter.get(tagName)==0){
	    			String sub = s.substring(index,i+1);
	    			System.out.println("Sub is    : "+sub);
	    			index=i+1;
	    			tagName=etagName="";
	    			i++;

	    		}
	    	}

        }
	}
}


