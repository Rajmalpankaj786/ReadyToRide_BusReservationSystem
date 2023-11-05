
//custome alert
function openCustomAlert(message) {
    const customAlert = document.getElementById('customAlert');
    const customAlertMessage = document.getElementById('customAlertMessage');
  
    customAlertMessage.textContent = message;
    customAlert.style.display = 'block';
    setTimeout(()=>{
        closeCustomAlert();  
    },3000)
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

function add_row() {

    let firstname = document.getElementById("firstname").value;
    let lastname = document.getElementById("lastname").value;
    let email = document.getElementById("email").value;
    let mobile = document.getElementById("mobile").value;
    let password = document.getElementById("password").value;
    //let username = document.getElementById("username").value;

    if (firstname == '' || lastname == '' || email == '' || password == ''  || mobile == '') {
        showToast('Please fill all mandatory field !!');
        return false;
    }
    if (!document.getElementById("agree").checked) {
        showToast('Please check Accepting all terms & conditions!!');
        return false;
    }

    let obj = {
        "email": email,
        "password": password,
        "firstName": firstname,
        "lastName": lastname,
        "contact": mobile,
      
    }
    addUser(obj);
}


function addUser(obj) {
    let url = "http://localhost:8080/users";

    fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', // Specify that we're sending JSON data
            },
            body: JSON.stringify(obj), // Convert the data to JSON format
        })
        .then(response => response.json())
        .then(data => {
            if (data.userLoginId == null) {
                //alert("please select valid data : "+data.message)
                 openCustomAlert(data.message);
                console.log(data);
            } else {
                showToast("User SignUp SucessFull!!");
                openCustomAlert("User SignUp SucessFull!!");
                console.log(data);
            }

            //change page location from here after ssucessfull signup
        })
        .catch(error => {
            // console.error('Error posting data:', error.);
        });
}