// Function to show confirmation dialogue before deleting user account
function confirmDelete(event) {
    event.preventDefault(); // Prevent default action (form submission) from occurring

    // Display confirmation dialogue using SweetAlert
    swal({
        title: "Are you sure?",
        text: "Once deleted, you will not be able to recover your account!",
        icon: "warning",
        buttons: true, // Show 'OK' and 'Cancel' buttons
        dangerMode: true, // Highlight 'OK' button to indicate action is 'dangerous'
    })
    .then((willDelete) => { // Handle the user's response
        if (willDelete) {
            // If user confirms deletion, submit delete form
            document.getElementById("delete-form").submit();
        } else {
            // If user cancels, do nothing
        }
    });
}

// Event listener that runs when DOM fully loaded
document.addEventListener("DOMContentLoaded", function () {
    // Get status element by ID
    let statusElement = document.getElementById("status");
    // Retrieve value of status element, if it exists
    let status = statusElement ? statusElement.value : '';

    // Display different SweetAlert error messages based on status value
    
    if (status === "failed") {
        swal("Login Failed", "Incorrect E-mail or Password", "error");
    }

    if (status === "invalidEmail") {
        swal("Error", "Please enter an E-mail", "error");
    }
	
	if(status == "invalidPassword"){
		swal("Error","Please enter a Password", "error");
	}
	
	if(status == "success"){
		swal("Success!","Account Created Succesfully","success");
	}
	
	if(status == "invalidName"){
		swal("Error","Please enter a Name","error");
	}
	
	if(status == "invalidRePassword"){
		swal("Error","Please enter a matching password","error");
	}
	
	if(status == "invalidPhone"){
		swal("Error","Please enter a Phone Number","error");
	}
	
	if(status == "invalidPhoneLength"){
		swal("Error","Please enter a number shorter than 10 characters","error");
	}
});