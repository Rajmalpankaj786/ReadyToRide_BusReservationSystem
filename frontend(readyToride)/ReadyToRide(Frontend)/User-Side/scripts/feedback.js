let userName = JSON.parse(localStorage.getItem("username")) || "";
if(userName==""){
    openCustomAlert("Please Login First");
}else{
    let user=document.getElementById("feedusername");
    user.innerText=userName;
}

function addFeedback() {
    let uuid = JSON.parse(localStorage.getItem("uuid")) || "";
    if (uuid == "") {
        openCustomAlert("Please Login First");
    } else {
        let url = `http://localhost:8080/feedback/add?key=${uuid}`;
        let driver=document.getElementById("driver").value;
        let service=document.getElementById("service").value;
        let overall=document.getElementById("overall").value;
        let comment= document.getElementById("comment").value;
        if (driver>10||driver==""|| service == ''||service>10 ||overall==''||overall>10) {
            showToast('Please Give field value in range specific!!');
            return false;
        }
        let obj = {
            "driverRating":driver,
            "serviceRating": service,
            "overallRating": overall,
            "comments":comment
        }

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', // Specify that we're sending JSON data
            },
            body: JSON.stringify(obj), // Convert the data to JSON format
        })
        .then(response => response)
        .then(data => {
            console.log(data);
            if(data.message!=null){
                openCustomAlert("FeedBack Added SucessFully!!");
            }else{
                    openCustomAlert("Please Book Some Ticket First!!");
            }
            })
        .catch(error => {
             console.error('Error posting data:', error);
        });

    }
   
}

//custom alert
function openCustomAlert(message) {
    const customAlert = document.getElementById('customAlert');
    const customAlertMessage = document.getElementById('customAlertMessage');

    customAlertMessage.textContent = message;
    customAlert.style.display = 'block';
    setTimeout(() => {
        closeCustomAlert();
        if(message=="Feedback Added Sucesssfully"||message=="Please Book Some Ticket First!!"){
            window.location.href="../User-Side/index.html";
        }else if(message=="Please Login First"){
            window.location.href="./login.html";
        }
    }, 3000)
}
//close custom alert
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