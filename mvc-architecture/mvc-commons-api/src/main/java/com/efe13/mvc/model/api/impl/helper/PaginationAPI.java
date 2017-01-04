package com.efe13.mvc.model.api.impl.helper;

public class PaginationAPI {

	private int currentPage;
	private int pageSize;
	private int totalPages;
	private int collectionSize;
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
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
	
	public int getCollectionSize() {
		return collectionSize;
	}
	
	public void setCollectionSize(int collectionSize) {
		this.collectionSize = collectionSize;
	}
}