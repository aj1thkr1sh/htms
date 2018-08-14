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

public class StatementProcessor {
	
	
	StatementProcessor(){
	}
	
	void statementProcess(String statement,HashMap<String,String> map){
		if(statement.startsWith("<assign")){
			Document doc = Jsoup.parse(statement);
			Elements text = doc.getElementsByTag("assign");
			for(Element element : text){
				map.put(element.attr("name"),element.attr("value"));
			}
		}else if(statement.startsWith("<print>")){
			Document doc = Jsoup.parse(statement);
			Elements text = doc.getElementsByTag("print");
			for(Element element : text){
				String store = "";
				String variableName="";
				char content[] = element.text().toCharArray();
				int i,n=content.length;
				for(i=0;i<n;++i){
					if(content[i]=='{'){
						++i;
						while(content[i]!='}'){
							variableName+=content[i++];
						}
						//System.out.println("Variable name  : "+variableName);
						store+=map.get(variableName);
						variableName="";
					}else{
						store+=content[i];
					}
				}
				System.out.println(store);
			}
		}else if(statement.startsWith("<expression>")){
			Document doc = Jsoup.parse(statement);
			Elements text = doc.getElementsByTag("expression");
			String variableName="";
			for(Element element : text){
				ScriptEngineManager scriptEngineManger = new ScriptEngineManager();
				ScriptEngine engine = scriptEngineManger.getEngineByName("JavaScript");
				try {
					String expression = element.text();
					String fullExpression="";
					if(expression.contains("=")){
						StringTokenizer equalToken = new StringTokenizer(expression,"=");
						variableName = equalToken.nextToken();
						String rightExpression = equalToken.nextToken();
						//System.out.println(rightExpression);
						char rightExpressionch[]=rightExpression.toCharArray();
						String rExpression = "";
						int expressionN=rightExpressionch.length;
						for(int e=0;e<expressionN;++e){
							String etoken = "";
							while((e<expressionN)){
								if((rightExpressionch[e]=='+' || rightExpressionch[e]=='-' || rightExpressionch[e]=='*' || rightExpressionch[e]=='/' || rightExpressionch[e]=='%')){
									break;
								}else{
									etoken+=rightExpressionch[e];
								}
								e++;
							}
							if(etoken.chars().allMatch(Character::isLetter)){
								etoken=map.get(etoken);
								//System.out.println("etoken in regex  : "+etoken);
							}
							//System.out.println("Etoken is  : "+etoken);
							fullExpression+=etoken;
							if(e<expressionN){
								fullExpression+=rightExpressionch[e];
							}
							//++e;
							//System.out.println("Right Expression  : "+fullExpression);
						}
						String evaluation = String.valueOf(engine.eval(fullExpression));
						map.put(variableName,evaluation);
					}else{
						System.out.println(engine.eval(expression));
					}

					//System.out.println(evaluation);
					
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//map.put(element.attr("name"),element.attr("value"));
			}
		}
	}

}
