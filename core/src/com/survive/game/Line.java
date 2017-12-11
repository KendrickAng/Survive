package com.survive.game;

public class Line {

	float x1;
	float y1;
	float x2;
	float y2;
	float a;
	float c;

	Line(float x1, float y1, float x2, float y2) {

		set(x1, y1, x2, y2);
	}

	void set(float x1, float y1, float x2, float y2) {

		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;

		a = -(y1 - y2)/(x1 - x2);
		c = a * x1 + y1;
	}

	boolean intersectCircle(float x, float y, float r) {

		this.translate(-x, -y);
		double discriminant = Math.pow(r, 2) * (Math.pow(a, 2) + 1) - Math.pow(c, 2);
		boolean o;

		if (discriminant >= 0) {

			double x1 = (a * c + Math.sqrt(discriminant)) / (Math.pow(a, 2) + 1);
			double y1 = (c - a * Math.sqrt(discriminant)) / (Math.pow(a, 2) + 1);
			double x2 = (a * c - Math.sqrt(discriminant)) / (Math.pow(a, 2) + 1);
			double y2 = (c + a * Math.sqrt(discriminant)) / (Math.pow(a, 2) + 1);

			o = ((this.x1 - x1 >= 0) == (x1 - this.x2 >= 0) && (this.y1 - y1 >= 0) == (y1 - this.y2 >= 0)) ||
				((this.x1 - x2 >= 0) == (x2 - this.x2 >= 0) && (this.y1 - y2 >= 0) == (y2 - this.y2 >= 0));

		} else {

			o = false;
		}

		this.translate(x, y);
		return o;
	}

	void translate(float x, float y) {

		float x1 = this.x1 + x;
		float y1 = this.y1 + y;
		float x2 = this.x2 + x;
		float y2 = this.y2 + y;
		set(x1, y1, x2, y2);
	}
}
