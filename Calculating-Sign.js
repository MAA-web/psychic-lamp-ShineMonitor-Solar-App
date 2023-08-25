const { timeStamp } = require("console");
const crypto = require("crypto");

const SHA1SignHash = crypto.createHash("sha1");

const SHA1PwdHash = crypto.createHash("sha1");


let UserName = "nazir abida";
let Password = "ali ali ali";
let Salt = 1692449911453;//Date.now();
let CompanyKey = "bnrl_frRFjEz8Mkn";


//SHA1PwdHash.update(Password);
SHA1SignHash.update(Buffer.from(Salt + SHA1PwdHash.update(Buffer.from(Password)).digest("hex") + "&action=authSource&usr=" + UserName + "&company-key=" + CompanyKey))

console.log(UserName);
console.log(Password);

//console.log(SHA1SignHash)
//console.log(SHA1PwdHash.digest("hex"));
//console.log(SHA1SignHash.digest("hex"));

console.log(Salt);

//http://android.shinemonitor.com/public/?sign=b0b6bc8e31d6e2f6d8b883677821dade7983f23a&salt=1692449911453&action=authSource&usr=nazir+abida&company-key=bnrl_frRFjEz8Mkn&i18n=en_US&lang=en_US&source=1&_app_client_=android&_app_id_=wifiapp.volfw.watchpower&_app_version_=1.2.0.0


//b0b6bc8e31d6e2f6d8b883677821dade7983f23a
//1692449911453


let url = `http://api.shinemonitor.com/public/?sign=${SHA1SignHash.digest("hex")}&salt=${Salt}&action=authSource&usr=${UserName}&company-key=${CompanyKey}&i18n=en_US&lang=en_US&source=1&_app_client_=android&_app_id_=wifiapp.volfw.watchpower&_app_version_=1.2.0.0`.replace(" ", "+");



console.log(url);


fetch(url, {
    "credentials": "omit",
    "headers": {
        //"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/116.0",
        "User-Agent": "HI",
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8",
        "Accept-Language": "en-US,en;q=0.5",
        "Upgrade-Insecure-Requests": "1"
    },
    "method": "GET",
    "mode": "cors"
}).then(
    (response) => {
        return response.text();
    }
).then(
    data => {
        console.log(data);
    }
);
