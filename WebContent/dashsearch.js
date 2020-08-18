/**
 * Handle the data returned by DashDashSearchServlet
 * @param resultDataString jsonObject
 */
function handleDashSearchResult(resultDataString) {
    resultDataJson = JSON.parse(resultDataString);

    console.log("handle dashsearch response");
    console.log(resultDataJson);
    console.log(resultDataJson["status"]);

    // If dashsearch success, redirect to index.html page
    if (resultDataJson["status"] === "successg") {
        window.location.replace("AddedSuccg.html");
    }else if(resultDataJson["status"] === "successc"){
    	window.location.replace("AddedSuccC.html");
    }
    // If dashsearch fail, display error message on <div> with id "dashdashsearch_error_message"
    else {
        console.log("show error message");
        console.log(resultDataJson["message"]);
        jQuery("#dashsearch_error_message").text(resultDataJson["message"]);
    }
}

/**
 * Submit the form content with GET method
 * @param formSubmitEvent
 */
function submitDashSearchForm(formSubmitEvent) {
    console.log("submit dashsearch form");

    // Important: disable the default action of submitting the form
    //   which will cause the page to refresh
    //   see jQuery reference for details: https://api.jquery.com/submit/
    formSubmitEvent.preventDefault();

    jQuery.post(
        "api/dashsearch",
        // Serialize the dashsearch form to the data sent by POST request
        jQuery("#dashsearch_form").serialize(),
        (resultDataString) => handleDashSearchResult(resultDataString));

}

// Bind the submit action of the form to a handler function
jQuery("#dashsearch_form").submit((event) => submitDashSearchForm(event));

