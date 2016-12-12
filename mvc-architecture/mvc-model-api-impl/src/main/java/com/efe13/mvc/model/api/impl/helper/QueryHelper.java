package com.efe13.mvc.model.api.impl.helper;

public class QueryHelper {

	private PaginationAPI paginationAPI;
	private FilterAPI filterAPI;
	
	public PaginationAPI getPaginationAPI() {
		return paginationAPI;
	}
	
	public void setPaginationAPI(PaginationAPI paginationAPI) {
		this.paginationAPI = paginationAPI;
	}
	
	public FilterAPI getFilterAPI() {
		return filterAPI;
	}
	
	public void setFilterAPI(FilterAPI filterAPI) {
		this.filterAPI = filterAPI;
	}
	
}