package com.htms;

import java.io.File;

public class App {

	public static void main(String[] args) {
		
        File file = new File("D:\\AllWorks\\Works\\htmls\\htmls\\src\\com\\htms\\file.htms");
        HTMLSParser parser = new HTMLSParser(file);
        parser.parser();
        //parser.storeVariables();

	}

}
