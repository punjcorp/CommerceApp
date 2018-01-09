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

	private Integer resultSize;
	private Integer pageSize;
	private Integer currentPageNo;
	private Integer maxDisplayPage;

	private Integer startCount;
	private List<Integer> displayPages;

	private Integer noOfPages;
	private Boolean firstPage = false;
	private Boolean lastPage = false;

	public Pager() {

	}

	public Pager(Integer resultSize, Integer pageSize, Integer currentPageNo, Integer maxDisplayPage) {
		this.resultSize = resultSize;
		this.pageSize = pageSize;
		this.currentPageNo = currentPageNo;
		this.maxDisplayPage = maxDisplayPage;

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
		List<Integer> pageList = new ArrayList<Integer>();

		int halfSize = this.noOfPages / 2;
		int start, size;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentPageNo == null) ? 0 : currentPageNo.hashCode());
		result = prime * result + ((displayPages == null) ? 0 : displayPages.hashCode());
		result = prime * result + ((firstPage == null) ? 0 : firstPage.hashCode());
		result = prime * result + ((lastPage == null) ? 0 : lastPage.hashCode());
		result = prime * result + ((maxDisplayPage == null) ? 0 : maxDisplayPage.hashCode());
		result = prime * result + ((noOfPages == null) ? 0 : noOfPages.hashCode());
		result = prime * result + ((pageSize == null) ? 0 : pageSize.hashCode());
		result = prime * result + ((resultSize == null) ? 0 : resultSize.hashCode());
		result = prime * result + ((startCount == null) ? 0 : startCount.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Pager other = (Pager) obj;
		if (currentPageNo == null) {
			if (other.currentPageNo != null) {
				return false;
			}
		} else if (!currentPageNo.equals(other.currentPageNo)) {
			return false;
		}
		if (displayPages == null) {
			if (other.displayPages != null) {
				return false;
			}
		} else if (!displayPages.equals(other.displayPages)) {
			return false;
		}
		if (firstPage == null) {
			if (other.firstPage != null) {
				return false;
			}
		} else if (!firstPage.equals(other.firstPage)) {
			return false;
		}
		if (lastPage == null) {
			if (other.lastPage != null) {
				return false;
			}
		} else if (!lastPage.equals(other.lastPage)) {
			return false;
		}
		if (maxDisplayPage == null) {
			if (other.maxDisplayPage != null) {
				return false;
			}
		} else if (!maxDisplayPage.equals(other.maxDisplayPage)) {
			return false;
		}
		if (noOfPages == null) {
			if (other.noOfPages != null) {
				return false;
			}
		} else if (!noOfPages.equals(other.noOfPages)) {
			return false;
		}
		if (pageSize == null) {
			if (other.pageSize != null) {
				return false;
			}
		} else if (!pageSize.equals(other.pageSize)) {
			return false;
		}
		if (resultSize == null) {
			if (other.resultSize != null) {
				return false;
			}
		} else if (!resultSize.equals(other.resultSize)) {
			return false;
		}
		if (startCount == null) {
			if (other.startCount != null) {
				return false;
			}
		} else if (!startCount.equals(other.startCount)) {
			return false;
		}
		return true;
	}

}
