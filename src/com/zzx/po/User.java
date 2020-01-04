package com.zzx.po;

import java.sql.*;
import java.util.*;

public class User {

	private String password;
	private String address;
	private String phone;
	private String sex;
	private String nickname;
	private Integer id;
	private Integer type;
	private String email;
	private String username;


	public String getPassword(){
		return password;
	}
	public String getAddress(){
		return address;
	}
	public String getPhone(){
		return phone;
	}
	public String getSex(){
		return sex;
	}
	public String getNickname(){
		return nickname;
	}
	public Integer getId(){
		return id;
	}
	public Integer getType(){
		return type;
	}
	public String getEmail(){
		return email;
	}
	public String getUsername(){
		return username;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public void setAddress(String address){
		this.address=address;
	}
	public void setPhone(String phone){
		this.phone=phone;
	}
	public void setSex(String sex){
		this.sex=sex;
	}
	public void setNickname(String nickname){
		this.nickname=nickname;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public void setType(Integer type){
		this.type=type;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public void setUsername(String username){
		this.username=username;
	}
}
