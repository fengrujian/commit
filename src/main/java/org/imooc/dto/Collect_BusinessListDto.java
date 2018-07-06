package org.imooc.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Collect_BusinessListDto {
	
	private boolean hasMore;
	private List<Collect_BusinessDto> data;
	
	public Collect_BusinessListDto() {
	    this.data = new ArrayList<Collect_BusinessDto>();
	}

	public boolean getHasMore() {
		return hasMore;
	}
	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

	public List<Collect_BusinessDto> getData() {
		return data;
	}

	public void setData(List<Collect_BusinessDto> data) {
		this.data = data;
	}
	
}