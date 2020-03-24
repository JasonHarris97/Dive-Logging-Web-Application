var currentUploadTab = 0; 

// Get the element with id="defaultOpen" and click on it
if(document.getElementById("defaultOpen") != null){
	document.getElementById("defaultOpen").click();
}

function openUploadTab(evt, tabName) {
  // Declare all variables
  var i, tabcontent, tablinks;

  // Get all elements with class="tabcontent" and hide them
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  // Get all elements with class="tablinks" and remove the class "active"
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }

 switch(tabName) {
    case "BasicInfo":
      currentUploadTab = 0;
      break;
    case "Environment":
      currentUploadTab = 1;
      break;
    case "Details":
      currentUploadTab = 2;
      break;
    case "Observations":
      currentUploadTab = 3;
      break;
    default:
      currentUploadTab = 0;
      break;
    // code block
  }

  // Show the current tab, and add an "active" class to the button that opened the tab
  document.getElementById(tabName).style.display = "block";
  tablinks[currentUploadTab].className += " active";
}

function nextUploadPrev(n) {
  // This function will figure out which tab to display
  var x = document.getElementsByClassName("tabcontent");
  // Hide the current tab:
  x[currentUploadTab].style.display = "none";
  // Increase or decrease the current tab by 1:
  currentUploadTab = currentUploadTab + n;
  // if you have reached the end of the form...
  if (currentUploadTab >= x.length) {
    currentUploadTab = currentUploadTab - 1;
  }
  // Otherwise, display the correct tab:
  // This function will display the specified tab of the form...
   switch(currentUploadTab) {
    case 0:
      openUploadTab(new Event("test"), "BasicInfo");
      break;
    case 1:
      openUploadTab(new Event("test"), "Environment")
      break;
    case 2:
      openUploadTab(new Event("test"), "Details")
      break;
    case 3:
      openUploadTab(new Event("test"), "Observations")
      break;
    default:
      openUploadTab(new Event("test"), "BasicInfo")
      break;
    // code block
  }
}











