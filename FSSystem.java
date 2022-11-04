package FileSecurity;

import java.io.*;
import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class FSSystem {
	public static FunctionShort con = FunctionShort.startOfExecuted();
	public static ThreadProgress progress = null;
	public static File file = null;
	
	//--------------------------------------
	static class CryptoFile{
		private final static String ALGORITHM = "AES";
		private final static String TRANSFORMATION = "AES/ECB/PKCS5PADDING";
		private final static String passKey = "PasswordFile";
		
		public static void fileSecurity(int cipherMode,String filePath){
			File fileOperate = new File(filePath);
			
			cryptoFile(cipherMode,fileOperate,new File(filePath));
		}
		
		private static void cryptoFile(int cripherMode,File inputFile,File outputFile){
			try {
				MessageDigest sha = MessageDigest.getInstance("SHA-1");
				byte[] key = passKey.getBytes("UTF-8");
					   key = sha.digest(key);
					   key = Arrays.copyOf(key, 16);
				
				Key secretKey = new SecretKeySpec(key,ALGORITHM);
				Cipher cipher = Cipher.getInstance(TRANSFORMATION);
				cipher.init(cripherMode, secretKey);
				
				FileInputStream inputStream = new FileInputStream(inputFile);
				byte []inputBytes = new byte[(int)inputFile.length()];
				inputStream.read(inputBytes);
				byte []outputBytes = cipher.doFinal(inputBytes);
				FileOutputStream outputStream = new FileOutputStream(outputFile);
				outputStream.write(outputBytes);
				
				inputStream.close();
				outputStream.close();
			} catch(NoSuchAlgorithmException  | 
					NoSuchPaddingException    | 
					InvalidKeyException       | 
					IOException               | 
					IllegalBlockSizeException | 
					BadPaddingException e) {
				con.write("\033[1B\r\033[0K"+e.getMessage()+"\033[1A");
			}
		}
	}
	//--------------------------------------
	
    //--------------------------------------
	public static class FunctionShort {
		public void writeln(String arg) {
			if(arg != null)
				System.out.println(arg);
		}
		public void write(String arg) {
			if(arg != null)
				System.out.print(arg);
		}
		public void write(String format,Object... objects) {
			if(format != null)
				System.out.printf(format,objects);
		}			
		public FunctionShort() {
			//default contractor
		}		
		private static long executedTime = 0;
			
		public long getExecutedTime() {
			long endTime = System.nanoTime() - executedTime;
			return (endTime / 100000000);
		}
			
		private static void setExecutedTime(long executedTime) {
			FunctionShort.executedTime = executedTime;
		}
			
		public static FunctionShort startOfExecuted() {
			setExecutedTime(System.nanoTime());
			return new FunctionShort();
		}
		public void gotoxy(int row,int column) {
			this.write("\033["+row+";"+column+"f");
		}
		public void goto_row(int row) {
			this.gotoxy(row, 0);
		}
		public void goto_column(int column) {
			this.gotoxy(0, column);
		}
		public void cls() {
			this.write("\033[2J");
			this.goto_row(0);
		}
	}
	//--------------------------------------
	
	//--------------------------------------
	static class ThreadProgress extends Thread{
		
		public int lenght = 50;
		public int speed = 20;
		public String textProcess = "loading...",
					  textStop = "done!";
		private boolean exit = false;
		
		private FunctionShort con = new FunctionShort();
		@Override
		public void run() {
			try {
				int tmp = this.lenght - 1;
				boolean cond = true;
				while(!exit) {
					
					String open = textProcess + " [",
						   right = "",
						   icon = "<-=->",
						   left = "",
						   close = "]";
					
					for(int i = 0; i < lenght - tmp;++i) {
						right +=  " ";
					}
					open += right + icon;
					for(int i = 0; i < tmp ;++i) {
						left += " ";
					}
					open += left + close;
					con.write(open + "\033[38;5;214m\r\b");
					
					if(cond) {
						--tmp;
						if(tmp == 0) {
							cond = false;
						}
					}else {
						++tmp;
						if(tmp == lenght) {
							cond = true;
						}
					}
					Thread.sleep(speed);
				}
				
			}catch(Exception e){
				//end running
			}
		}
		
		public void _stop() {
			this.exit = true;
			con.write("\n\033[0m    "+textStop+"\033[0m");
		}
		
	}
	//--------------------------------------
	
	private static void printBanner(FunctionShort con) {
		
		con.write("\033[38;5;44m"
				+ " ███████╗██╗██╗     ███████╗                                         \r\n"
				+ " ██╔════╝██║██║     ██╔════╝                                         \r\n"
				+ " █████╗  ██║██║     █████╗                                           \r\n"
				+ " ██╔══╝  ██║██║     ██╔══╝                                           \r\n"
				+ " ██║     ██║███████╗███████╗                                         \r\n"
				+ " ╚═╝     ╚═╝╚══════╝╚══════╝ \033[38;5;27mMR.SHAD | 228025\033[38;5;44m\r\n"
				+ "                                                                     \r\n" 
				+ " ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗██╗██╗\r\n"
				+ " ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝██║██║\r\n"
				+ " ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ ██║██║\r\n"
				+ " ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  ╚═╝╚═╝\r\n"
				+ " ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   ██╗██╗\r\n"
				+ " ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   ╚═╝╚═╝\r\n"
				+ "                                                \033[38;5;27mconsole application\033[38;5;44m\r\n"
				+ "══════════════════════════════════════════════════════════════════════\033[0m\r\n");
		con.write("\033[?25l");
		
	}
	
	public static boolean run = true;
	public static void exit() throws InterruptedException {
		progress = new ThreadProgress();//create thread for process
		progress.textProcess = "Exiting...";
		progress.start();//start progress
		Thread.sleep(2001);//useless, i add this for showing progress
		progress._stop();//end progress
	}
	
	public static String[] checkFileInfo(File file) throws InterruptedException {
		progress = new ThreadProgress();//create progress
		progress.start();//start progress
		int n = 0;//start 0
		int issue = 0;
		String []paths = new String[(int)file.length()];//limit
		
		for(File f: file.listFiles()) {//get fullname
			if(f.isFile()) {
				paths[n++] = f.getAbsolutePath();
			}else {
				++issue;
			}
			Thread.sleep(21);
		}
		
		progress._stop();
		
		for(String path:paths) {//over all files
			if(path == null) {
				break;
			}
			con.write("\r\033[0K\033[38;5;47m"+path);
			Thread.sleep(10);
		}con.write("\033[38;5;45m\r\033[0K [ count file issue's ] : "+issue+"\n [ over all file ] : "+(n+1)+"\033[0m\n");
		
		return paths;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		BufferedReader bufread = new BufferedReader(new InputStreamReader(System.in));
	    con.cls();
		printBanner(con);
		while(run) {//run true because it is continuing code
			
			con.write("\n path : ");
			String inp = bufread.readLine();//get directory nor file
			
			if(inp.equalsIgnoreCase("exit")) {//if you want to exit this program
				run = false;//stop loops
				exit();
			}else {
				file = new File(inp);
				if(file.exists()) {//test if exists file
					if(file.isFile()) {//test if it file 
						fileOperate(checkFileInfo(file));
					}else if(file.isDirectory()) {//else if is directory
						fileOperate(checkFileInfo(file));
					}//end
				}else {
					con.write("\033[38;5;196merror message:\033[38;5;198m can't identify if file or director is!\033[0m\n");//message error
				}//end
			}
		}
	}
	
	private static void fileOperate(String[] paths) throws IOException, InterruptedException {
		String []color = new String[] {"\033[0m","\033[38;5;0m\033[48;5;15m"};
		int n0 = 0,n1 = 0, inp = 0, mode = 0;
		String tmp = "";
		boolean bool = true;
		BufferedReader bufread = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			con.write
			("\n\t[1] "+color[n0]+"Encrypt File"+color[0]+"\r\n"
			+"\t[2] "+color[n1]+"Decrypt File"+color[0]+"\r\n"
			+"\n     -----------------------\r\n");
			if(bool) {
				con.write("\tnum : ");
				try {
					inp = Integer.parseInt(bufread.readLine().toCharArray()[0]+"");//get first char in string and convert it to integer
					
					if(inp == 49 || inp == 1) {
						n0 = 1;
						n1 = 0;
						bool = !bool;
						mode = Cipher.ENCRYPT_MODE;
					}else if(inp == 50 || inp == 2) {
						n0 = 0;
						n1 = 1;
						bool = !bool;
						mode = Cipher.DECRYPT_MODE;
					}
				}catch(Exception e) {
					//ignored
				}
				con.write("\r\033[7A");
			}else {
				bool = !bool;
				con.write("\n");
				while(bool) {
					con.write("  permission to contiue this opetation [Y/n]: ");
					try {
						tmp = bufread.readLine().toCharArray()[0]+"";
						
						if(tmp.equalsIgnoreCase("y")){
							bool = !bool;//kill 2 while's
						}else {
							con.write("\r\033[8A");
							break;//kill parent while
						}
					}catch(Exception e) {
						//ignored
					}
					con.write("\r\033[1A");
				}
				if(!bool) {
					break;
				}				
			}
		}
		
		progress = new ThreadProgress();
		
		switch(mode) {
		case 1:
			progress.textProcess = "Encrypting...";
			progress.textStop= "  success encryption process!";
			break;
		case 2:
			progress.textProcess = "Decrypting...";
			progress.textStop= "  success decryption process!";
			break;
		}
		progress.speed = 10;
		progress.start();
		con.write("\n");
		Thread.sleep(20);//wait 20 milisec for refreshing
		for(String path: paths) {
			if(path == null) {
				break;
			}
			Thread.sleep(10);
			CryptoFile.fileSecurity(mode, path);
		}
		progress._stop();
	}//end fileOpetion
}
















