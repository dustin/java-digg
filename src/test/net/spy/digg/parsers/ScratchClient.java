package net.spy.digg.parsers;

import net.spy.digg.Digg;

public class ScratchClient {

	public static void main(String args[]) throws Exception {
		Digg d=new Digg("http://rubik.west.spy.net/hg/java/digg/");

		System.out.println(d.getTopics());
		System.out.println(d.getTopic("apple"));
		System.out.println(d.getTopic("Apple"));
	}
}
