package FileSecurity;

import java.io.*;
import java.nio.file.Paths;
import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class FSSystem {//File Security System
	static BufferedReader bufread = new BufferedReader(new InputStreamReader(System.in));
	static FunctionShort show = FunctionShort.startOfExecuted();
	static ThreadProgress progress = null;
	static File file = null;
	
	//--------------------------------------
	static class CryptoFile{
		private final static String ALGORITHM = "AES";
		private final static String TRANSFORMATION = "AES/ECB/PKCS5PADDING";
		private final static String passKey = "PasswordFile";
		private final static String ext = ".se$";//secured by encryption
		private static String getCryptFileFullPath = "null";//if not change, default file name is null
		
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
		
		private FunctionShort show = new FunctionShort();
		@Override
		public void run() {
			try {
				int tmp = this.lenght - 1;
				boolean showd = true;
				
				while(!exit) {
 					//create progress string
					String open = textProcess + " [",
						   right = "",
						   ishow = "<-=->",
						   left = "",
						   close = "]";
					
					for(int i = 0; i < lenght - tmp;++i) {
						right +=  " ";
					}
					open += right + ishow;
					for(int i = 0; i < tmp ;++i) {
						left += " ";
					}
					open += left + close;
					//end create progress
					
					show.write(open + "\033[38;5;214m\r\b");//reset and show
					
					//move animation
					if(showd) {
						--tmp;
						if(tmp == 0) {
							showd = false;
						}
					}else {
						++tmp;
						if(tmp == lenght) {
							showd = true;
						}
					}//----
					Thread.sleep(speed);
				}
				
			}catch(Exception e){
				//end running
			}
		}
		
		public void _stop() {
			this.exit = true;
			show.write("\n\033[0m    "+textStop+"\033[0m");
		}
		
	}
	//--------------------------------------
	
	private static void printBanner(FunctionShort show) {
		show.cls();
		show.write("\033[38;5;44m\n"
				+ "                  ███████╗██╗██╗     ███████╗                        \r\n"
				+ "                  ██╔════╝██║██║     ██╔════╝                        \r\n"
				+ "                  █████╗  ██║██║     █████╗                          \r\n"
				+ "                  ██╔══╝  ██║██║     ██╔══╝                          \r\n"
				+ "                  ██║     ██║███████╗███████╗                        \r\n"
				+ "                  ╚═╝     ╚═╝╚══════╝╚══════╝ \033[38;5;27mMR.SHAD | 228025\033[38;5;44m\r\n"
				+ "                                                                     \r\n" 
				+ " ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗██╗██╗\r\n"
				+ " ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝██║██║\r\n"
				+ " ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ ██║██║\r\n"
				+ " ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  ╚═╝╚═╝\r\n"
				+ " ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   ██╗██╗\r\n"
				+ " ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   ╚═╝╚═╝\r\n"
				+ "                                                \033[38;5;27mconsole application\033[38;5;44m\r\n"
				+ "══════════════════════════════════════════════════════════════════════\033[0m\r\n");
		show.writeln("\033[?25l"
				 + " native dir : " + (new File("").getAbsolutePath()));
		
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
		
		try {
			for(File f: file.listFiles()) {//get fullname
				if(f.isFile()) {
					paths[n++] = f.getAbsolutePath();
				}else {
					++issue;
				}
				Thread.sleep(21);
			}
		}catch(NullPointerException e) {
			if(file.isFile()) {
				paths[n] = file.getAbsolutePath();
			}
		}
		
		progress._stop();
		
		for(String path:paths) {//over all files
			if(path == null) {
				break;
			}
			show.write("\r\033[0K\033[38;5;47m"+path);
			Thread.sleep(10);
		}show.write("\033[38;5;45m\r\033[0K [ count file issue's ] : "+issue+"\n [ over all file ] : "+(n+1)+"\033[0m\n");
		
		return paths;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		printBanner(show);
		
		while(run) {//run true because it is continuing code
			
			show.write("\n path : ");
			String inp = bufread.readLine();//get directory nor file
			
			file = Paths.get(inp).toFile();
			
			if(inp.equalsIgnoreCase("exit")) {//if you want to exit this program
				run = false;//stop loops
				exit();
			}
			
			if(file.exists()) {//test if exists file
				if(file.isFile()) {//test if it file 
				
					fileOperate(checkFileInfo(file));
				}else if(file.isDirectory()) {//else if is directory
				
					fileOperate(checkFileInfo(file));
				}//end
			}else {
				show.write("\033[38;5;196merror message:\033[38;5;198m can't identify if file or director is!\033[0m\n");//message error
			}//end
				
			
		}
	}
	
	private static void fileOperate(String[] paths) throws IOException, InterruptedException {
		String []color = new String[] {"\033[0m","\033[38;5;0m\033[48;5;15m"};
		int n0 = 0,n1 = 0, inp = 0, mode = 0;
		String tmp = "";
		boolean bool = true;
		
		while(true) {
			show.write
			("\n\t[1] "+color[n0]+"Lock File"+color[0]+"\r\n"
			+"\t[2] "+color[n1]+"UnLock File"+color[0]+"\r\n"
			+"\n     -----------------------\r\n");
			if(bool) {
				show.write("\tnum : ");
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
				show.write("\r\033[6A");
			}else {
				bool = !bool;
				show.write("\n");
				
				while(bool) {
					show.write("  permission to showtiue this opetation [Y/n]: ");
					try {
						tmp = bufread.readLine().toCharArray()[0]+"";
						
						if(tmp.equalsIgnoreCase("y")){
							bool = !bool;//kill 2 while's
						}else {
							show.write("\r\033[7A");
							break;//kill parent while
						}
					}catch(Exception e) {
						//ignored
					}
					show.write("\r\033[1A");
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
		show.write("\n");
		Thread.sleep(20);//wait 20 milisec for refreshing
		List<String> list = new LinkedList<String>();//collect file 
		for(String path: paths) {
			if(path == null) {
				break;
			}
			Thread.sleep(10);
			file = CryptoFile.fileSecurity(mode, path);
			
			if(file.exists() && list.add(file.getAbsolutePath())) {
				continue;
			}
		}
		progress._stop();
		
	}//end fileOpetion
}
















