import java.io.*;
import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class CryptoFile extends FunctionShort{
    private final static String ALGORITHM = "AES";
    private final static String TRANSFORMATION = "AES/ECB/PKCS5PADDING";
    private final static String passKey = "PasswordFile";
    private final static String ext = ".se$";//secured by encryption
    private static String getCryptFileFullPath = "null";//if not change, default file name is null
    
    static FunctionShort show = new FunctionShort();

    public static File fileSecurity(int cipherMode,String filePath){
        File fileOperate = new File(filePath);
        
        cryptoFile(cipherMode,fileOperate,fileOperate);
        return new File(getCryptFileFullPath);
    }
    
    private static void cryptoFile(int cripherMode,File inputFile,File outputFile){
        try {
            //create secret key
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key = passKey.getBytes("UTF-8");
                   key = sha.digest(key);
                   key = Arrays.copyOf(key, 16);
            //--------------------------
            
            Key secretKey = new SecretKeySpec(key,ALGORITHM);
            Cipher cripher = Cipher.getInstance(TRANSFORMATION);
            
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte []inputBytes = new byte[(int)inputFile.length()];
            inputStream.read(inputBytes);
            cripher.init(cripherMode, secretKey);
            
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            byte[] out = cripher.doFinal(inputBytes);
            outputStream.write(out);
            
            inputStream.close();
            outputStream.close();
            getCryptFileFullPath = outputFile.getAbsolutePath();
        } catch(NoSuchAlgorithmException  | 
                NoSuchPaddingException    | 
                InvalidKeyException       | 
                IOException               | 
                IllegalBlockSizeException | 
                BadPaddingException 	  | 
                OutOfMemoryError e) {
            show.write("From: "+inputFile.getAbsolutePath()+"\r\033[2k\n"
                    + "  Error: "+e.getMessage()+"\r\n");
        }
    }
}