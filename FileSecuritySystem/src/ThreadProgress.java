public class ThreadProgress extends Thread{
		
    public int lenght = 50;
    public int speed = 20;
    public String textProcess = "loading...",
                  textStop = "done!";
    private boolean exit = false;
    
    private static FunctionShort show = new FunctionShort();
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