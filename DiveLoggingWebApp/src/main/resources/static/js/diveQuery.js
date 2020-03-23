
/*<![CDATA[*/
function loadQuery(){
	var source = /*[[${query.source}]]*/;
	if(source.equals("query")){
		document.getElementById("inputString").text = /*[[${query.getInputString}]]*/ null;
		var searchOptionToCheck = /*[[${query.searchOption}]]*/ null;
		var orderByToCheck = /*[[${query.orderBy}]]*/ null;

		var searchOption = document.getElementsByName("searchOption");
		var orderBy = document.getElementsByName("orderBy");
		searchOption.querySelector('input[value=' + searchOptionToCheck + ']').checked = true;
		orderBy.querySelector('input[value=' + orderByToCheck + ']').checked = true;
	}
}

function submitPage(pageNo) {
	document.getElementById("pageNo").value = pageNo;
	document.getElementById("queryForm").submit();
}
/*]]>*/

