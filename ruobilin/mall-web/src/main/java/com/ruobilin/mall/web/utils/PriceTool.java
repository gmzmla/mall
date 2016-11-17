package com.ruobilin.mall.web.utils;

import java.text.DecimalFormat;

public class PriceTool {
	public String format(Integer price) {
		DecimalFormat fnum = new DecimalFormat("##0.00");
		return fnum.format(((float)price) / 100.0);
	}
}
