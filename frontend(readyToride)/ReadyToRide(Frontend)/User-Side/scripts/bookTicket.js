let bookHeading = document.getElementById("book-heading");
let searchTicketForm = document.getElementById("search-ticket-form");
let fetchedBusContainer = document.getElementById("fetched-bus-container");

const currUser = JSON.parse(localStorage.getItem("uuid"));
let busDetails = [];

function createBusDiv(bus, departureDate) {
    let busDetailsContainer = document.createElement("div");
    busDetailsContainer.classList.add("bus-details-contaner");

    let travelClassDiv = document.createElement("div");
    travelClassDiv.classList.add("travels-name");

    let travelsName = document.createElement("h3");
    travelsName.innerText = bus.busName;

    let bustypep = document.createElement("p");
    bustypep.innerText = "Bus Type: " + bus.busType;

    travelClassDiv.appendChild(travelsName);
    travelClassDiv.appendChild(bustypep);

    let busTime = document.createElement("div");
    busTime.classList.add("bus-time");

    const table = document.createElement("table");
    table.id = "bus-timing-table";

    const tableHead = document.createElement("thead");

    const headCell1 = document.createElement("th");
    headCell1.innerText = "Departure";

    const headCell2 = document.createElement("th");
    const headCell3 = document.createElement("th");
    headCell3.innerText = "Arrival";

    tableHead.appendChild(headCell1);
    tableHead.appendChild(headCell2);
    tableHead.appendChild(headCell3);

    const tableBody = document.createElement("tbody");

    const bodyRow1 = document.createElement("tr");

    const bodyCell1 = document.createElement("td");
    bodyCell1.innerText = bus.departureTime;

    const bodyCell2 = document.createElement("td");
    bodyCell2.innerHTML = `<i class="fa-solid fa-arrow-right"></i>`;

    // Insert your custom content cell
    const customContentCell = document.createElement("td");
    customContentCell.innerText = bus.arrivalTime; // Replace with your content

    const bodyCell3 = document.createElement("td");
    bodyCell3.innerText = bus.arrivalTime;

    bodyRow1.appendChild(bodyCell1);
    bodyRow1.appendChild(bodyCell2);
    bodyRow1.appendChild(customContentCell); // Insert custom content cell

    const bodyRow2 = document.createElement("tr");

    const bodyCell4 = document.createElement("td");
    bodyCell4.innerText = bus.routeFrom;

    const bodyCell5 = document.createElement("td");
    // Leave this cell empty or add any relevant content

    const bodyCell6 = document.createElement("td");
    bodyCell6.innerText = bus.routeTo;

    bodyRow2.appendChild(bodyCell4);
    bodyRow2.appendChild(bodyCell5);
    bodyRow2.appendChild(bodyCell6);

    tableBody.appendChild(bodyRow1);
    tableBody.appendChild(bodyRow2);

    table.appendChild(tableHead);
    table.appendChild(tableBody);

    busTime.appendChild(table);

    let ticketPriceDetails = document.createElement("div");
    ticketPriceDetails.classList.add("ticket-price-details");

    let availableSeatsh2 = document.createElement("h2");
    availableSeatsh2.innerText = "Available Seats: " + bus.availableSeats;

    let bookButton = document.createElement("button");
    bookButton.id = "book-ticket";
    bookButton.innerText = "Book";

    bookButton.addEventListener("click", () => {
        const currBus = bus.busId;
        const bookApi = `http://localhost:8080/reservation/add/${currBus}?key=${currUser}`;

        let bodyToSend = {
            reservationDate: departureDate,
            source: bus.routeFrom,
            destination: bus.routeTo,
        };

        fetch(bookApi, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(bodyToSend),
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                alert(
                    "Ticket Booked Successfully!\nYour Ticket Id is " +
                        data.reservationId
                );
                window.location.href = "http://127.0.0.1:5500/index.html";
            })
            .catch((error) => {
                console.error("Error:", error);
                alert(error.message);
            });
    });

    ticketPriceDetails.appendChild(availableSeatsh2);
    ticketPriceDetails.appendChild(bookButton);

    busDetailsContainer.appendChild(travelClassDiv);
    busDetailsContainer.appendChild(busTime);
    busDetailsContainer.appendChild(ticketPriceDetails);

    console.log(busDetailsContainer);
    return busDetailsContainer;
}

function createBusDetails(busDetails, departureDate) {
    busDetails.forEach((e) => {
        fetchedBusContainer.appendChild(createBusDiv(e, departureDate));
    });
}

function checkBusDetails(data, fromCity, toCity, departureDate) {
    console.log(fromCity, toCity);
    data.forEach((element) => {
        if (element.routeFrom == fromCity || element.routeTo == toCity) {
            busDetails.push(element);
        }
    });
    if (busDetails.length == 0) {
        alert(
            "Dear Customer There Is No bus Available From " +
                fromCity +
                " to " +
                toCity
        );
        window.location.reload();
    }
    createBusDetails(busDetails, departureDate);
}

function displayBus(fromCity, toCity, departureDate) {
    if (currUser == null || currUser == "") {
        alert("Please Login to Check Available buses...!");
        window.location.href = "./login.html";
    }
    const fetchBusApi = `http://localhost:8080/Bus/viewAllBus?key=${currUser}`;
    fetch(fetchBusApi)
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            bookHeading.style.display = "none";
            checkBusDetails(data, fromCity, toCity, departureDate);
        })
        .catch((error) => {
            console.error("Error fetching bus details:", error);
        });
}

searchTicketForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const fromCity = event.target[0].value;
    const toCity = event.target[1].value;
    const departureDate = event.target[2].value;
    fetchedBusContainer.innerHTML = null;
    busDetails = [];
    displayBus(fromCity, toCity, departureDate);
});
