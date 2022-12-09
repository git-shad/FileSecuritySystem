public class ThreadProgress extends Thread{
		
    public int lenght = 50;
    public int speed = 20;
    public String textProcess = "loading...",
                  textStop = "done!";
    private boolean exit = false;
    private static FunctionShort show = new FunctionShort();
    static String saveAni = null;
    @Override
    public void run() {
        try {
            int tmp = this.lenght - 1;
            boolean showd = true;
            
            do {
                 //create progress string
                String open = textProcess + " [",
                       right = "",
                       ishow = "\033[38;5;165m■■\033[38;5;214m",
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
                saveAni = open;
                show.write(open + "\r\b");//reset and show
                
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
            }while(!exit);
            
        }catch(Exception e){
            //end running
        }
    }
    
    public void _stop() {
        try{
            Thread.sleep(5);
            this.exit = true;
            show.write(saveAni +"\033[0m  "+textStop+"\033[0m\n" );
        }catch(Exception e){
            //ignored
        }
        
    }
}