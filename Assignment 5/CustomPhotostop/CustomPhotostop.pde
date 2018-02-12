// Assignment 5: Custom Photoshop 
// Team Members: Hari Narayan Sanadhya, Ireti Fasere, Jack Nelson


PImage img01;
PImage img02;
String url = "";
String extension = "";
float[][] kernel;
String textAtBottom = "";
void setup() {
  background(200);
  size(600, 600);
  //rect(150, 50, 300, 30);
  state=START_SCREEN;
  kernel = new float[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
}

void draw() {
  if (state != previousState) {
    previousState=state;
    menu();
  } else if (state==IMAGE_DISPLAYED) {
    pixelEdit();
  } else if (state ==START_SCREEN)
    menu();
}

// function that will copy the content from the clipboard and display it on the screen
// clipboard must carry a valid url to a jpg or png image.
void mouseClicked() {
  textAtBottom = HELP_TEXT;
  if (state==IMAGE_FROM_WEB && mouseX>=15 && mouseX<=585 && mouseY>=50 && mouseY <=200) {
    fill(255);
    background(100);
    rect(15, 50, 570, 150);
    fill(0);
    text(TEXT2, 15, 20);
    url = GClip.paste(); 
    String clipBoardText = "", temp = "";
    temp=url;
    // After every 75 characters, insert newline character in the url pasted just for proper display
    while (temp.length()>75) {
      clipBoardText = clipBoardText.concat(temp.substring(0, 75)+ "\n");
      temp=temp.substring(75);
    }
    clipBoardText = clipBoardText.concat(temp);
    text(clipBoardText, 16, 65);
    // check to ensure that the url is link to a jpg or png image
    try {
      if (url!=null) {
        extension = url.substring(url.lastIndexOf(".")+1).toLowerCase();
        if (! (extension.equals("png") || extension.equals("jpg")))
          throw new Exception();
        state = IMAGE_DISPLAYED;
      } else {
        fill(100, 230, 230);
        text("Clipboard empty. Copy the desired URL link first", 150, 215);
      }
    }
    catch(Exception e) {
      fill(150, 0, 50);
      //println(url);
      text("Incorrect URL. URL should end with .png or .jpg", 150, 215);
    }
  }
}

// Function to alter the pixel values of the image as per the kernel
void pixelEdit() {
  image(img02, 0, 0);
  int w = img02.width;
  int h = img02.height; 
  fill(0);
  rect(0, h, w, 20);
  fill(255);
  text(textAtBottom, 15, h+14);
  int stroke = 10; // amount of pixels away from center point for window filtering original pixels (i.e. stroke 1 = 3x3 window) 

  int loc_x = windowEdgeFix(mouseX, w, stroke);
  int loc_y = windowEdgeFix(mouseY, h, stroke);

  img01.loadPixels();
  img02.loadPixels();

  // looping through each pixel in window that is to be updated
  for (int i = loc_y - stroke; i < loc_y+stroke; i++) {
    for (int j = loc_x-stroke; j < loc_x+stroke; j++) {
      int k = i*w - (w-j);
      color[][] window = getKernelWindow(w, k);
      img02.pixels[k] = getNewColor(window);
    }
  }
  img02.updatePixels();
}

void mouseMoved() {
  textAtBottom = HELP_TEXT;
}