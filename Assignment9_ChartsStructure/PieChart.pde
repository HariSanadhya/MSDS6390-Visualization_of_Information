class PieChart extends Chart{
  
  //FIELDS
  color[] pcColors = {#e6194b, #3cb44b, #ffe119, #0082c8, #f58231, 
                      #911eb4, #46f0f0, #f032e6, #d2f53c, #fabebe,
                      #008080, #e6beff, #aa6e28, #fffac8, #800000,
                      #aaffc3, #808000, #ffd8b1, #000080, #808080};
  
  //CSTRS
  PieChart(){
    super();
  }
  
  PieChart(Table data, String xLabelField, String yValueField, PVector loc, PVector scl, color[] pcColors){
    super(data, xLabelField, yValueField, loc, scl);
    this.pcColors = pcColors;
  } 
  
  //METHODS
  void display(){
    println("This will show a summary PieChart of the data");
  }
}
