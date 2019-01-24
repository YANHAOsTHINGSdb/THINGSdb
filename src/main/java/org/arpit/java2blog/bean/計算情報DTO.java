package org.arpit.java2blog.bean;

import java.util.List;

import lombok.Data;

@Data
public class 計算情報DTO {
	String s目標;
	String s操作;
	List<条件DTO> 条件DTOList;
	String s条件関係;
}
