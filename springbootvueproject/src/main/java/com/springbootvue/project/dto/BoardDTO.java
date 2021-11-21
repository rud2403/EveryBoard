package com.springbootvue.project.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BoardDTO {

	private Integer num;
	private String title;
	private String content;
	private String author;
	private int readcount;
	private int likecount;
	private String filepath;
	private String regdate;
	private Integer id;
}
