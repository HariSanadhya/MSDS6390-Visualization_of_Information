import static javax.swing.JOptionPane.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

// states
final int START_SCREEN = 0; // const 
final int IMAGE_FROM_LOCAL_SYSTEM        = 1;
final int IMAGE_FROM_WEB        = 2;
final int IMAGE_DISPLAYED        = 3;

int state = START_SCREEN; // current 
int previousState = -1;  // prev 
String id;

final String TEXT1 = "=== Assignment 5: Custom Photoshop ===\nTeam Members: Hari Narayan Sanadhya, Ireti Fasere, Jack Nelson\n\n"
  + "1: Select the image from local file system.\n"
  + "2: Use an image from website.\n"
  + "\n--- Press 1 or 2 ---";

final String TEXT11 = "Press 0 anytime to return to the main menu\nPress v anytime to view the custom photoshop key help";

final String TEXT2 = "=== Assignment 5: Custom Photoshop ===\n"
  + "Click on the white region below to paste the content of the clipboard to the sketch\n"
  + "The clipboard must contain the weblink of the image to be opened by processing";

final String TEXT_KEY_HELP = "=== CUSTOM PHOTOSHOP help menu ===" 
  + "press spacebar to reset image and kernel\n"
  + "press b key to switch to blur kernel\n"
  + "press e key to switch to edge detection kernel\n"
  + "press s key to switch to sharpen kernel\n"
  + "press m key to switch to emboss kernel\n"
  + "press w key to switch to horizontal edge detection kernel\n"
  + "press r key to switch to vertical edge detection kernel\n"
  + "press f key to switch to horizontal gradient kernel\n"
  + "press h key to switch to vertical gradient kernel\n"
  + "press a key to switch to smoothed horizontal gradient kernel\n"
  + "press d key to switch to smoothed vertical gradient kernel\n"
  + "press k key to switch to keep (save) the image.\n"
  + "press 0 key to return to the main menu\n"
  + "press v key to display the help menu";

final String ALLOWED_FILE_FORMATS = "Only png and jpg file formats allowed to be selected !!";

final String HELP_TEXT = "Press v to view help on the available options";