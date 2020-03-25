var currentRegTab = 0; // Current tab is set to be the first tab (0)

if(document.getElementsByClassName("multiStepTab").length >= 1){
  showRegTab(currentRegTab);
}

function showRegTab(n) {
  // This function will display the specified tab of the form...
  var x = document.getElementsByClassName("multiStepTab");
  x[n].style.display = "block";
  //... and fix the Previous/Next buttons:
  if(document.getElementById("regForm") != null){
	  if (n == 0) {
	    document.getElementById("prevBtn").style.display = "none";
	  } else {
	    document.getElementById("prevBtn").style.display = "inline";
	  }
	  if (n == (x.length - 1)) {
	    document.getElementById("nextBtn").innerHTML = "Submit";
	  } else {
	    document.getElementById("nextBtn").innerHTML = "Next";
	  }
	  //... and run a function that will display the correct step indicator:
	  fixStepIndicator(n)
  }
}

function nextRegPrev(n) {
  // This function will figure out which tab to display
  var x = document.getElementsByClassName("multiStepTab");
  // Exit the function if any field in the current tab is invalid:
  if (n == 1 && !validateForm()) return false;
  // Hide the current tab:
  x[currentRegTab].style.display = "none";
  // Increase or decrease the current tab by 1:
  currentRegTab = currentRegTab + n;
  // if you have reached the end of the form...
  if (currentRegTab >= x.length) {
    // ... the form gets submitted:
    document.getElementById("regForm").submit();
    return false;
  }
  // Otherwise, display the correct tab:
  showRegTab(currentRegTab);
}

function validateForm() {
  // This function deals with validation of the form fields
  var x, y, i, valid = true;
  x = document.getElementsByClassName("multiStepTab");
  y = x[currentRegTab].getElementsByTagName("input");
  // A loop that checks every input field in the current tab:
  for (i = 0; i < y.length; i++) {
    // If a field is empty...
    if (y[i].value == "") {
      // add an "invalid" class to the field:
      y[i].className += " invalid";
      // and set the current valid status to false
      valid = false;
    }
  }
  // If the valid status is true, mark the step as finished and valid:
  if (valid) {
    document.getElementsByClassName("step")[currentRegTab].className += " finish";
  }
  return valid; // return the valid status
}

function fixStepIndicator(n) {
  // This function removes the "active" class of all steps...
  var i, x = document.getElementsByClassName("step");
  for (i = 0; i < x.length; i++) {
    x[i].className = x[i].className.replace(" active", "");
  }
  //... and adds the "active" class on the current step:
  x[n].className += " active";
}
