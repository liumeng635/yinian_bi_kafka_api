package com.yinian.bury.util;

import java.util.UUID;

public class UUIDUtil {
	public static String get32UUIDStr() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
