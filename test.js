const { response } = require("express");

var Token;
var Secret;

async function sha1(bArr) {
    try {
        const encoder = new TextEncoder();
        const data = encoder.encode(bArr);

        const buffer = await crypto.subtle.digest('SHA-1', data);
        const hashArray = Array.from(new Uint8Array(buffer));
        const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
        
        return hashHex;
    } catch (error) {
        console.error("call SHA-1 function failed:", error.toString());
        return null;
    }
}

async function getLogindUrl(baseAction, Salt, HashedPassword) {
    let sign = await sha1(Salt + HashedPassword + baseAction);
    let url = `http://api.shinemonitor.com/public/?sign=${sign}&salt=${Salt}${baseAction}`;
    return url;
}


async function NewAPICall() {
    let Salt = Date.now();//"1692449911453"; // Sample Salt
    let Password = "ali ali ali"; // Sample Password

    date = "2023-08-26";

    let baseAction = "&action=authSource&usr=nazir+abida&company-key=bnrl_frRFjEz8Mkn&i18n=en_US&lang=en_US&source=1&_app_client_=android&_app_id_=wifiapp.volfw.watchpower&_app_version_=1.2.0.0";
    let baseActionQuery = `&action=queryDeviceDataOneDay&pn=W0820091184710&devcode=2400&sn=92932010100438&devaddr=1&date=${date}&i18n=en_US&lang=en_US&source=1&_app_client_=android&_app_id_=wifiapp.volfw.watchpower&_app_version_=1.2.0.0`;

    let HashedPassword = await sha1(Password);
    //let formattedUrl = await getFormattedUrl(baseActionQuery, Salt);

    //console.log(formattedUrl);
    let result = await getLogindUrl(baseAction, Salt, HashedPassword);
    console.log(result);
}

fetch("http://localhost:3000/scripts/login.json")
.then (
    response => {
    return response.json();
}
).then(
    data => {
        console.log(data.dat.secret);
        console.log(data.dat.token);
    }
)