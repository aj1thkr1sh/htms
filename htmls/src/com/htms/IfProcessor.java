package com.htms;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class IfProcessor {
	StatementProcessor statementProcessor;
	LoopProcessor loopProcessor;
	IfProcessor(){
		statementProcessor = new StatementProcessor();
		loopProcessor = new LoopProcessor();
	}
	
	void ifProcess(String[] block,int blockN,String booleanString,HashMap<String,String> map){
		if(booleanString.equals("true")){
			//System.out.println("In true");
			for(int i=0;i<blockN;++i){
				if(block[i].startsWith("<print>")){
					statementProcessor.statementProcess(block[i], map);
				}else if(block[i].startsWith("<expression>")){
					statementProcessor.statementProcess(block[i], map);
				}else if(block[i].startsWith("<loop")){
					loopProcessor = new LoopProcessor();
					//System.out.println("The inner block is "+block[j]);
					Document doc = Jsoup.parse(block[i]);
					Element loopBlock = doc.select("loop").first();
					String stringTimes = loopBlock.attr("times");
					if(stringTimes.chars().allMatch(Character::isLetter)){
						stringTimes = map.get(stringTimes);
					}
					int itimes = Integer.parseInt(stringTimes)-1;
					String iextractBlock[]=new String[9999];
					int iextractBlockN=0;
					//System.out.println("In Block");
					for(int k=i;k<blockN-1;++k){
						iextractBlock[iextractBlockN++]=block[k];
					}
					loopProcessor.loopProcessor(iextractBlock,iextractBlockN,itimes, map);
					
					
				}
			}
		}
	}
	void ifeProcess(String[] block,int blockN,String booleanString,HashMap<String,String> map){
		String ifBlock[]=new String[9999];
		String elseBlock[]=new String[9999];
		int ifBlockN=0;
		int elseBlockN=0;
		int i=0;
		while(!block[i].equals("</eif>")){
			if(i==0){
				i++;
				continue;
			}
			ifBlock[ifBlockN++]=block[i++];
		}
		i+=2;
		while(!block[i].equals("</else>")){
			if(i==0){
				i++;
				continue;
			}
			elseBlock[elseBlockN++]=block[i++];
		}
		/*
		System.out.println("If Block");
		for(i=0;i<ifBlockN;++i){
			System.out.println(ifBlock[i]);
		}
		System.out.println("Else Block");
		for(i=0;i<elseBlockN;++i){
			System.out.println(elseBlock[i]);
		}
		*/
		if(booleanString.equals("true")){
			//System.out.println("In true");
			for(i=0;i<ifBlockN;++i){
				if(ifBlock[i].startsWith("<print>")){
					statementProcessor.statementProcess(ifBlock[i], map);
				}else if(ifBlock[i].startsWith("<expression>")){
					statementProcessor.statementProcess(ifBlock[i], map);
				}else if(ifBlock[i].startsWith("<loop")){
					loopProcessor = new LoopProcessor();
					//System.out.println("The inner block is "+block[j]);
					Document doc = Jsoup.parse(ifBlock[i]);
					Element loopBlock = doc.select("loop").first();
					String stringTimes = loopBlock.attr("times");
					if(stringTimes.chars().allMatch(Character::isLetter)){
						stringTimes = map.get(stringTimes);
					}
					int itimes = Integer.parseInt(stringTimes)-1;
					String iextractBlock[]=new String[9999];
					int iextractBlockN=0;
					//System.out.println("In Block");
					for(int k=i;k<ifBlockN-1;++k){
						iextractBlock[iextractBlockN++]=ifBlock[k];
					}
					loopProcessor.loopProcessor(iextractBlock,iextractBlockN,itimes, map);
					
					
				}
			}
		}else if(booleanString.equals("false")){
			for(i=0;i<elseBlockN;++i){
				if(elseBlock[i].startsWith("<print>")){
					statementProcessor.statementProcess(elseBlock[i], map);
				}else if(elseBlock[i].startsWith("<expression>")){
					statementProcessor.statementProcess(elseBlock[i], map);
				}else if(elseBlock[i].startsWith("<loop")){
					loopProcessor = new LoopProcessor();
					//System.out.println("The inner block is "+block[j]);
					Document doc = Jsoup.parse(elseBlock[i]);
					Element loopBlock = doc.select("loop").first();
					String stringTimes = loopBlock.attr("times");
					if(stringTimes.chars().allMatch(Character::isLetter)){
						stringTimes = map.get(stringTimes);
					}
					int itimes = Integer.parseInt(stringTimes)-1;
					String iextractBlock[]=new String[9999];
					int iextractBlockN=0;
					//System.out.println("In Block");
					for(int k=i;k<elseBlockN-1;++k){
						iextractBlock[iextractBlockN++]=elseBlock[k];
					}
					loopProcessor.loopProcessor(iextractBlock,iextractBlockN,itimes, map);
					
					
				}
			}
		}

	}
}
