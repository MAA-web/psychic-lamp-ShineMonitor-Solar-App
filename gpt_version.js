const crypto = require("crypto");
//const fetch = require("node-fetch");

const SHA1SignHash = crypto.createHash("sha1");
const SHA1PwdHash = crypto.createHash("sha1");

let UserName = "nazir abida";
let Password = "ali ali ali";
let Salt = 1692449911453;//Date.now();
let CompanyKey = "bnrl_frRFjEz8Mkn&i18n";

// Calculate the SHA-1 hash of the password and ensure lowercase
SHA1PwdHash.update(Buffer.from(Password, "utf8"));
const hashedPassword = SHA1PwdHash.digest("hex").toLowerCase();

// Construct the SHA-1 hashed signature
SHA1SignHash.update(Buffer.from(Salt + hashedPassword + "&action=auth&usr=" + UserName + "&company-key=" + CompanyKey));
const signature = SHA1SignHash.digest("hex").toLowerCase();

// Construct the URL, properly encoded
let url = `http://api.shinemonitor.com/public/?sign=${signature}&salt=${Salt}&action=authSource&usr=${encodeURIComponent(UserName)}&company-key=${encodeURIComponent(CompanyKey)}=en_US&lang=en_US&source=1&_app_client_=android&_app_id_=wifiapp.volfw.watchpower&_app_version_=1.2.0.0`;

console.log(url);

// Make the HTTP GET request
fetch(url, {
  credentials: "omit",
  headers: {
    "User-Agent": "HI",
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8",
    "Accept-Language": "en-US,en;q=0.5",
    "Upgrade-Insecure-Requests": "1"
  },
  method: "GET",
  mode: "cors"
})
  .then(response => {
    if (!response.ok) {
      throw new Error("HTTP Error: " + response.status);
    }
    return response.text();
  })
  .then(data => {
    console.log(data); // Handle the response data as needed
  })
  .catch(error => {
    console.error(error); // Handle errors
  });
