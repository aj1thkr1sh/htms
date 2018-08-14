package com.htms;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BlockProcessor {
	
	LoopProcessor loopProcessor;
	IfProcessor ifProcessor;
	
	BlockProcessor(){
		loopProcessor = new LoopProcessor();
		ifProcessor = new IfProcessor();
	}
	
	void blockProcess(String[] block,int blockN,HashMap<String,String> map){
		if(block[0].startsWith("<loop")){
			Document doc = Jsoup.parse(block[0]);
			Element loopBlock = doc.select("loop").first();
			String stringTimes = loopBlock.attr("times");
			if(stringTimes.chars().allMatch(Character::isLetter)){
				stringTimes = map.get(stringTimes);
			}
			int times = Integer.parseInt(stringTimes);
			String extractBlock[]=new String[9999];
			int extractBlockN=0;
			//System.out.println("In Block");
			for(int j=1;j<blockN-1;++j){
				extractBlock[extractBlockN++]=block[j];
			}
			loopProcessor.loopProcessor(extractBlock,extractBlockN,times, map);
		}else if(block[0].startsWith("<if")){
			Document doc = Jsoup.parse(block[0]);
			Element loopBlock = doc.select("if").first();
			String stringCondition = loopBlock.attr("condition");
			ScriptEngineManager scriptEngineManger = new ScriptEngineManager();
			ScriptEngine engine = scriptEngineManger.getEngineByName("JavaScript");
			if(stringCondition.contains("==")){
				StringTokenizer tokens=new StringTokenizer(stringCondition,"==");
				String variableLeft = tokens.nextToken();
				String variableRight = tokens.nextToken();
				if(variableLeft.chars().allMatch(Character::isLetter)){
					variableLeft = map.get(variableLeft);
				}
				if(variableRight.chars().allMatch(Character::isLetter)){
					variableRight = map.get(variableRight);
				}
				String fullExpression = variableLeft+"=="+variableRight;
				try {
					String booleanString=String.valueOf(engine.eval(fullExpression));
					String extractBlock[]=new String[9999];
					int extractBlockN=0;
					//System.out.println("In Block");
					for(int j=1;j<blockN-1;++j){
						extractBlock[extractBlockN++]=block[j];
					}
					//System.out.println(extractBlockN);
					ifProcessor.ifProcess(extractBlock,extractBlockN,booleanString,map);
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(stringCondition.contains("!=")){
				StringTokenizer tokens=new StringTokenizer(stringCondition,"!=");
				String variableLeft = tokens.nextToken();
				String variableRight = tokens.nextToken();
				if(variableLeft.chars().allMatch(Character::isLetter)){
					variableLeft = map.get(variableLeft);
				}
				if(variableRight.chars().allMatch(Character::isLetter)){
					variableRight = map.get(variableRight);
				}
				String fullExpression = variableLeft+"!="+variableRight;
				try {
					String booleanString=String.valueOf(engine.eval(fullExpression));
					String extractBlock[]=new String[9999];
					int extractBlockN=0;
					//System.out.println("In Block");
					for(int j=1;j<blockN-1;++j){
						extractBlock[extractBlockN++]=block[j];
					}
					//System.out.println(extractBlockN);
					ifProcessor.ifProcess(extractBlock,extractBlockN,booleanString,map);
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/*
			if(stringTimes.chars().allMatch(Character::isLetter)){
				stringTimes = map.get(stringTimes);
			}
			*/
		}else if(block[0].startsWith("<eif")){
			Document doc = Jsoup.parse(block[0]);
			Element loopBlock = doc.select("eif").first();
			String stringCondition = loopBlock.attr("condition");
			ScriptEngineManager scriptEngineManger = new ScriptEngineManager();
			ScriptEngine engine = scriptEngineManger.getEngineByName("JavaScript");
			if(stringCondition.contains("==")){
				StringTokenizer tokens=new StringTokenizer(stringCondition,"==");
				String variableLeft = tokens.nextToken();
				String variableRight = tokens.nextToken();
				if(variableLeft.chars().allMatch(Character::isLetter)){
					variableLeft = map.get(variableLeft);
				}
				if(variableRight.chars().allMatch(Character::isLetter)){
					variableRight = map.get(variableRight);
				}
				String fullExpression = variableLeft+"=="+variableRight;
				try {
					String booleanString=String.valueOf(engine.eval(fullExpression));
					String extractBlock[]=new String[9999];
					int extractBlockN=0;
					//System.out.println("In Block");
					for(int j=0;j<blockN;++j){
						extractBlock[extractBlockN++]=block[j];
					}
					//System.out.println(extractBlockN);
					ifProcessor.ifeProcess(extractBlock,extractBlockN,booleanString,map);
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(stringCondition.contains("!=")){
				StringTokenizer tokens=new StringTokenizer(stringCondition,"!=");
				String variableLeft = tokens.nextToken();
				String variableRight = tokens.nextToken();
				if(variableLeft.chars().allMatch(Character::isLetter)){
					variableLeft = map.get(variableLeft);
				}
				if(variableRight.chars().allMatch(Character::isLetter)){
					variableRight = map.get(variableRight);
				}
				String fullExpression = variableLeft+"!="+variableRight;
				try {
					String booleanString=String.valueOf(engine.eval(fullExpression));
					String extractBlock[]=new String[9999];
					int extractBlockN=0;
					//System.out.println("In Block");
					for(int j=0;j<blockN;++j){
						extractBlock[extractBlockN++]=block[j];
					}
					//System.out.println(extractBlockN);
					ifProcessor.ifeProcess(extractBlock,extractBlockN,booleanString,map);
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		/*
		if(block.startsWith("<loop")){
			Document doc = Jsoup.parse(block);
			Element loopBlock = doc.select("loop").first();
			int times = Integer.parseInt(loopBlock.attr("times"));
			String blockString = loopBlock.html();
			//System.out.println(blockString+" "+times);
			loopProcessor.loopProcessor(blockString, times, map);
		}
		*/
	}

}
