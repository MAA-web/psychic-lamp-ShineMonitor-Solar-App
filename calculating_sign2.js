function sha1(bArr) {
    try {
        const crypto = require('crypto');
        const hash = crypto.createHash('sha1');
        hash.update(bArr);
        return hash.digest("hex");
    } catch (error) {
        console.error("call SHA-1 function failed:", error.toString());
        return null;
    }
}

function sha1StrLowerCase(bArr) {
    return (sha1(bArr)).toString().toLocaleLowerCase();
}

function getFormattedUrl(baseAction, Salt, HashedPassword) {
    let sign = sha1StrLowerCase(Salt + HashedPassword + baseAction);
    let url = `http://api.shinemonitor.com/public/?sign=${sign}&salt=${Salt}${baseAction}`;
    return url;
}

let Salt = "1692449911453"; // Sample Salt
let Password = "ali ali ali"; // Sample Password
let baseAction = "&action=authSource&usr=nazir+abida&company-key=bnrl_frRFjEz8Mkn&i18n=en_US&lang=en_US&source=1&_app_client_=android&_app_id_=wifiapp.volfw.watchpower&_app_version_=1.2.0.0";

let HashedPassword = sha1StrLowerCase(Password);
let formattedUrl = getFormattedUrl(baseAction, Salt, HashedPassword);

console.log(formattedUrl);
