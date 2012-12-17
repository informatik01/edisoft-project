/* Function that toggles visibility of a specified element */
function toggle_visibility(id) {
	var element = document.getElementById(id);

	if (element.style.display == "none") {
		element.style.display = "block";
	} else {
		element.style.display = "none";
	}
}


/* Function that changes location to the specified path */
function goToLocation(path) {
	this.location.href = path;
}