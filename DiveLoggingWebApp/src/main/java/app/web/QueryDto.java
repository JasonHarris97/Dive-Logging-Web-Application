package app.web;

import java.time.LocalDate;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;


public class QueryDto {

    private String inputString;
    
    @NotEmpty
    private String searchOption;
    
    @NotEmpty
    private String orderBy;
    
    @NotEmpty
    private String sortBy;
    
    @NotEmpty
    private String source;
    
    private String pageNo;
    
    private String pageSize;
    
    private String country;
    
    private String padiLevel;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @AssertTrue
    private Boolean terms;
    
    public QueryDto() { 	
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }
    
    public String getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(String searchOption) {
        this.searchOption = searchOption;
    }
    
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPadiLevel() {
		return padiLevel;
	}

	public void setPadiLevel(String padiLevel) {
		this.padiLevel = padiLevel;
	}
}

