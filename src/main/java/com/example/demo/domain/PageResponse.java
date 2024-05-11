package com.example.demo.domain;



import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;



public class PageResponse<T> {
	
	int pageNum; // an int variable representing the current page number.
	int pageSize; // an int variable representing the number of items per page.
	int totalPages; // an int variable representing the total number of pages.
	List<T> content; // List of generic type T representing the list of content items for the current page.

	
	public PageResponse(Page<T> paged) {

		if (Optional.ofNullable(paged).isPresent()) {
			this.pageNum = paged.getNumber();
			this.pageSize = paged.getSize();
			this.totalPages = paged.getTotalPages();
			this.content = paged.getContent();
		}
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getTotalPages() {
		return totalPages;
	}


	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}


	public List<T> getContent() {
		return content;
	}


	public void setContent(List<T> content) {
		this.content = content;
	}
	
	
}
