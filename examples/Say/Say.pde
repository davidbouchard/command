import deadpixel.command.*;

void setup() {
  // This will only work on Mac OSX:
  // using the "say" command for some basic text to speech
  Command c = new Command("say pretty sweet");	
  c.run();	
}

void draw() {
}