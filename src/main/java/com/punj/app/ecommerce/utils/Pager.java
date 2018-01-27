/**
 * 
 */
package com.punj.app.ecommerce.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 *
 */
public class Pager implements Serializable {

	private static final long serialVersionUID = -6504656288105354223L;

	private Integer resultSize;
	private Integer pageSize;
	private Integer currentPageNo;
	private Integer maxDisplayPage;

	private Integer startCount;
	private List<Integer> displayPages;

	private Integer lastPageNo;
	private String viewBasePath;

	private Integer noOfPages;
	private Boolean firstPage = false;
	private Boolean lastPage = false;

	public Pager() {

	}

	public Pager(Integer resultSize, Integer pageSize, Integer currentPageNo, Integer maxDisplayPage,
			String viewBasePath) {
		this.resultSize = resultSize;
		this.pageSize = pageSize;
		this.currentPageNo = currentPageNo;
		this.maxDisplayPage = maxDisplayPage;
		this.viewBasePath = viewBasePath;

		this.setNoOfPages();
		this.setFirstPage();
		this.setLastPage();

		this.setDisplayPages();
	}

	/**
	 * @return the resultSize
	 */
	public Integer getResultSize() {
		return resultSize;
	}

	/**
	 * @param resultSize
	 *            the resultSize to set
	 */
	public void setResultSize(Integer resultSize) {
		this.resultSize = resultSize;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the currentPageNo
	 */
	public Integer getCurrentPageNo() {
		return currentPageNo;
	}

	/**
	 * @param currentPageNo
	 *            the currentPageNo to set
	 */
	public void setCurrentPageNo(Integer currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	/**
	 * @return the displayPages
	 */
	public List<Integer> getDisplayPages() {
		return displayPages;
	}

	/**
	 * @param displayPages
	 *            the displayPages to set
	 */
	public void setDisplayPages() {
		List<Integer> pageList = new ArrayList<>();

		int halfSize = this.noOfPages / 2;
		int start;
		int size;

		if (this.noOfPages <= this.maxDisplayPage) {
			start = 1;
			size = this.noOfPages;
		} else {
			if (this.currentPageNo <= this.maxDisplayPage - halfSize) {
				start = 1;
				size = this.maxDisplayPage;
			} else if (this.currentPageNo >= this.noOfPages - halfSize) {
				start = this.noOfPages - this.maxDisplayPage + 1;
				size = this.maxDisplayPage;
			} else {
				start = this.currentPageNo - halfSize;
				size = this.maxDisplayPage;
			}
		}

		for (int i = 0; i < size; i++) {
			pageList.add(start + i);
			this.lastPageNo = start + i;
		}

		this.displayPages = pageList;

	}

	/**
	 * @return the noOfPages
	 */
	public Integer getNoOfPages() {
		return noOfPages;
	}

	/**
	 */
	public void setNoOfPages() {

		this.noOfPages = this.resultSize / this.pageSize;
		if (this.resultSize % this.pageSize > 0)
			this.noOfPages++;
	}

	/**
	 * @return the firstPage
	 */
	public Boolean isFirstPage() {
		return firstPage;
	}

	/**
	 */
	public void setFirstPage() {
		if (this.currentPageNo == 1)
			this.firstPage = Boolean.TRUE;
	}

	/**
	 * @return the lastPage
	 */
	public Boolean isLastPage() {
		return lastPage;
	}

	/**
	 */
	public void setLastPage() {
		if (this.currentPageNo == this.noOfPages)
			this.lastPage = Boolean.TRUE;
	}

	/**
	 * @return the maxDisplayPage
	 */
	public Integer getMaxDisplayPage() {
		return maxDisplayPage;
	}

	/**
	 * @param maxDisplayPage
	 *            the maxDisplayPage to set
	 */
	public void setMaxDisplayPage(Integer maxDisplayPage) {
		this.maxDisplayPage = maxDisplayPage;
	}

	/**
	 * @return the startCount
	 */
	public Integer getStartCount() {
		return startCount;
	}

	/**
	 * @param startCount
	 *            the startCount to set
	 */
	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}

	/**
	 * @return the lastPageNo
	 */
	public Integer getLastPageNo() {
		return lastPageNo;
	}

	/**
	 * @param lastPageNo
	 *            the lastPageNo to set
	 */
	public void setLastPageNo(Integer lastPageNo) {
		this.lastPageNo = lastPageNo;
	}

	/**
	 * @param firstPage
	 *            the firstPage to set
	 */
	public void setFirstPage(Boolean firstPage) {
		this.firstPage = firstPage;
	}

	/**
	 * @param lastPage
	 *            the lastPage to set
	 */
	public void setLastPage(Boolean lastPage) {
		this.lastPage = lastPage;
	}

	/**
	 * @param displayPages
	 *            the displayPages to set
	 */
	public void setDisplayPages(List<Integer> displayPages) {
		this.displayPages = displayPages;
	}

	/**
	 * @param noOfPages
	 *            the noOfPages to set
	 */
	public void setNoOfPages(Integer noOfPages) {
		this.noOfPages = noOfPages;
	}

	/**
	 * @return the viewBasePath
	 */
	public String getViewBasePath() {
		return viewBasePath;
	}

	/**
	 * @param viewBasePath
	 *            the viewBasePath to set
	 */
	public void setViewBasePath(String viewBasePath) {
		this.viewBasePath = viewBasePath;
	}

}
