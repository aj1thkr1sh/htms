package com.htms;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class AllTags {

	public static void main(String[] args) throws MalformedURLException, IOException {
		String sourceUrlString="D:\\AllWorks\\Works\\htmls\\htmls\\src\\com\\htms\\file.htms";
		Source source=new Source(new URL("file:///D:/AllWorks/Works/htmls/htmls/src/com/htms/file.htms"));
		List<Element> elementList=source.getAllElements();
		for (Element element : elementList) {
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println(element.getDebugInfo());
			if (element.getAttributes()!=null) System.out.println("XHTML StartTag:\n"+element.getStartTag().tidy(true));
			System.out.println("Source text with content:\n"+element);
		}
		System.out.println(source.getCacheDebugInfo());
	}

}
