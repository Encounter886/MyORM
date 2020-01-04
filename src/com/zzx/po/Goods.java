package com.zzx.po;

import java.sql.*;
import java.util.*;

public class Goods {

	private Integer number;
	private Integer min;
	private Integer max;
	private Integer price;
	private String context;
	private Integer id;
	private String goodsName;
	private String username;


	public Integer getNumber(){
		return number;
	}
	public Integer getMin(){
		return min;
	}
	public Integer getMax(){
		return max;
	}
	public Integer getPrice(){
		return price;
	}
	public String getContext(){
		return context;
	}
	public Integer getId(){
		return id;
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
	public void setMin(Integer min){
		this.min=min;
	}
	public void setMax(Integer max){
		this.max=max;
	}
	public void setPrice(Integer price){
		this.price=price;
	}
	public void setContext(String context){
		this.context=context;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public void setGoodsName(String goodsName){
		this.goodsName=goodsName;
	}
	public void setUsername(String username){
		this.username=username;
	}
}
