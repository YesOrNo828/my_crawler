/**
 * 
 */
package com.my.common.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

/**
 * 数字可比较的MDC过滤器实现
 *
 */
public class NumCompareMDCFilter extends Filter<ILoggingEvent> {
	private String MDCKey;
	private double value;
	private String op;
	@Override
	public FilterReply decide(ILoggingEvent event) {
		if(StringUtils.isBlank(MDCKey) || StringUtils.isBlank(op)) {
			return FilterReply.NEUTRAL;
		}
		
		boolean accept = false;
		double val = 0;
		try {
			String s = MDC.get(MDCKey);
			if(StringUtils.isBlank(s)) {
				return FilterReply.DENY;
			}
			val = Double.parseDouble(s);
		} catch(NumberFormatException e) {
			return FilterReply.NEUTRAL;
		}
		
		if(op.equals("=") || op.equals("==")) {
			accept = val == value;
		} else if(op.equals("!=")) {
			accept = val != value;
		} else if(op.equals(">=")) {
			accept = val >= value;
		} else if(op.equals(">")) {
			accept = val > value;
		} else if(op.equals("<=")) {
			accept = val <= value;
		} else if(op.equals("<")) {
			accept = val < value;
		}
		if(accept) {
			return FilterReply.ACCEPT;
		} else {
			return FilterReply.DENY;
		}
	}
	/**
	 * @return 获取 mDCKey
	 */
	public String getMDCKey() {
		return MDCKey;
	}
	/**
	 * @param 设置 mDCKey
	 */
	public void setMDCKey(String mDCKey) {
		this.MDCKey = mDCKey;
	}
	/**
	 * @return 获取 value
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @param 设置 value
	 */
	public void setValue(double value) {
		this.value = value;
	}
	/**
	 * @return 获取 op
	 */
	public String getOp() {
		return op;
	}
	/**
	 * @param 设置 op
	 */
	public void setOp(String op) {
		this.op = op;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NumCompareMDCFilter [" + MDCKey + " " + op + " " + value + "]";
	}

	
}
