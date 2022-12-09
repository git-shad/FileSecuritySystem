import java.io.*;
import java.nio.file.*;
import javax.crypto.*;

public class FileSecuritySystem extends CryptoFile  { //this is a main class
	
	//================================================================================================//
	//====MAIN====//
	public static void main(String... args){
	try{
		printBanner();//display logo
		while(run) {//run true because it is continuing code
			try{
				
				//--------------------------------------
				show.writeln("┌"+ pathDesign());
				show.write("└\033[38;5;27m/\033[38;5;46msecure \033[38;5;44m>>\033[0m");
				String inp = bufread.readLine();//get directory nor file
				BuildCommandLine bcl = BuildCommandLine.cl(inp.trim());
				//--------------------------------------
				
				//--------------------------------------//
				//       change directory command       //
				//--------------------------------------//
				if(bcl.arg(0, "/cd", true)){
					String path = "";
					for(int i = 1; i < bcl.size() ;++i){
						path += bcl.arg(i) + " ";
					}
					path = path.trim();
					file = new File(path);
					
					if(file.isAbsolute()){
						currentDirectory = path;
					}
					throw new Exception("ignored");//shift
				}else
				//--------------------------------------

				//--------------------------------------//
				//             help command             //
				//--------------------------------------//
				if(bcl.arg(0, "/hlp", true)){
					help();
					throw new Exception("ignored");//shift
				}
				//--------------------------------------//

				file = Paths.get(inp).toFile();
				
				//--------------------------------------//
				if(!file.isDirectory()){
					String path = currentDirectory + "\\"+ inp;
					if(new File(path).exists())
					file = new File(path);
				}
				//--------------------------------------
				
				//--------------------------------------//
				//              exit command            //
				//--------------------------------------//
				if(bcl.arg(0,"/exit",true)) {//if you want to exit this program
					run = false;//stop loops
					exit();//exiting to the program
				}else
				//--------------------------------------//

				//--------------------------------------//
				//             reset command            //
				//--------------------------------------//
				if(bcl.arg(0,"/reset",true)){//reset for cleaning
					
					show.cls();//clear screen
					
					try{
						switch(bcl.arg(1)){
							case "--off":
								resetON = false;
							break;
							case "--on":
								resetON = true;
							break;
						}
					}catch(Exception e){
						throw new Exception("ignored");
					}

					if(resetON){
						printBanner();//display logo
					}

				}else
				//--------------------------------------//

				//--------------------------------------//
				//           clear screen command       //
				//--------------------------------------//
				if(bcl.arg(0, "/clear", true)){

					show.cls();//command for clear screen

				}else
				//--------------------------------------//

				//--------------------------------------//
				//statment: if input have no value or   //
				//          empty, it will be ignored.  //
				//--------------------------------------//
				if(inp.length() == 0){//if no input exists 
					throw new Exception("ignored");//ignored exception
				}else
				//--------------------------------------//

				//--------------------------------------//
				//statment: test the file if it zero of //
				//          not.                        //
				//--------------------------------------//
				if(!(file.length() != 0) && file.exists() && !(file.isDirectory())){//if file has zero byte
					throw new Exception("File 0 size is not encryptable");//throw the exception message 
					
				}else 
				//--------------------------------------//

				//--------------------------------------//
				if(file.exists()) {//test if exists file
					
					if(file.isFile()) {//test if it file 
						
						fileOperate(checkFileInfo(file));

					}else if(file.isDirectory()) {//else if is directory
						
						fileOperate(checkFileInfo(file));
					}//end
				}else { //throwing exception
					throw new Exception("not valid command");
				}//end
				//--------------------------------------
			
			}catch(Exception e){
				
				String msg = e.getMessage();
				
				if(e.getMessage().contains("Trailing char")){//for space exception
					msg = "spacing is not valid for command nor path!";
				}else if(e.getMessage().contains("not valid command")){
					msg = "this is not part of may program :(";
				}

				if(!msg.equalsIgnoreCase("ignored")){
					show.write("\033[38;5;196m warning! : \033[38;5;159m"+msg+"\033[0m\n\n");//message warning
				}
			}
		}//end while
	}catch(Exception e){
		//ignored for exiting console
		//make safe to exiting
	}
	}
	//================================================================================================//





	//----------------------------------------
	static BufferedReader bufread = new BufferedReader(new InputStreamReader(System.in));
	static FunctionShort show = FunctionShort.startOfExecuted();
	static ThreadProgress progress = null;
	static File file = null;
	static boolean run = true, resetON = true;
	static String currentDirectory = (new File("").getAbsolutePath());
	//-----------------------------------------
	
	
	
	
	
	
	//--------------------------------------
	//this function is for displaying the logo
	private static void printBanner() {
		show.cls();
		show.write("\033[38;5;44m\n"
				+ "                  ███████╗██╗██╗     ███████╗                        \r\n"
				+ "                  ██╔════╝██║██║     ██╔════╝                        \r\n"
				+ "                  █████╗  ██║██║     █████╗                          \r\n"
				+ "                  ██╔══╝  ██║██║     ██╔══╝                          \r\n"
				+ "                  ██║     ██║███████╗███████╗                        \r\n"
				+ "                  ╚═╝     ╚═╝╚══════╝╚══════╝ \033[38;5;27mshad/isaac | 228025\033[38;5;44m\r\n"
				+ "                                                                     \r\n" 
				+ " ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗██╗██╗\r\n"
				+ " ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝██║██║\r\n"
				+ " ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ ██║██║\r\n"
				+ " ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  ╚═╝╚═╝\r\n"
				+ " ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   ██╗██╗\r\n"
				+ " ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   ╚═╝╚═╝\r\n"
				+ "                                                \033[38;5;27mconsole application\033[38;5;44m\r\n"
				+ "══════════════════════════════════════════════════════════════════════\033[0m\r\n");
		show.writeln("\033[?25l type /hlp for more info about command of this program.\n");
		
	}//end
	//--------------------------------------
	



