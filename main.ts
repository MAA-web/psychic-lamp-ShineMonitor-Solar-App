/*let url = "http://localhost:3000/2023-08-19.json";

let SolarEnergyArray : any = [];

fetch(url).then(
    response => {return response.json()}
).then(
    data => {
        for (let index = 0; index < data.dat.row.length; index++) {
            //ss += data.dat.row[index].field[42];
            SolarEnergyArray.push(data.dat.row[index].field[42])
        }
    }
);

console.log(SolarEnergyArray);*/