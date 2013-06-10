import deadpixel.command.*;

// You cannot run a script directly like you would from a terminal.
 
// In order to execute a batch file or a script, you have to first run 
// the interpreter program and pass your script as a parameter

void setup() {
	
	// On Mac OSX or Linux
	
	String scriptPath = sketchPath("myScript.sh"); 
	
	Command script = new Command("/bin/sh " + scriptPath); 
	if ( script.run() == true ) {
		// peachy
		String[] output = script.getOutput(); 
		println(output); 	
	} 
	 
	// The Windows equivalent:
	// String scriptPath = sketchPath("myBatchFile.bat"); 	
	// Command script = new Command("cmd.exe /c " + scriptPath); 
	
}

void draw() {	
}