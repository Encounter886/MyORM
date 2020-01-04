package com.zzx.po 

import java.sql.*;
import java.util.*;

public class Goods {

	private Interger number;
	private Interger min;
	private Interger max;
	private Interger price;
	private String context;
	private Interger id;
	private String goodsName;
	private String username;


	public Interger getNumber(){
		return number;
	}
	public Interger getMin(){
		return min;
	}
	public Interger getMax(){
		return max;
	}
	public Interger getPrice(){
		return price;
	}
	public String getContext(){
		return context;
	}
	public Interger getId(){
		return id;
	}
	public String getGoodsName(){
		return goodsName;
	}
	public String getUsername(){
		return username;
	}
	public void setNumber(Interger number){
	}
	public void setMin(Interger min){
	}
	public void setMax(Interger max){
	}
	public void setPrice(Interger price){
	}
	public void setContext(String context){
	}
	public void setId(Interger id){
	}
	public void setGoodsName(String goodsName){
	}
	public void setUsername(String username){
	}
}
