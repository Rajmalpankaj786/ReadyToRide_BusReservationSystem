const search_ticket_button = document.getElementById("search-ticket-button");
const ticket_tabel = document.getElementById("ticket-table");
const ticket_cancel_buttons = document.getElementById(
    "cancel-or-download-ticket"
);
const download_ticket_button = document.getElementById(
    "download-ticket-button"
);
const cancel_ticket_button = document.getElementById("cancel-ticket-button");
var fetchedDataId = null;

const currUserId = JSON.parse(localStorage.getItem("uuid"));
if (currUserId == null) {
    alert("Please Login to Check Ticket status...!");
    window.location.href = "./login.html";
}

function fetchAndDisplayTicket(searchId) {
    const apiUrl = `http://localhost:8080/reservation/view/${searchId}?key=${currUserId}`;

    // Fetch data from the API and populate the table
    fetch(apiUrl)
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then((data) => {
            console.log(data);
            fetchedDataId = data.reservationId;
            ticket_tabel.style.visibility = "visible";
            ticket_cancel_buttons.style.visibility = "visible";
            document.getElementById("ticketId").textContent =
                data.reservationId;
            document.getElementById("departure-time").textContent =
                data.bus.departureTime + " IST";
            document.getElementById("busName").textContent = data.bus.busName;
            document.getElementById("source").textContent = data.source;
            document.getElementById("destination").textContent =
                data.destination;
            document.getElementById("reservationDate").textContent =
                data.reservationDate;
            document.getElementById("ticketStatus").textContent =
                data.reservationStatus;
        })
        .catch((error) => {
            alert("Dear Customer Please Enter Correct Ticket Id");
        });
}

search_ticket_button.addEventListener("click", () => {
    const searchId = document.getElementById("search-ticket-id").value;
    if (searchId == "") {
        alert("Please Enter Id To Check Status");
        return;
    }
    fetchAndDisplayTicket(searchId);
});

function cancelTicket() {
    const userConfirmed = confirm("Are You Sure You Want To Cancel ?");
    if (userConfirmed) {
        const cancelTicketApi = `http://localhost:8080/reservation/delete/${fetchedDataId}?key=${currUserId}`;
        fetch(cancelTicketApi, {
            method: "delete",
        }).then((response) => {
            if (response.ok) {
                response.json().then((data) => {
                    alert("Reservation Cancelled Successfully...!");
                    window.location.href = "http://127.0.0.1:5500/index.html";
                });
            } else {
                console.log(data.message);
            }
        });
    }
}

cancel_ticket_button.addEventListener("click", cancelTicket);
