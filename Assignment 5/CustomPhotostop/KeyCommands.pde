// 3x3 kernels taken from http://matlabtricks.com/post-5/3x3-convolution-kernels-with-online-demo

void keyPressed() {
  textAtBottom = HELP_TEXT;
  //press spacebar to reset image and kernel
  if (Character.toLowerCase(key)== ' ') {
    img02 = loadImage(url, "jpg");
    kernel = new float[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
  }
  //press b key to switch to blur kernel
  else if (Character.toLowerCase(key)== 'b') {
    kernel = new float[][]{{1.0/9, 1.0/9, 1.0/9}, {1.0/9, 1.0/9, 1.0/9}, {1.0/9, 1.0/9, 1.0/9}};
  }
  //press e key to switch to edge detection kernel
  else if (Character.toLowerCase(key)== 'e') {
    kernel = new float[][]{{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}};
  }
  //press s key to switch to sharpen kernel
  else if (Character.toLowerCase(key)== 's') {
    kernel = new float[][]{{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
  }
  //press m key to switch to emboss kernel
  else if (Character.toLowerCase(key)== 'm') {
    kernel = new float[][]{{-2, -1, 0}, {-1, 1, 1}, {0, 1, 2}};
  }
  //press w to switch to horizontal edge detection kernel (left of 'e')
  else if (Character.toLowerCase(key)== 'w') {
    kernel = new float[][]{{0, 0, 0}, {-1, 2, -1}, {0, 0, 0}};
  }
  //press r to switch to vertical edge detection kernel (right of 'e')
  else if (Character.toLowerCase(key)== 'r') {
    kernel = new float[][]{{0, -1, 0}, {0, 2, 0}, {0, -1, 0}};
  }
  //press f to switch to horizontal gradient kernel (left of 'g')
  else if (Character.toLowerCase(key)== 'f') {
    kernel = new float[][]{{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}};
  }
  //press h to switch to vertical gradient kernel (right of 'g')
  else if (Character.toLowerCase(key)== 'h') {
    kernel = new float[][]{{-1, 0, 1}, {-1, 0, 1}, {-1, 0, 1}};
  }
  //press a to switch to smoothed horizontal gradient kernel (left of 's')
  else if (Character.toLowerCase(key)== 'a') {
    kernel = new float[][]{{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
  } 
  //press d to switch to smoothed vertical gradient kernel (right of 's')
  else if (Character.toLowerCase(key)== 'd') {
    kernel = new float[][]{{1, 0, -1}, {2, 0, -2}, {1, 0, -1}};
  }      
  //press v to display the help on keys used for kernel selection 
  else if (Character.toLowerCase(key) == 'v') {
    showMessageDialog(null, TEXT_KEY_HELP, "Help on keys used in the Sektch", INFORMATION_MESSAGE );
  }   
  //press 0 to return to the main menu
  else if (key == '0') {
    state=START_SCREEN;
    previousState = -1;
  }
  // press k to keep the image (save)
  else if (Character.toLowerCase(key)=='k') {
    surface.setSize(width, height-20);
    saveFrame("CustomImage-######.jpg");
    surface.setSize(width, height+20);
    textAtBottom = "Frame Saved. " + HELP_TEXT;
  }
  //press 1 to load the image from the local system
  else if (key == '1' && state ==START_SCREEN) {
    state=IMAGE_FROM_LOCAL_SYSTEM;
  }
  //press 2 to load the image from web
  else if (key == '2' && state ==START_SCREEN) {
    state=IMAGE_FROM_WEB;
  }
}