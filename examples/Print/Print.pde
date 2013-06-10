import deadpixel.command.*;
import processing.pdf.*;

// This is a modified version of an example from the PDF library.
// When done drawing, the PDF is created and sent to a printer
// using the "lp" command (Mac OSX only) 

void setup() {
  size(400, 400);
  beginRecord(PDF, "everything.pdf");
  background(255);
}

void draw() {
  // Be sure not to call background, otherwise your file
  // will just accumulate lots of mess, only to become invisible   
  // Draw something good here
  line(mouseX, mouseY, width/2, height/2);
}

void keyPressed() {
  if (key == 'q') {
    endRecord();
    
    // we need an absolute path to our PDF file  
    String fullpath = sketchPath("everything.pdf");
    
    // This should be the name of your printer
    // You can find out what that is by using the "lpstat -a" command
    // in a terminal 
    String yourPrinter = "Samsung_ML_2160_Series"; 
    
    Command c = new Command("lp -o media=letter -o fitplot -d " + yourPrinter + " " + fullpath);
    c.run();
    
    exit();
  }
}