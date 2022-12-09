import java.io.*;
import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class CryptoFile extends FunctionShort{//file manipulation 

    //----------------------------------------
    private final static String ALGORITHM = "AES";//advance encryption standard
                                                  //AES is 128-bit symmetric block
    private final static String TRANSFORMATION = "AES/ECB/PKCS5PADDING";

    private final static String passKey = "\\37AB9\\N";//warning: do not try to edit this password if you are not a developer
    private static String getCryptFileFullPath = null;//if not change, default file name is null
    static FunctionShort show = new FunctionShort();
    //----------------------------------------
    
    public static File fileSecurity(int cipherMode,String filePath){
        File fileOperate = new File(filePath);

        cryptoFile(cipherMode,fileOperate,fileOperate);
        return new File(getCryptFileFullPath);
    }
    
    private static void cryptoFile(int cripherMode,File inputFile,File outputFile){
        boolean repeat = false;
        if(cripherMode == Cipher.DECRYPT_MODE){
            repeat = true;
        }
        
        do{
            try {
                //create secret key
                MessageDigest sha = MessageDigest.getInstance("SHA-512");
                byte[] key = passKey.getBytes("UTF-8");
                       key = sha.digest(key);
                       key = Arrays.copyOf(key, 16);
                //--------------------------
                
                Key secretKey = new SecretKeySpec(key,ALGORITHM);
                Cipher cripher = Cipher.getInstance(TRANSFORMATION);
                
                FileInputStream inputStream = new FileInputStream(inputFile);
                byte []inputBytes = new byte[(int)inputFile.length()];
                inputStream.read(inputBytes);
                
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                byte[] out = null;
    
                try{
                    cripher.init(cripherMode, secretKey);
                    out = cripher.doFinal(inputBytes);//change the data 
                }catch(IllegalBlockSizeException e){
                    out = inputBytes;
                    repeat = false;
                }
                
                outputStream.write(out);
                
                //close in/out file
                inputStream.close();
                outputStream.close();
                //-----------------------
    
                getCryptFileFullPath = outputFile.getAbsolutePath();//set encrypted or decrypted file absolute path 
    
            } catch(NoSuchAlgorithmException  | 
                    NoSuchPaddingException    | 
                    InvalidKeyException       | 
                    IOException               | 
                    BadPaddingException 	  | 
                    OutOfMemoryError e) {
                show.write("From: "+inputFile.getAbsolutePath()+"\r\033[2k\n"
                        + "  Error: "+e.getMessage()+"\r\n");
            }
        }while(repeat);//repeat to decrypting multiple encryption
    }
}