package lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


class ScanContext {
	private final CharStream input;
	private StringBuilder builder;
	
	ScanContext(File file) throws FileNotFoundException {
		this.input = CharStream.from(file);
		this.builder = new StringBuilder();
	}
	
	ScanContext(StringBuffer sb) throws IOException {
		this.input = CharStream.from(sb);
		this.builder = new StringBuilder();
	}
	
	CharStream getCharStream() {
		return input;
	}
	
	String getLexime() {
		String str = builder.toString();
		builder.setLength(0);
		return str;
	}
	
	void append(char ch) {
		builder.append(ch);
	}
}
