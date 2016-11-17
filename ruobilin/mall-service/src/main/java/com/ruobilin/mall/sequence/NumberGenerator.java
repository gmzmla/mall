package com.ruobilin.mall.sequence;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.ruobilin.mall.entity.Sequence;
import com.ruobilin.mall.mapper.SequenceMapper;

public class NumberGenerator implements SequenceGenerator {
	@Autowired
	private SequenceMapper sequenceMapper;
	
	private Long start;
	private Long end;

	private String id;
	
	public NumberGenerator(String id) {
		this.id = id;
	}
	
	@Override
	public void initialize() {
		do {
			Sequence s = sequenceMapper.getByName(id);
			if (s == null) {
				throw new IllegalArgumentException("Cann't found sequence " + id);
			}
			start = s.getStart();
			end = s.getEnd();
			s.setStart(end);
			s.setEnd(end + s.getStep());
			s.setModified(new Date());
			s.setOldVersion(s.getVersion());
			s.setVersion(s.getVersion() + 1);
			int rt = sequenceMapper.update(s);
			if (rt > 0)
				return;
		} while (true);
	}

	@Override
	public String generate() {
		synchronized(this) {
			if (start >= end) {
				initialize();
			}
			return String.valueOf(start++);
		}
	}

	@Override
	public void destroy() {
	}

}
