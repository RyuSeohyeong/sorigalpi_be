package com.spring.sorigalpi.base;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListResponse <T> extends BaseResponse {
	
	List<T> dataList;

}
