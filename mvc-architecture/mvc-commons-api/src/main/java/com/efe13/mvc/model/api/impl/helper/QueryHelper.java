package com.efe13.mvc.model.api.impl.helper;

public class QueryHelper {

	private PaginationAPI paginationAPI;
	private FilterAPI filterAPI;
	private OrderAPI orderAPI;
	
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

	public OrderAPI getOrderAPI() {
		return orderAPI;
	}

	public void setOrderAPI(OrderAPI orderAPI) {
		this.orderAPI = orderAPI;
	}
}