package lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

class CharStream {
	//private final Reader reader;
	private Reader reader;
	private StringBuffer sb;
	private Character cache;
	private int index;
	
	static CharStream from(File file) throws FileNotFoundException {
		return new CharStream(new FileReader(file));
	}
	
	static CharStream from(StringBuffer sb) throws IOException {
		return new CharStream(sb);
	}
	
	CharStream(Reader reader) {
		this.reader = reader;
		this.cache = null;
	}
	
	CharStream(StringBuffer sb)
	{
		this.sb = sb;
		this.cache = null;
		index = 0;
	}
	
	Char nextChar() {
		if ( cache != null ) {
			char ch = cache;
			cache = null;
			
			return Char.of(ch);
		}
		else {
			//int ch = sb.read();
			int ch = sb.charAt(index);
			index ++;
			if ( ch == -1 ) {
				return Char.end();
			}
			else {
				return Char.of((char)ch);
			}
		}
	}
	
	void pushBack(char ch) {
		cache = ch;
	}
}
