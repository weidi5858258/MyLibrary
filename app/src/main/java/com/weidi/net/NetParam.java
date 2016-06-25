package com.c1tech.dress.net;

import java.io.Serializable;

public class NetParam implements Serializable, Comparable {

	private static final long serialVersionUID = 2247322475L;
	String mName;
	String mValue;

	
	public NetParam(String name, String value) {
		this.mName = name;
		this.mValue = value;
	}

	@Override
	public boolean equals(Object arg0) {
		if (null == arg0) {
			return false;
		}
		if (this == arg0) {
			return true;
		}
		if (arg0 instanceof NetParam) {
			NetParam param = (NetParam) arg0;
			return this.mName.equals(param.mName) && this.mValue.equals(param.mValue);
		}
		return false;
	}

	public int compareTo(Object o) {
		int compared;
		NetParam param = (NetParam) o;
		compared = mName.compareTo(param.mName);
		if (0 == compared) {
			compared = mValue.compareTo(param.mValue);
		}
		return compared;
	}
	
}
