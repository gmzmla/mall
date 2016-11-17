package com.ruobilin.mall.sequence;

public interface SequenceGenerator {
	void initialize();
	
	String generate();
	
	void destroy();
}
