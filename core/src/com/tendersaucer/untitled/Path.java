package com.tendersaucer.untitled;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;

public class Path implements IUpdate {

	protected float speed;
	protected float currSegmentX;
	protected float currSegmentY;
	protected int currSegmentIdx = 0;
	protected ArrayList<Segment> segments = new ArrayList<Segment>();
	
	public Path(float speed) {
		this.speed = speed;
	}
	
	public Path(float speed, List<Segment> segments) {
		this(speed);
		
		this.segments.addAll(segments);
		currSegmentX = segments.get(0).x1;
		currSegmentY = segments.get(0).y1;
	}
	
	public Path(float speed, float x1, float y1, float x2, float y2) {
		this(speed);
		
		segments.add(new Segment(speed, x1, y1, x2, y2));
		currSegmentX = x1;
		currSegmentY = y1;
	}
	
	@Override
	public boolean update() {
		Segment currSegment = getCurrSegment();
		currSegmentX += currSegment.speedX;
		currSegmentY += currSegment.speedY;
		
		if((currSegment.speedX > 1 && currSegmentX > currSegment.x2) ||
		   (currSegment.speedX < 1 && currSegmentX < currSegment.x2) ||
		   (currSegment.speedY > 1 && currSegmentY > currSegment.y2) ||
		   (currSegment.speedY < 1 && currSegmentY < currSegment.y2)) {
			currSegmentIdx = (currSegmentIdx + 1) % segments.size();
		}
		
		return false;
	}
	
	@Override
	public void done() {		
	}
	
	public void addSegment(Segment segment) {
		segments.add(segment);
	}
	
	public float getSpeedX() {
		return getCurrSegment().speedX;
	}
	
	public float getSpeedY() {
		return getCurrSegment().speedY;
	}
	
	protected Segment getCurrSegment() {
		return segments.get(currSegmentIdx);
	}
	
	public class Segment {
		
		public float x1;
		public float y1;
		public float x2;
		public float y2;
		public float angle;
		public float speedX;
		public float speedY;
		
		public Segment(float speed, float x1, float y1, float x2, float y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;			
			angle = MathUtils.atan2(x2 - x1, y2 - y1);
			speedX = speed * MathUtils.cos(angle);
			speedY = speed * MathUtils.sin(angle);
		}
	}
}
