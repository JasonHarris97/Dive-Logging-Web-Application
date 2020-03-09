package app.web;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;


public class QueryDto {

    @NotEmpty
    private String inputString;
    
    @NotEmpty
    private String searchOption;
    
    @NotEmpty
    private String orderBy;
    
    @NotEmpty
    private String source;

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
}

