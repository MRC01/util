# util
Android common utilities and helper classes

Getting Android project resources, reading & writing data from/to files, playing sounds (short or long), etc.

NOTE: you must build this using JDK 1.6 so that it will be compatible with Android.
If you don't, it will fail with obscure misleading error messages that don't tell you why.
Once the JAR is build with JDK 1.6 you can use it with any Android project by adding it to its libaries.

The dependent Android project that uses this class must initialize Util.APP_DIR before using it.
APP_DIR is the directory where this app will store its resources.
This is a subdirectory of the system's external storage directory.
Just the subdirectory name; Util will automatically find the system external storage directory and put it there.

For example the dependent Android project using Util can do this:
	static {
		Util.APP_DIR = "com.x.y.z";
	}
