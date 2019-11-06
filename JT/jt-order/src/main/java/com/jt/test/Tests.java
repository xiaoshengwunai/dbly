package com.jt.test;

import java.util.UUID;

import org.junit.Test;

public class Tests {

	@Test
	public void tests() {
		long randomUUID = UUID.randomUUID().toString().hashCode();
		System.out.println(randomUUID);
	}
}
