let uuid = JSON.parse(localStorage.getItem("uuid")) || "";
console.log(uuid);
let baseURL = `http://localhost:8080`;
let buses = 0;
let users = 0;
let routes = 0;
let resid = document.getElementById("res-id");
fetchData("Bus/viewAllBus");
fetchData("viewall");
fetchData("route/viewall");
setTimeout(() => {
    // console.log(buses)
    showGraph();
}, 2000);

function fetchData(Query) {
    fetch(`${baseURL}/${Query}?key=${uuid}`)
        .then((Response) => {
            return Response.json();
        })
        .then((data) => {
            // console.log(data);

            Query == "Bus/viewAllBus"
                ? (buses = data.length)
                : Query == "viewall"
                ? (users = data.length)
                : (routes = data.length);
        });
}

function showGraph() {
    google.charts.load("current", { packages: ["corechart"] });
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ["Contry", "Mhl"],
            ["Buses", buses],
            ["Users", users],
            ["Routes", routes],
        ]);

        var options = {
            title: "Company Growth Chart",
            // is3D: true
        };

        var chart = new google.visualization.BarChart(
            document.getElementById("myChart")
        );
        chart.draw(data, options);
    }
}

document.addEventListener("DOMContentLoaded", function () {
    // Add event listener to the "Logout" link
    var logoutLink = document.getElementById("logout");
    logoutLink.addEventListener("click", function (event) {
        event.preventDefault(); // Prevent the default link behavior
        Swal.fire({
            title: "Logout Confirmation",
            text: "Are you sure you want to logout?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: "Logout",
            cancelButtonText: "Cancel",
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
        }).then((result) => {
            if (result.isConfirmed) {
                // Handle the logout action here (replace with your actual logout logic)
                Swal.fire({
                    title: "Logged Out!",
                    text: "You have been successfully logged out.",
                    icon: "success",
                    confirmButtonColor: "#3085d6",
                }).then(() => {
                    // Redirect to the login page or any other designated page for logging out
                    let url = `${baseURL}/admin/logout?key=${uuid}`;
                    console.log(uuid);
                    fetch(url, {
                        method: "POST",
                    })
                        .then((response) => response)
                        .then((data) => {
                            // openCustomAlert("User LogOut SucessFull!!");
                            localStorage.setItem(
                                "username",
                                JSON.stringify("")
                            );
                            localStorage.setItem("uuid", JSON.stringify(""));

                            console.log(data);
                        })
                        .catch((error) => {
                            console.error("Error posting data:", error);
                        });
                    window.location.href = "../index.html"; // Replace "login.html" with the actual logout page URL
                });
            }
        });
    });
});

function showResvereation() {
    fetch(`${baseURL}/reservation/viewall?key=${uuid}`)
        .then((Response) => {
            return Response.json();
        })
        .then((data) => {
            console.log(data);
            let cal = (data.length * 100) / 500;
            resid = cal + "%";
        });
}
