let currUserID = JSON.parse(localStorage.getItem('uuid'));

if(currUserID == undefined){
    currUserID = null;
}

localStorage.setItem('uuid', JSON.stringify(currUserID));

//custome alert


function openCustomAlert(message) {
    const customAlert = document.getElementById('customAlert');
    const customAlertMessage = document.getElementById('customAlertMessage');

    customAlertMessage.textContent = message;
    customAlert.style.display = 'block';
    setTimeout(() => {
        closeCustomAlert();
    }, 5000)
}

function closeCustomAlert() {
    const customAlert = document.getElementById('customAlert');
    customAlert.style.display = 'none';
}



// Function to show the custom toast notification
function showToast(message) {
    const toastContainer = document.getElementById("customToastContainer");
    const toast = document.createElement("div");
    toast.className = "custom-toast";
    toast.textContent = message;

    toastContainer.appendChild(toast);

    // Auto-hide the toast after 3 seconds (adjust as needed)
    setTimeout(function () {
        toastContainer.removeChild(toast);
    }, 3000);
}

function sign_in() {
    let password = document.getElementById("password").value;
    let email = document.getElementById("email").value;

    if (password == '' || email == '') {
        showToast('Please fill all mandatory field !!');
        return false;
    }
    if (!document.getElementById("agree").checked) {
        showToast('Please check Accepting all terms & conditions!!');
        return false;
    }
    let signInObj = {
        "email": email,
        "password": password
    }
    
    if (signInObj.email === "a@gmail.com" && signInObj.password === "123456") loginAdmin(signInObj);
    else loginUser(signInObj);
}


function loginUser(obj) {
    let url = "http://localhost:8080/user/login";

    fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', // Specify that we're sending JSON data
            },
            body: JSON.stringify(obj), // Convert the data to JSON format
        })
        .then(response => response.json())
        .then(data => {
            // console.log(data);
            if (data.userId== null) {
                openCustomAlert(data.message);
            } else {
                showToast("User Login SucessFull!!");
                
                openCustomAlert("WelCome " + data.type);
                
                localStorage.setItem("uuid", JSON.stringify(data.uuid));
                localStorage.setItem("username", JSON.stringify(data.type));
                
               window.location.href="http://127.0.0.1:5500/index.html";
                //change page location from here after ssucessfull signup
            }


        })
        .catch(error => {
            // console.error('Error posting data:', error);
        });
}

function loginAdmin(obj) {
    console.log("Inside admin");
    let url = "http://localhost:8080/admin/login";

    fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', // Specify that we're sending JSON data
            },
            body: JSON.stringify(obj) // Convert the data to JSON format
        })
        .then(response => response.json())
        .then(data => {
            console.log(data)
            if (data.userId == null) {
                openCustomAlert(data.message);
            } else {
                
                showToast("Admin Login SucessFull!!");
                openCustomAlert("WelCome Admin!!");

                localStorage.setItem("uuid", JSON.stringify(data.uuid));
                
                window.location.href = "../Admin_section/Admin_Home.html";
            }
        })
        .catch(error => {
            console.error('Error posting data:', error);
        });
}