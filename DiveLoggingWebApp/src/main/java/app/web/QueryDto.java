package app.web;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;


public class QueryDto {

    @NotEmpty
    private String inputString;

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
}

