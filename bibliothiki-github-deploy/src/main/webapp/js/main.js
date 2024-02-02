function confirmDelete(event) {
    event.preventDefault();

    swal({
        title: "Are you sure?",
        text: "Once deleted, you will not be able to recover your account!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
    .then((willDelete) => {
        if (willDelete) {
            document.getElementById("delete-form").submit();
        } else {
        }
    });
}

document.addEventListener("DOMContentLoaded", function () {
    let statusElement = document.getElementById("status");
    let status = statusElement ? statusElement.value : '';

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