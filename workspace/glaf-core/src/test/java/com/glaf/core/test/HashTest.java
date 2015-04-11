package com.glaf.core.test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.glaf.core.util.UUID32;
import com.glaf.core.util.hash.JenkinsHash;

public class HashTest {

	public static void main(String[] args) throws IOException {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < 1024; i++) {
			int hash = JenkinsHash.getInstance().hash(
					UUID32.getUUID().getBytes());
			set.add(Math.abs(hash % 1024));
			System.out.println(Math.abs(hash % 1024));
		}
		System.out.println("size:" + set.size());
	}
}
