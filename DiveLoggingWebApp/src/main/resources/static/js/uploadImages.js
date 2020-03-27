'use strict';

// ==============================================================================================================
// BINDINGS
// ==============================================================================================================

var multipleUploadForm = document.querySelector('#multipleUploadForm');
var multipleFileUploadInput = document.querySelector('#multipleFileUploadInput');
var multipleFileUploadInputDive = document.querySelector('#multipleFileUploadInputDive');
var multipleFileUploadError = document.querySelector('#multipleFileUploadError');
var multipleFileUploadSuccess = document.querySelector('#multipleFileUploadSuccess');

var profilePicUploadForm = document.querySelector('#profilePicUploadForm');
var profilePicUploadInput = document.querySelector('#profilePicUploadInput');
var profilePicUploadInputUse = document.querySelector('#profilePicUploadInputUse');

var singleFileUploadError = document.querySelector('#singleFileUploadError');
var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');


// ==============================================================================================================
// FUNCTIONS
// ==============================================================================================================
function uploadMultipleFiles(files, diveId) {
    var formData = new FormData();

    for(var index = 0; index < files.length; index++) {
        formData.append("files", files[index]);
    }

    formData.set("diveId", diveId);
    formData.set("fileUse", "diveImage");
    console.log(String(diveId));
    console.log(String(formData.get("diveId")));
    console.log(String(formData.get("fileUse")));

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/uploadMultipleFiles");

    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            multipleFileUploadError.style.display = "none";
            var content = "<p>All Files Uploaded Successfully</p>";
	    for(var i = 0; i < response.length; i++) {
                content += "<p>Image: " + response[i].fileName + "</p>";
            }
            multipleFileUploadSuccess.innerHTML = content;
            multipleFileUploadSuccess.style.display = "block";
        } else {
            multipleFileUploadSuccess.style.display = "none";
            multipleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }

    xhr.send(formData);
}

function uploadSingleFile(file, fileUse) {
    var formData = new FormData();

    formData.append("file", file);
    formData.set("fileUse", fileUse);
    console.log(String(formData.get("fileUse")));

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/uploadSingleFile");

    xhr.onload = function() {
	console.log("HELLO FROM UPLOAD SINGLE FILE");
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            singleFileUploadError.style.display = "none";
            singleFileUploadSuccess.innerHTML = "<p>File Uploaded Successfully.</p><p>Image: " + response.fileName + "</p>";
            singleFileUploadSuccess.style.display = "block";
        } else {
            singleFileUploadSuccess.style.display = "none";
            singleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }

    xhr.send(formData);
}

// ==============================================================================================================
// EVENT LISTENERS
// ==============================================================================================================

if (multipleUploadForm != null){
	// MULTIPLE FILES
	multipleUploadForm.addEventListener('submit', function(event){
	    var files = multipleFileUploadInput.files;
	    var diveId = multipleFileUploadInputDive.value;
	    if(files.length === 0) {
		multipleFileUploadError.innerHTML = "Please select at least one file";
		multipleFileUploadError.style.display = "block";
	    }
	    uploadMultipleFiles(files, diveId);
	    event.preventDefault();
	}, true);
}

if(profilePicUploadForm != null) {
	// PROFILE PICTURE
	profilePicUploadForm.addEventListener('submit', function(event){
	    var files = profilePicUploadInput.files;
	    var fileUse = profilePicUploadInputUse.value;
	    if(files.length === 0) {
		singleFileUploadError.innerHTML = "Please select a file";
		singleFileUploadError.style.display = "block";
	    }
	    console.log(fileUse);
	    console.log("Running uploadSingleFiles");
	    uploadSingleFile(files[0], fileUse);
	    event.preventDefault();
	}, true);
}

