package com.zzx.po;

import java.sql.*;
import java.util.*;

public class Record {

	private Integer number;
	private Integer id;
	private String state;
	private String type;
	private String goodsName;
	private String username;


	public Integer getNumber(){
		return number;
	}
	public Integer getId(){
		return id;
	}
	public String getState(){
		return state;
	}
	public String getType(){
		return type;
	}
	public String getGoodsName(){
		return goodsName;
	}
	public String getUsername(){
		return username;
	}
	public void setNumber(Integer number){
		this.number=number;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public void setState(String state){
		this.state=state;
	}
	public void setType(String type){
		this.type=type;
	}
	public void setGoodsName(String goodsName){
		this.goodsName=goodsName;
	}
	public void setUsername(String username){
		this.username=username;
	}
}
