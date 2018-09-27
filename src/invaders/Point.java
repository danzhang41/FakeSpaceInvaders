package invaders;

public class Point<N>{
	
	private N x, y;

	public Point(N x, N y){
		
		this.x = x;
		this.y = y;
	}
	public N getX(){
		
		return this.x;
	}
	public N getY(){
		
		return this.y;
	}
	public void setX(N x) {
		this.x = x;
	}
	public void setY(N y) {
		this.y = y;
	}
	public static Point <? extends Number> add(Point <? extends Number> a, Point <? extends Number> b){
		 	
		if(a.x instanceof Integer){
			
			int x = a.x.intValue() + b.x.intValue();
			int y = a.y.intValue() + b.y.intValue();
			
			return new Point<Integer>(x,y);
		}
		else if(a.x instanceof Double){
			
			double x = a.x.doubleValue() + b.x.doubleValue();
			double y = a.y.doubleValue() + b.y.doubleValue();
			
			return new Point<Double>(x,y);
		}
		else if(a.x instanceof Float){
			
			float x = a.x.floatValue() + b.x.floatValue();
			float y = a.y.floatValue() + b.y.floatValue();
			
			return new Point<Float>(x,y);
		}
		else if(a.x instanceof Long){
			
			long x = a.x.longValue() + b.x.longValue();
			long y = a.y.longValue() + b.y.longValue();
			
			return new Point<Long>(x,y);
		}
		else{
			return null;
		}
	
	}
	public static Point <? extends Number> subtract(Point <? extends Number> a, Point <? extends Number> b){
	 	
		if(a.x instanceof Integer){
			
			int x = a.x.intValue() - b.x.intValue();
			int y = a.y.intValue() - b.y.intValue();
			
			return new Point<Integer>(x,y);
		}
		else if(a.x instanceof Double){
			
			double x = a.x.doubleValue() - b.x.doubleValue();
			double y = a.y.doubleValue() - b.y.doubleValue();
			
			return new Point<Double>(x,y);
		}
		else if(a.x instanceof Float){
			
			float x = a.x.floatValue() - b.x.floatValue();
			float y = a.y.floatValue() - b.y.floatValue();
			
			return new Point<Float>(x,y);
		}
		else{
			
			long x = a.x.longValue() - b.x.longValue();
			long y = a.y.longValue() - b.y.longValue();
			
			return new Point<Long>(x,y);
		}
	
	}
	@Override public int hashCode(){
		
		if(x instanceof Integer){
			
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) x;
			result = prime * result + (int) y;
			return result;
		}
		else if(x instanceof Double){
			
			final int prime = 31;
			int result = 1;
			int hX = (int) Double.doubleToLongBits((double) x);
			int hY = (int) Double.doubleToLongBits((double) y);
			result = prime * result + hX;
			result = prime * result + hY;
			return (int) result;
		}
		else if(x instanceof Float){
			
			final int prime = 31;
			int result = 1;
			result = prime * result + Float.floatToIntBits((float) x);
			result = prime * result + Float.floatToIntBits((float) y);
			return (int) result;
		}
		else{
			
			long xCoord = (long) x;
			long yCoord = (long) y;
			final long prime= 31;
			long result = 1;
			result = prime * 1 + (int)(xCoord ^ (xCoord >>> 32));
			result = prime * 1 + (int)(yCoord ^ (yCoord >>> 32));
			return (int) result;
		}
		
	}
	@Override
	public boolean equals(Object obj){
		
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Point<?> other = (Point<?>) obj;
		if(x != other.x)
			return false;
		if(y != other.y)
			return false;
		return true;		
	}
	@Override
	public String toString(){
		
		return x + "," + y;
	}

}
