import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.time.LocalDate;
import java.util.Date;
import java.time.format.DateTimeFormatter;

class Data {
  //variables of Data object (fields from cryptocurrency price data output)
  ArrayList<LocalDate> time_period_start = new ArrayList<LocalDate>();
  ArrayList<LocalDate> time_period_end = new ArrayList<LocalDate>();
  ArrayList<LocalDate> time_open = new ArrayList<LocalDate>();
  ArrayList<LocalDate> time_close = new ArrayList<LocalDate>();
  float[] price_open;
  float[] price_high;
  float[] price_low;
  float[] price_close;
  float[] volume_traded;
  int[] trades_count; 

  Data(String path) throws Exception {
    Table df = loadTable(path, "header,csv"); //read in data
    //initialize object variables
    price_open = new float[df.getRowCount()];
    price_high = new float[df.getRowCount()];
    price_low = new float[df.getRowCount()];
    price_close = new float[df.getRowCount()];
    volume_traded = new float[df.getRowCount()];
    trades_count = new int[df.getRowCount()];  

    int i = 0; //dummy iterator for arrays
    for (TableRow row : df.rows()) {
      //assign each variable in each row to its appropriate variable array
      price_open[i] = row.getFloat("price_open");
      price_high[i] = row.getFloat("price_high");
      price_low[i] = row.getFloat("price_low");
      price_close[i] = row.getFloat("price_close");
      volume_traded[i] = row.getFloat("volume_traded");
      trades_count[i] = row.getInt("trades_count");    
      time_period_start.add(LocalDate.parse(row.getString(0).substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      //new SimpleDateFormat("yyyy-MM-dd").parse(row.getString(0).substring(0,10)));
      time_period_end.add(LocalDate.parse(row.getString(1).substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      time_open.add(LocalDate.parse(row.getString(2).substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      time_close.add(LocalDate.parse(row.getString(3).substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

      i++; //increment dummy iterator for variable arrays
    }
  }
}