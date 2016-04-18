package com.example.redboy.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

// javabean  --       java 特殊的java类-----    x    getX  setX   ---  内省操作
// 对应的表名字
@Table(name="help1")
public class Help {
//	{"id":1,"title":"如何派送"}
	// 不允许自增长
	@NoAutoIncrement
	@Column(column="_id")
	public long id;
	public String title;
	public Help(long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	public Help() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
