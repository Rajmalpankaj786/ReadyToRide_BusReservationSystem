const search_ticket_button = document.getElementById('search-ticket-button');

const currUserId = JSON.parse(localStorage.getItem('uuid'));
if(currUserId == null){
    alert("Please Login to Cancel Ticket...!");
    window.location.href="./login.html";
}

function cancelTicket(ticketId){
    const userConfirmed = confirm("Are You Sure You Want To Cancel ?");
    if(userConfirmed){
        const cancelTicketApi = `http://localhost:8080/reservation/delete/${ticketId}?key=${currUserId}`;
        fetch(cancelTicketApi, {
            method: 'delete'
        })
        .then(response => {
            if(response.ok){
                response.json().then(data => {
                    alert("Reservation Cancelled Successfully...!");
                    window.location.href = "http://127.0.0.1:5500/index.html";
                })
            }else{
                alert("Dear Customer Please Enter Correct Ticket Id");
            }
        })
    }
}

search_ticket_button.addEventListener('click', () => {
    var searchIdText = document.getElementById('search-ticket-id');
    var searchId = searchIdText.value;
    if(searchId == ""){
        alert("Please Enter Id To Check Status");
        return;
    }
    cancelTicket(searchId);
    searchIdText.value = "";

})