	//--------------------------------------
	private static void help(){
		show.write
("| DESCRIPTION                                                                              \r\n" +
"    File Security System help you to protect your files for other user that no permission.  \r\n" +
"	you can lock or unlock your file with this program by encryption and decryption.         \r\n" +
"                                                                                            \r\n" +
"Usage: /secure >> [directory or file] || [commands (/hlp, /exit, /reset, /clear, /cd, /shf)]\r\n" +
"                                                                                            \r\n" +
"	[directory or files] - drog or type a path of file or directory to operate it.           \r\n" +
"                                                                                      		 \r\n" +
"		example:                                                                             \r\n" + 
"               /secure >> c:\\my directory                                                  \r\n" +
"                           (have contain a file to multiple encrypt or decrypt)             \r\n" +
"               /secure >> c:\\my directory\\isFile.ext                                      \r\n" +
"                           (ext stand for any extention for single encrypt or decrypt)      \r\n" +
"	                                                                                         \r\n" +
"        [commands]      - basic command to make it easy for user.                           \r\n" +
"	                                                                                         \r\n" +
"       command and example:                                                                 \r\n" +
"			                                                                                 \r\n" +
"          /hlp     - display help program.                                                      \r\n" +
"          /exit    - exit and clean the program.                                            \r\n" +
"          /reset   - reset screen with logo, if --off it just clear screen without a logo,  \r\n" +
"                     and --on that is oposite of --off                                      \r\n" +
"                  example: /secure >> /reset --off                                          \r\n" +
"                           /secure >> /reset --on                                           \r\n" +
"          /clear   - just clean/clear the screen without logo                               \r\n" +
"           /cd     - change current directory.                                              \r\n" +
"                  example: /secure >> /cd c:\\the directory\\                               \r\n\n");
	}
	//--------------------------------------
	


	//--------------------------------------
	private static String pathDesign(){
		String []sepa = currentDirectory.split("\\\\");
		String build = "";
		for(String s : sepa){
			if(s == null){
				break;
			}
			build += s;
			build += ("\033[38;5;165m\\\033[0m");
		}
		return build;
	}
	//----------------------------------------
	




	//----------------------------------------
	//exiting command
	static void exit() throws InterruptedException {
		progress = new ThreadProgress();//create thread for process
		progress.textProcess = "Exiting...";
		progress.start();//start progress
		Thread.sleep(2000);//useless, i add this for showing progress
		progress._stop();//end progress
	}//end
	//----------------------------------------
	



	//----------------------------------------
	//check File and fix
	public static String[] checkFileInfo(File file) throws InterruptedException {
		progress = new ThreadProgress();//create progress
		progress.start();//start progress
		int n = 0;//start 0
		int issue = 0;
		String []paths = new String[(int)file.length()+1024];//limit

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
	}//end
	//----------------------------------------
	




	//----------------------------------------
	//this function is file operation
	private static boolean fileOperate(String[] paths) throws IOException, InterruptedException {

		String []color = new String[] {"\033[0m","\033[38;5;0m\033[48;5;15m"};
		int n0 = 0,n1 = 0,n2 = 0,inp = 0, mode = 0;
		String tmp = "";
		boolean bool = true,
		canceling = false;
		
		while(true) {
			show.write
			("\n\t[1] "+color[n0]+"Lock File"+color[0]+"\r\n"
			+"\t[2] "+color[n1]+"UnLock File"+color[0]+"\r\n"
			+"\t[3] "+color[n2]+"Cancel"+color[0]+"\r\n"
			+"\n     -----------------------\r\n");
			if(bool) {
				if(canceling){
					Thread.sleep(100);
					show.writeln("canceling done!");
					return true;//clean breaking loop and function
				}
				show.write("\tnum : ");
				try {
					inp = Integer.parseInt(bufread.readLine().toCharArray()[0]+"");//get first char in string and convert it to integer
					
					if(inp == 49 || inp == 1) {
						n0 = 1;
						n1 = 0;
						n2 = 0;
						bool = !bool;
						mode = Cipher.ENCRYPT_MODE;
					}else if(inp == 50 || inp == 2) {
						n0 = 0;
						n1 = 1;
						n2 = 0;
						bool = !bool;
						mode = Cipher.DECRYPT_MODE;
					}else if(inp == 51 || inp == 3){
						n0 = 0;
						n1 = 0;
						n2 = 1;
						canceling = true;
					}
				}catch(Exception e) {
					//ignored
				}
				show.write("\r\033[7A");
			}else {
				bool = !bool;
				show.write("\n");
				
				while(bool) {
					show.write("  permission to contiue this opetation [Y/n]: ");
					try {
						tmp = bufread.readLine().toCharArray()[0]+"";
						
						if(tmp.equalsIgnoreCase("y")){
							bool = !bool;//kill 2 while's
						}else {
							show.write("\r\033[8A");
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
		for(String path: paths) {
			if(path == null) //if empty or null it will be break the foreach loop
				break;
			
			Thread.sleep(10);
			CryptoFile.fileSecurity(mode, path);
			
		}
		progress._stop();
		return true;
	}//end fileOpetion
	//----------------------------------------

}//end class