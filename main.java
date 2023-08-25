import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Key;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ShineMonitorOpenApi
{
    /** shinemonitor Open platform API Access Address. */
    private static String OPEN_API_URI = "http://api.shinemonitor.com/public/";

    public static final void main(String[] args)
    {
        ShineMonitorOpenApi.reg();
        ShineMonitorOpenApi.auth();
        ShineMonitorOpenApi.authPassed();
    }

    /** Authentication interface. */
        private static final void auth()
        {
            String usr = "vplant"; /* Account, password, and manufacturer identification assigned by the platform. */
            String pwd = "vplant";
            String companyKey = "0123456789ABCDEF";
            String source = "0";
            String _app_id_ = "com.demo.test";
            String _app_version_ = "3.6.2.1";
            String _app_client_ = "android";
            String salt = System.currentTimeMillis() + ""; /* salt value. */
            String sha1Pwd = ShineMonitorOpenApi.sha1ToLowerCase(pwd.getBytes()); /* SHA-1(pwd). */
            String action = "&action=auth&usr=" + usr /* Note: URLEncoder.encode encoding is required for Chinese */ +                         "&company-key=" + companyKey  + "&source=" + source + "&_app_id_=" + _app_id_ + "&_app_version_=" + _app_version_              +"&_app_client_=" + _app_client_;
             /* SHA-1(slat + SHA-1(pwd) + action). */
            String sign = ShineMonitorOpenApi.sha1ToLowerCase((salt + sha1Pwd + action).getBytes());
            String request = ShineMonitorOpenApi.OPEN_API_URI + "?sign=" + sign + "&salt=" + salt + action;
            System.out.println(request);
        }


        /** Interface for registering Owner account. */
        private static final void reg()
        {
            String usr = "plant-user-01";
            String pwd = "plant-user-01";
            String mobile = "15889700000";
            String email = "eybond@eybond.com";
            String pn = "0123456789ABCD";
            String sn = pn;
            String companyKey = "0123456789ABCDEF";
            String source = "0";
            String _app_id_ = "com.demo.test";
            String _app_version_ = "3.6.2.1";
            String _app_client_ = "android";
            String salt = System.currentTimeMillis() + ""; /* salt value. */
            String pwdSha1 = ShineMonitorOpenApi.sha1ToLowerCase(pwd.getBytes()); /* SHA-1(PWD). */
            String pnSha1 = ShineMonitorOpenApi.sha1ToLowerCase(pn.getBytes()); /* SHA-1(PN). */
            byte[] rc4 = ShineMonitorOpenApi.rc4enc(pnSha1.getBytes(), pwdSha1.getBytes(), 0, pwdSha1.getBytes().length); /*                 RC4(SHA-1(PN), SHA-1(PWD)). */
            String action = "&action=reg&usr=" + usr /*  Note: URLEncoder.encode encoding is required for Chinese. */ + "&pwd="               + ShineMonitorOpenApi.byte2HexStr(rc4, 0, rc4.length).toLowerCase();
            action += "&mobile=" + mobile + "&email=" + email + "&sn=" + sn + "&company-key=" + companyKey  + "&source=" 
                    + source + "&_app_id_=" + _app_id_ + "&_app_version_=" + _app_version_ + "&_app_client_=" + _app_client_;
            String sign = ShineMonitorOpenApi.sha1ToLowerCase((salt + pwdSha1 + action).getBytes());
            String request = ShineMonitorOpenApi.OPEN_API_URI + "?sign=" + sign + "&salt=" + salt + action;
            System.out.println(request);
        }

        /** Certified Business API Interface. */
        private static final void authPassed()
        {
            String secret = "ffa1655ee3726840822063a02ac5017795809b18"; /* Secrets and tokens after authentication. */
            String token = "88d22d819e31897eea2d9d5b9f7792cf4065ac5372aad3672f5e4e147cd25b5f";
            String source = "0";
            String _app_id_ = "com.demo.test";
            String _app_version_ = "3.6.2.1";
            String _app_client_ = "android";
            String salt = System.currentTimeMillis() + ""; /* salt value. */
            String action = "&action=queryCollectorInfo&pn=G0916522248153"  + "&source=" + source + "&_app_id_=" 
                    + _app_id_ + "&_app_version_=" + _app_version_ + "&_app_client_=" + _app_client_;
            String sign = ShineMonitorOpenApi.sha1ToLowerCase((salt + secret + token + action).getBytes()); /* SHA-1(slat +                   secret + token + action). */
            String request = ShineMonitorOpenApi.OPEN_API_URI + "?sign=" + sign + "&salt=" + salt + "&token=" + token + action;
            System.out.println(request);
        }

    /** Convert byte stream to hexadecimal string (compact format, no spaces). */
    private static final String byte2HexStr(byte by[], int ofst, int len)
    {
        if (len < 1)
            return "";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        for (int i = ofst; i < ofst + len; i++)
            ps.printf("%02X", by[i]);
        return bos.toString();
    }

    /** SHA-1 digest algorithm. */
    private static final String sha1ToLowerCase(byte[] by)
    {
        try
        {
            byte dat[] = MessageDigest.getInstance("SHA-1").digest(by);
            return ShineMonitorOpenApi.byte2HexStr(dat, 0, dat.length).toLowerCase();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /** RC4 encryption. */
    public static final byte[] rc4enc(byte key[], byte[] org, int ofst, int len)
    {
        try
        {
            Key k = new SecretKeySpec(key, "RC4");
            Cipher cip = Cipher.getInstance("RC4");
            cip.init(Cipher.ENCRYPT_MODE, k);
            return cip.doFinal(org, ofst, len);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}