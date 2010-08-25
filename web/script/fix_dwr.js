dwr.engine.defaultErrorHandler = function(message, ex) {
	dwr.engine._debug("Error: " + ex.name + ", " + ex.message, true);

	if (message == null || message == "") alert("A server error has occured. More information may be available in the console.");
	// Ignore NS_ERROR_NOT_AVAILABLE if Mozilla is being narky
	else if (message.indexOf("0x80040111") != -1) dwr.engine._debug(message);
	else if (message.indexOf("Failed to read input") != -1) { }
	else alert(message);
}; 