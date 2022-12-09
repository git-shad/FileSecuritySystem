public class FunctionShort extends ThreadProgress{

    

    public static class BuildCommandLine {
        private BuildCommandLine(){}//private default constractor
        private static String[] args = null;
        
        private static int size = 0;
        public int size(){
            return size;
        }
        public String arg(int index_Element) {
            return args[index_Element];
        }

        public boolean arg(int index_Element,String inLine,boolean caseIgnore){
            if(args[index_Element].equalsIgnoreCase(inLine) && caseIgnore == true){
                return true;
            }else if(args[index_Element].equals(inLine) && caseIgnore == false){
                return true;//if index is equal
            }
            return false;//if index is not equal
        }
        
        public boolean arg(int index_Element,String inLine){
            if(args[index_Element].equals(inLine)){
                return true;//if index is equal
            }
            return false;//if index is not equal
        }
        
        public static BuildCommandLine cl(String argOneLine){//Command Line
            args = argOneLine.split(" ");//split every space
            size = args.length;
            return new BuildCommandLine();
        }
    }
    

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
        try{
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        }catch(Exception e){
            //ignored
        }
    }
}