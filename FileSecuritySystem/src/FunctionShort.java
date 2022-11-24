public class FunctionShort extends ThreadProgress{
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