package com.ruobilin.mall.mapper;

import com.ruobilin.mall.entity.Sequence;

public interface SequenceMapper {

	Sequence getByName(String name);

	int update(Sequence s);
	
}
