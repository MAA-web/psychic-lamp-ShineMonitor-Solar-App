import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

import java.util.Date;
import java.text.SimpleDateFormat;

public class login {
    public class Misc {
        
        // printf

        public static final void printf(String str, Object... objArr) {
            System.out.printf(str, objArr);
        }

        // Byte to String

        public static final String printf2Str(String str, Object... objArr) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new PrintStream(byteArrayOutputStream).printf(str, objArr);
            return byteArrayOutputStream.toString();
        }

        // Byte to Hex then to String

        public static final String byte2HexStr(byte[] bArr) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(byteArrayOutputStream);
            for (int i = 0; i < bArr.length; i++) {
                printStream.printf("%02X", new Object[]{Byte.valueOf(bArr[i])});
            }
            return byteArrayOutputStream.toString();
        }

        // SHA 1 encryption

        public static final byte[] sha1(byte[] bArr) {
            try {
                return MessageDigest.getInstance("SHA-1").digest(bArr);
            } catch (Exception e) {
                printf("call SHA-1 function failed: %s", e.toString());
                return null;
            }
        }
        
        // Converting SHA 1 to string and then Lower Case

        public static final String sha1StrLowerCase(byte[] bArr) {
            return byte2HexStr(sha1(bArr)).toLowerCase();
        }
    }


    private static String getBaseAction(String str) {//(Context context, String str) {
        //if (context == null) {
        //    return null;
        //}
        //String language = Utils.getLanguage(context);
        String language = "en_US";
        //if (appID == null) {
        //    appID = ((Context) Objects.requireNonNull(context)).getApplicationInfo().processName;
        //}
        String appID = "wifiapp.volfw.watchpower";
        //if (appVersion == null) {
        //    appVersion = AppUtils.getVersionName(context);
        //}
        String CLIENT_TYPE = "android";
        String appVersion = "1.2.0.0";
        return Misc.printf2Str("%s&i18n=%s&lang=%s&source=%s&_app_client_=%s&_app_id_=%s&_app_version_=%s", str, language, language, 1, CLIENT_TYPE, appID, appVersion);
    }


    public static final String KEY_COMPANY = "bnrl_frRFjEz8Mkn";
    
    // Getting the source login URL

    public static String getSourceLoginUrl (String Username, String Password) {
        try {
            String EncodedUsername_UTF_8 = URLEncoder.encode(Username, "UTF-8");
            String HashedPassword = Misc.sha1StrLowerCase(Password.getBytes());
            String baseAction = getBaseAction(Misc.printf2Str("&action=authSource&usr=%s&company-key=%s", EncodedUsername_UTF_8, KEY_COMPANY));
            String Salt = System.currentTimeMillis() + "";
            //String Salt = "1692449911453";
            return Misc.printf2Str("http://android.shinemonitor.com/public/" + "?sign=%s&salt=%s%s", Misc.sha1StrLowerCase((Salt + HashedPassword + baseAction).getBytes()), Salt, baseAction);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get Current Date

    public static String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentDate);
        return formattedDate;
    }

    // Format URL Makes the action url

    private static String getFormatUrl(String str, String str2, String Token, String Secret) {
        
        String Salt = System.currentTimeMillis() + "";
        
        //String baseAction = getBaseAction(str2);
        String baseAction = Misc.printf2Str("&action=queryDeviceDataOneDay&pn=W0820091184710&devcode=2400&sn=92932010100438&devaddr=1&date=2023-08-24&i18n=en_US&lang=en_US&source=1&_app_client_=android&_app_id_=wifiapp.volfw.watchpower&_app_version_=1.2.0.0");
        return Misc.printf2Str(str + "?sign=%s&salt=%s&token=%s%s", Misc.sha1StrLowerCase(( Salt + Secret + Token + baseAction).getBytes()), Salt, Token, baseAction);
    }

    // Query Data
    
    //String ownerUrl = VertifyUtils.getOwnerUrl(this.mContext, Misc.printf2Str("&action=queryDeviceDataOneDay&pn=%s&devcode=%s&sn=%s&devaddr=%s&date=%s", this.deviceBean.getPn(), Integer.valueOf(this.deviceBean.getDevcode()), this.deviceBean.getSn(), Integer.valueOf(this.deviceBean.getDevaddr()), Utils.getValueUrlEncode(this.dateTv.getText().toString().trim())));

    // Sending Get Request



    // Main Method

    public static void main(String[] args) {
        System.out.println("HI");
        //System.out.println(getSourceLoginUrl("nazir abida", "ali ali ali"));
        System.out.println(getCurrentDate());
        String Token;
        String Salt;
        String Secret;
        System.out.println(getFormatUrl("http://api.shinemonitor.com/public/", "nothing here", "6cb48f07fa045267adfceadf1a7f8d57e650f611293f873205a8a20c5422a693", "a10d2f9468caf7d9ef44c979b39bc64c1836a69b"));
        System.out.println("HI");
    }
}