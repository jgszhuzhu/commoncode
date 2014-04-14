package com.wowotuan.common;

import java.util.Stack;

import android.app.Activity;
import android.util.Log;

public class ActivityManager {
	private static Stack<Activity> activities = new Stack<Activity>();
	private static Activity current;

	public static Activity getCurrent() {
		return current;
	}

	public static void pop() {
		if (!activities.isEmpty())
			current = activities.peek();
		else
			current = null;

	}

	public static void popall() {
		for (Activity activity : activities) {
			activity.finish();
		}
	}

	public static void push(Activity paramActivity) {
		if (current != paramActivity) {
			current = activities.push(paramActivity);
		}
	}

	public static int getSize() {
		return activities.size();
	}
}
