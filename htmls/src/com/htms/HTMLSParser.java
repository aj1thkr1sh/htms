package com.htms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLSParser {

	HashMap<String,String> map = new HashMap<String,String>();
	
	File name;
	
	String buffer[];
	int bufferN;
	
	StatementProcessor statementProcessor;
	BlockProcessor blockProcessor;
	
	public HTMLSParser(File name){
		this.name = name;
		buffer=new String[9999];
		bufferN=0;
		statementProcessor = new StatementProcessor();
		blockProcessor = new BlockProcessor();
	}
	
	void parser(){
		String s="";
		int i;
        FileInputStream fis;
		try {
			fis = new FileInputStream(name);
	        while(fis.available()>0){
	        	s+=(char)fis.read();
	        }
	        StringTokenizer tokens = new StringTokenizer(s,"\n");
	        while(tokens.hasMoreTokens()){
	        	buffer[bufferN++]=tokens.nextToken().trim();
	        }
	        
	        /*
	        for(int i=0;i<bufferN;++i){
	        	System.out.println(i+"  : "+buffer[i]);
	        }
	        */
	        for(i=0;i<bufferN;++i){
	        	if(	   buffer[i].equals("<htms>") || buffer[i].equals("</htms>")
	        		|| buffer[i].equals("<head>") || buffer[i].equals("</head>")
	        		|| buffer[i].equals("<body>")|| buffer[i].equals("</body>") ){
	        		continue;
	        	}else if(buffer[i].startsWith("<assign")){
	        		statementProcessor.statementProcess(buffer[i],map);
	        	}else if(buffer[i].startsWith("<print>")){
	        		statementProcessor.statementProcess(buffer[i], map);
	        	}else if(buffer[i].startsWith("<loop")){
	        		String startTag="",endTag="",temp;
	        		String blockString[]=new String[9999];
	        		int blockStringN=0;
	        		/*
	        		temp = buffer[i];
	        		int nn=temp.length();
	        		for(int j=0;j<nn;++j){
	        			if(temp.charAt(j)==' '){
	        				break;
	        			}
	        			if(j==1){
	        				endTag+="/";
	        			}
	        			startTag+=temp.charAt(j);
	        			endTag+=temp.charAt(j);
	        		}
	        		startTag+=">";
	        		endTag+=">";
	        		*/
	        		int t=0;
	        		for(;i<bufferN;++i){
	        			if(buffer[i].startsWith("<loop")){
	        				t++;
	        			}else if(buffer[i].equals("</loop>")){
	        				t--;
	        			}
	        			blockString[blockStringN++]=buffer[i];
	        			if(t==0){
	        				break;
	        			}
	        		}
	        		//System.out.println(blockString);
	        		blockProcessor.blockProcess(blockString,blockStringN, map);
	        	}else if(buffer[i].startsWith("<expression>")){
	        		statementProcessor.statementProcess(buffer[i], map);
	        	}else if(buffer[i].startsWith("<if")){
	        		String blockString[]=new String[9999];
	        		int blockStringN=0;
	        		/*
	        		temp = buffer[i];
	        		int nn=temp.length();
	        		for(int j=0;j<nn;++j){
	        			if(temp.charAt(j)==' '){
	        				break;
	        			}
	        			if(j==1){
	        				endTag+="/";
	        			}
	        			startTag+=temp.charAt(j);
	        			endTag+=temp.charAt(j);
	        		}
	        		startTag+=">";
	        		endTag+=">";
	        		*/
	        		int t=0;
	        		for(;i<bufferN;++i){
	        			if(buffer[i].startsWith("<if")){
	        				t++;
	        			}else if(buffer[i].equals("</if>")){
	        				t--;
	        			}
	        			blockString[blockStringN++]=buffer[i];
	        			if(t==0){
	        				break;
	        			}
	        		}
	        		//System.out.println(blockString);
	        		blockProcessor.blockProcess(blockString,blockStringN, map);
	        	}else if(buffer[i].startsWith("<eif")){
	        		String blockString[]=new String[9999];
	        		int blockStringN=0;
	        		/*
	        		temp = buffer[i];
	        		int nn=temp.length();
	        		for(int j=0;j<nn;++j){
	        			if(temp.charAt(j)==' '){
	        				break;
	        			}
	        			if(j==1){
	        				endTag+="/";
	        			}
	        			startTag+=temp.charAt(j);
	        			endTag+=temp.charAt(j);
	        		}
	        		startTag+=">";
	        		endTag+=">";
	        		*/
	        		int t=0;
	        		for(;i<bufferN;++i){
	        			if(buffer[i].startsWith("<eif")){
	        				t++;
	        			}else if(buffer[i].equals("</else>")){
	        				t--;
	        			}
	        			blockString[blockStringN++]=buffer[i];
	        			if(t==0){
	        				break;
	        			}
	        		}
	        		//System.out.println(blockString);
	        		/*
	        		for(int g=0;g<blockStringN;++g){
	        			System.out.println(blockString[g]);
	        		}
	        		*/
	        		blockProcessor.blockProcess(blockString,blockStringN, map);
	        	}
	        }
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	
	public void storeVariables(){
        try {
			Document doc = Jsoup.parse(name,"UTF-8");
			Elements text = doc.getElementsByTag("assign");
			for(Element element : text){
				map.put(element.attr("name"),element.attr("value"));
			}
			System.out.println(map);
			Elements div1 = doc.getElementsByTag("div");
			System.out.println(div1.html());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
