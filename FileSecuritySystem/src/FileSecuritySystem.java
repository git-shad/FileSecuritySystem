import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import javax.crypto.*;

public class FileSecuritySystem extends CryptoFile{ //this is a main class
	static BufferedReader bufread = new BufferedReader(new InputStreamReader(System.in));
	static FunctionShort show = FunctionShort.startOfExecuted();
	static ThreadProgress progress = null;
	static File file = null;
	
	
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
			break;
		case 2:
			progress.textProcess = "Decrypting...";
			break;
		}
        
        progress.textStop= "  success process!";
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
















