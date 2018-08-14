package com.htms;

import java.util.HashMap;

public class TagCounter<K,V> extends HashMap<K,V>{
	
	
	protected V defaultValue;
	public TagCounter(V defaultValue){
		this.defaultValue = defaultValue;
	}
	
	@Override
	public V get(Object k){
		return containsKey(k) ? super.get(k) : defaultValue;
	}

}
