package com.halit.veterinaryapphalit.Models;

import java.io.Serializable;

public class DeleteAnswerModel implements Serializable {
	private String text;
	private boolean tf;

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	@Override
	public String toString() {
		return "DeleteAnswerModel{" +
				"text='" + text + '\'' +
				", tf=" + tf +
				'}';
	}
}