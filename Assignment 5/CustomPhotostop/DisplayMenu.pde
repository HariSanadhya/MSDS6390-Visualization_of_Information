void menu() {
  // Main menu - displays the options to pick the source image from.
  if (state==START_SCREEN) {
    surface.setSize(600, 600);
    background(200);
    //rect(150, 50, 300, 30);
    fill(0);
    text(TEXT1, 15, 20);
    text(TEXT11, 15, 500);
  } 
  // if option chosen to load the image from the local file system
  else if (state==IMAGE_FROM_LOCAL_SYSTEM) {
    // Repeat the loop untill esc is pressed or till a png or jpg image is choosen
    while (true) {
      url = loadFile(new Frame(), "Select an image to open (.jpg or .png files only)", "", "");
      // URL will be null if esc is pressed on the frame which was browsing the local file system
      if (url != null) {
        //println(url); 
        extension = url.substring(url.lastIndexOf(".")+1);
        // only jpg and png files allowed
        if (extension.equals("jpg") ||extension.equals("png")) {
          // All validations passed, set state to display the image
          state=IMAGE_DISPLAYED;
          break;
        } else {
          // Invalid image extension
          showMessageDialog(null, ALLOWED_FILE_FORMATS, "Choose correct file format", ERROR_MESSAGE );
          continue;
        }
      } else {
        // esc is pressed on the local file browser window
        state=START_SCREEN;
        break;
      }
    }
  } 
  // If web image is to be loaded into the sketch
  else if (state==IMAGE_FROM_WEB) {
    surface.setSize(600, 600);
    background(200);
    fill(255);
    rect(15, 50, 570, 150);
    fill(0);
    text(TEXT2, 20, 15);
    text(TEXT11, 15, 500);
  } 
  // if image is ready to be displayed
  else if (state == IMAGE_DISPLAYED) {
    img01 = loadImage(url, extension); 
    img02 = loadImage(url, extension);
    int w = img02.width;
    int h = img02.height; 
    // Resize the sketch window
    surface.setSize(w, h+20);
    textAtBottom = HELP_TEXT;
    // load the kernel
    pixelEdit();
    //noLoop();
  }
}

// Function that opens the file browser window for the selection of file from local file system
// https://processing.org/discourse/beta/num_1140107049.html
String loadFile (Frame f, String title, String defDir, String fileType) {
  FileDialog fd = new FileDialog(f, title, FileDialog.LOAD);
  fd.setFile(fileType);
  fd.setDirectory(defDir);
  fd.setLocation(50, 50);
  fd.show();
  String path = (fd.getDirectory()==null)?null:fd.getDirectory()+fd.getFile();
  return path;
}