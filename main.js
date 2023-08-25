//require("data/scripts/chart.js");

function calculateArea(GraphData) {
    let area = 0;

    for (let i = 0; i < GraphData.length - 1; i++) {
        const wattage1 = GraphData[i];
        const wattage2 = GraphData[i + 1];
        const timeInterval = 5/60; // Assuming a uniform time interval of 5/60 units i.e 5 mins

        // Calculating the area of the trapezoid and adding it to the total area
        const trapezoidArea = (1/2) * (wattage1 + wattage2) * timeInterval;
        area += trapezoidArea;
    }

    return area;
}

function main(url) {
    //let url = "http://localhost:3000/2023-08-22.json";

    let SolarEnergyArray = [];
    //let TotalSolarOutput = 0;
    let TimeStamp = [];
    let ACOutput = [];

    fetch(url).then(
        response => {return response.json()}
    ).then(
        data => {
            for (let index = 0; index < data.dat.row.length; index++) {
                //ss += data.dat.row[index].field[42];
                SolarEnergyArray.push(Number(data.dat.row[index].field[42]));

                ACOutput.push(Number(data.dat.row[index].field[12]));
                
                //TimeStamp.push(new Date(data.dat.row[index].field[1]));
                TimeStamp.push(data.dat.row[index].field[1].split(" ")[1])
            }
        }
        ).then( () => {
            // Get the canvas element
            const ctx = document.getElementById('my_chart');

            new Chart(ctx, {
                type: 'line',
                data: {
                    //labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
                    labels: TimeStamp, // xlables
                    datasets: [ // ylables
                    {
                        label: 'Solar Production',
                        fill: 'origin',
                        //data: [12, 19, 3, 5, 2, 3],
                        data: SolarEnergyArray,
                        borderWidth: 1
                    },
                    {
                        label: 'Home Usage',
                        fill: "origin",
                        //data: [12, 19, 3, 5, 2, 3],
                        data: ACOutput,
                        borderWidth: 1
                    }
                ]
                },
                options: {
                    scales: {
                    x: {
                        title: {
                        display: true,
                        text: 'Time (H:M:S)',
                        },
                    },

                    y: {
                        title: {
                        display:true,
                        text: "Watts(J/s)"
                        },
                        beginAtZero: true,
                    }
                    }
                }
            });
            
            //<!----------------------------------------------------------------------------------------------------->
            
            //console.log(SolarEnergyArray);
            //console.log(SolarEnergyArray);
            //console.log("Solar Energy Produced: " + (calculateArea(SolarEnergyArray)/1000) + " Killo Watt Hours");
            //console.log("Home Usage: " + (calculateArea(ACOutput)/1000) + " Killo Watt Hours");

            document.getElementById("solar_output_day").textContent = "Solar Energy Produced: " + (calculateArea(SolarEnergyArray)/1000) + " Killo Watt Hours";
            document.getElementById("home_usage_day").textContent = "Home Usage: " + (calculateArea(ACOutput)/1000) + " Killo Watt Hours";

            
            //<!----------------------------------------------------------------------------------------------------->

        }
    );
}


document.addEventListener("DOMContentLoaded", 
    //main("http://localhost:3000/2023-08-23.json")
    //main("https://api.shinemonitor.com/public/?sign=cdcaf33d79bcd16737767a481aae358d561924d7&salt=1692738603930&token=6cb48f07fa045267adfceadf1a7f8d57e650f611293f873205a8a20c5422a693&action=queryDeviceDataOneDay&pn=W0820091184710&devcode=2400&sn=92932010100438&devaddr=1&date=2023-08-23&i18n=en_US&lang=en_US&source=1&_app_client_=android&_app_id_=wifiapp.volfw.watchpower&_app_version_=1.2.0.0")
    //main("http://api.shinemonitor.com/public/?sign=5e9441360750b3f09145115ef5d6a1259a9f3a29&salt=1692945743408&token=6cb48f07fa045267adfceadf1a7f8d57e650f611293f873205a8a20c5422a693&action=queryDeviceDataOneDay&pn=W0820091184710&devcode=2400&sn=92932010100438&devaddr=1&date=2023-08-24&i18n=en_US&lang=en_US&source=1&_app_client_=android&_app_id_=wifiapp.volfw.watchpower&_app_version_=1.2.0.0")
    main("http://api.shinemonitor.com/public/?sign=edc729277875ab35150f3b49d8d8b15ac7eaa1b5&salt=1692945688858&token=6cb48f07fa045267adfceadf1a7f8d57e650f611293f873205a8a20c5422a693&action=queryDeviceDataOneDay&pn=W0820091184710&devcode=2400&sn=92932010100438&devaddr=1&date=2023-08-25&i18n=en_US&lang=en_US&source=1&_app_client_=android&_app_id_=wifiapp.volfw.watchpower&_app_version_=1.2.0.0")

);