package com.survive.game;

public class Circle {

	float x;
	float y;
	float radius;

	Circle(float x, float y, float radius) {

		this.set(x, y, radius);
	}

	void set(float x, float y, float radius) {

		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	boolean intersectCircle(Circle circle) {

		double distance = Math.sqrt(Math.pow(x - circle.x, 2) + Math.pow(y - circle.y, 2));
		return distance < radius + circle.radius;
	}
}
