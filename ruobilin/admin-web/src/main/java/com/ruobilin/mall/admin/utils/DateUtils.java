package com.ruobilin.mall.admin.utils;

import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 功能说明
 * <ul>
 * <li>创建文件描述：日期处理类</li>
 * <li>修改文件描述：</li>
 * </ul>
 * 
 * @author <ul>
 *         <li>创建者：<a href="mailto:43840397@qq.com">蔡越锐 </a></li>
 *         <li>修改者：</li>
 *         </ul>
 * @version <ul>
 *          <li>创建版本：v1.0.0 日期：2011-11-19</li>
 *          <li>修改版本：</li>
 *          </ul>
 */
public class DateUtils
{
	
	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @return 日期字符串
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @param pattern
	 *            字符串格式
	 * @return 日期字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "null";
		}
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return new java.text.SimpleDateFormat(pattern).format(date);
	}
	/**
	 * 获取当前服务器的时间，格式【yyyy-MM-dd HH:mm:ss SSS】
	 * 
	 * @return
	 */
	public static String getNowDateTime()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		String time = format.format(new Date());
		return time;
	}

	/**
	 * 根据格式获取当前日期
	 * 
	 * @param formatType
	 * @return
	 */
	public static String getDateByFormat(String formatType)
	{
		SimpleDateFormat format = new SimpleDateFormat(formatType);
		String time = format.format(new Date());
		return time;
	}

	/**
	 * 获取当前时间戳
	 * 
	 * @return
	 */
	public static long getTimeStamp()
	{
		return System.currentTimeMillis();
	}

	public static String getTimeStampToDate(long timeStamp, String _format)
	{
		Date date = new Timestamp(timeStamp);
		SimpleDateFormat format = new SimpleDateFormat(_format);
		String now = format.format(date);
		return now;
	}

	/**
	 * 时间戳转换成日期
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static String getTimeStampToDate(String timeStamp)
	{
		if (!"".equals(timeStamp) && timeStamp != null && !"0".equals(timeStamp))
		{
			String date = new Timestamp(Long.parseLong(timeStamp)).toString();
			date = date.split("\\x2E")[0];
			return date;
		}
		else
		{
			return "";
		}
	}

	public static void main(String[] args)
	{
		 System.out.println(DateUtils.formatDate("2013-7-3 0:0:0", "yyyy-MM-dd HH:mm:ss", "yyyyMMdd")) ; 
	}

	/**
	 * 时间戳转换成日期
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static String getTimeStampToDate(long timeStamp)
	{
		String date = new Timestamp(timeStamp).toString();
		date = date.split("\\x2E")[0];
		return date;
	}

	/**
	 * 时间戳转换成日期
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static String getTimeStampToDate(Object ts)
	{
		if (ts == null)
			return null;
		return getTimeStampToDate(((Timestamp) ts).getTime());
	}

	/**
	 * 日期（yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss）转换成时间戳
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static long dateToTime(String date)
	{
		if (date == null || StringUtils.isEmpty(date))
			return 0l;
		long l = 0;
		Date d = new Date();
		try
		{
			String _date[] = date.split(" ");
			String temp[] = _date[0].split("/");
			if (temp.length > 1)
			{
				date = new StringBuffer(temp[2]).append("-").append(temp[1]).append("-").append(temp[0]).toString();
			}
			if (date.trim().length() <= 10)
			{
				date = date + " 00:00:00";
			}
			Format format = null;
			try
			{
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			catch (Exception e)
			{
				try
				{
					format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				}
				catch (Exception ex)
				{
					try
					{
						format = new SimpleDateFormat("yyyy-MM-dd HH");
					}
					catch (Exception ex1)
					{
						format = new SimpleDateFormat("yyyy-MM-dd");
					}
				}
			}

			try
			{
				d = (Date) format.parseObject(date);
			}
			catch (Exception ex)
			{

			}
			_date = null;
			temp = null;
			format = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		l = d.getTime();
		d = null;
		return l;
	}

	/**
	 * 生成所有月份（从开始月到结束月）
	 * 
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	public static String[] monthToMonth(String startMonth, String endMonth)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		Date date1 = null; // 开始日期
		Date date2 = null; // 结束日期
		try
		{
			date1 = df.parse(startMonth);
			date2 = df.parse(endMonth);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		// 定义集合存放月份
		List<String> list = new ArrayList<String>();
		list.add(startMonth);
		c1.setTime(date1);
		c2.setTime(date2);
		while (c1.compareTo(c2) < 0)
		{
			c1.add(Calendar.MONTH, 1);// 开始日期加一个月直到等于结束日期为止
			Date ss = c1.getTime();
			String str = df.format(ss);
			list.add(str);
		}
		// 存放入数组
		String[] str = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			str[i] = (String) list.get(i);
		}
		System.out.println(Arrays.toString(str));
		return str;

	}

	/**
	 * 将一种格式时间转换成另一种时间格式的字符串
	 * 
	 * @param dateValue
	 * @param beforeFormat
	 * @param afterFormat
	 * @return
	 */
	public static String formatDate(String dateValue, String beforeFormat, String afterFormat)
	{
		Date date = null;
		SimpleDateFormat beforeFormatDate = new SimpleDateFormat(beforeFormat);
		SimpleDateFormat afterFormatDate = new SimpleDateFormat(afterFormat);
		try
		{
			date = beforeFormatDate.parse(dateValue);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return afterFormatDate.format(date);
	}

	/**
	 * 取得系统时间与参数相差的之前的时间
	 * 
	 * @param date
	 * @param beforeTime
	 * @param i
	 * @return
	 */

	public static String getBeforeDateByYMdHms(Date date, String dateFormat, String beforeTime, int i)
	{
		Calendar cal = null;
		SimpleDateFormat format = null;
		if(dateFormat==null ||dateFormat.equals(""))
		{
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		else
		{
			format = new SimpleDateFormat(dateFormat);
		}
		 

		if (date != null)
		{
			format.format(date);
			cal = format.getCalendar();
		}
		else
		{
			cal = Calendar.getInstance();
		}

		cal = format.getCalendar();

		if ("YEAR".equals(beforeTime))
		{
			cal.add(Calendar.YEAR, i);
		}
		if ("MONTH".equals(beforeTime))
		{
			cal.add(Calendar.MONTH, i);
		}
		if ("DATE".equals(beforeTime))
		{
			cal.add(Calendar.DATE, i);
		}
		if ("HOUR".equals(beforeTime))
		{
			cal.add(Calendar.HOUR, i);
		}
		if ("MINUTE".equals(beforeTime))
		{
			cal.add(Calendar.MINUTE, i);
		}

		return format.format(cal.getTime());
	}

	/**
	 * 将字符串转成日期类型
	 * 
	 * @param dateString
	 * @param strFormat
	 * @return
	 */
	public static Date getDateByString(String dateString, String strFormat)
	{
		Date date = null;
		SimpleDateFormat formatDate = new SimpleDateFormat(strFormat);
		try
		{
			date = formatDate.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 根据时间格式取得当前时间的字符串
	 * 
	 * @param strFormat
	 * @return
	 */
	public static String getTodayByFormat(Date date, String strFormat)
	{
		if (strFormat == null || strFormat.equals(""))
		{
			strFormat = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		String strDate = format.format(date);

		return strDate;

	}
}
