package com.htms;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LoopProcessor {
	StatementProcessor statementProcessor;
	LoopProcessor loopProcessor;
	IfProcessor ifProcessor;
	LoopProcessor(){
		statementProcessor = new StatementProcessor();
	}
	void loopProcessor(String block[],int blockN,int times,HashMap<String,String> map){
		//System.out.println("In Loop");
		int i,j=0;
		/*
		for(int k=0;k<blockN;++k){
			System.out.println(block[k]);
		}
		*/
		times = times*blockN;
		//System.out.println("times is "+times);
		for(i=0;i<times;++i){
			String blocks="";
			if(block[j].startsWith("<print>")){
				statementProcessor.statementProcess(block[j], map);
				//System.out.println(j);
			}else if(block[j].startsWith("<expression>")){
				statementProcessor.statementProcess(block[j], map);
			}else if(block[j].startsWith("<loop")){
				loopProcessor = new LoopProcessor();
				//System.out.println("The inner block is "+block[j]);
				Document doc = Jsoup.parse(block[j]);
				Element loopBlock = doc.select("loop").first();
				String stringTimes = loopBlock.attr("times");
				if(stringTimes.chars().allMatch(Character::isLetter)){
					stringTimes = map.get(stringTimes);
				}
				int itimes = Integer.parseInt(stringTimes)-1;
				String iextractBlock[]=new String[9999];
				int iextractBlockN=0;
				//System.out.println("In Block");
				for(int k=j;k<blockN-1;++k){
					iextractBlock[iextractBlockN++]=block[k];
				}
				loopProcessor.loopProcessor(iextractBlock,iextractBlockN,itimes, map);
				
			}
			j=(j+1)%blockN;
		}
		
	}
}
