package ch.epfl.cs107.play.math.shape;

import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.random.RandomGenerator;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a polygon, without self-intersection.
 */
public final class Polygon extends Shape {

	private final List<Vector> points;
	private List<Vector[]> components;
    private float[] areas;
    private float area;
    private float perimeter;

	/**
	 * Creates a new polygon.
	 * @param points (List of Vector): sequence of vertices, not null
	 */
	public Polygon(List<Vector> points) {
		this.points = new ArrayList<>(points);
		initialize();
	}

	/**
	 * Creates a new polygon.
	 * @param points (Array of points): sequence of vertices, not null
	 */
	public Polygon(Vector... points) {
		this(Arrays.asList(points));
	}

	/**
	 * Creates a new polygon.
	 * @param points (Array of float): sequence of vertices (x1, y1, x2, y2, etc.), not null
	 */
	public Polygon(float... points) {
		if (points.length % 2 != 0)
			throw new IllegalArgumentException("An even number of coordinates is expected");
		this.points = new ArrayList<>(points.length / 2);
		for (int i = 0; i < points.length; i += 2)
			this.points.add(new Vector(points[i], points[i + 1]));
		initialize();
	}

	// Generate convex decomposition
	private void initialize() {

		// TODO use better convex partitioning, i.e. split in convex polygons instead of only triangles

		// Check validity
		if (points.size() < 3)
			throw new IllegalArgumentException("At least three points are required");

		// Prepare buffers
		Vector[] vertices = new Vector[points.size()];
		vertices = points.toArray(vertices);
		int count = vertices.length;
		components = new ArrayList<>();

		// Make sure vertices are counter-clockwise
        area = area(vertices);
		if (area < 0.0f) {
			reverse(vertices);
            area = -area;
        }
        perimeter = perimeter(vertices);

        // Handle trivial case
		if (vertices.length == 3) {
			components.add(vertices);
            areas = new float[] {area};
			return;
		}

		// Triangulate using ear clipping
		while (count > 2) {

			// Find the ear with largest signed area
			float bestA = 0;// Float.NEGATIVE_INFINITY;
			int bestI = 0;
			for (int i = count - 2, j = count - 1, k = 0; k < count; ++k) {
				float a = area(vertices[i], vertices[j], vertices[k]);
				if (a > bestA) {
					boolean empty = true;
					for (int p = 0; p < count; ++p)
						if (p != i && p != j && p != k && area(vertices[k], vertices[i], vertices[p]) >= 0.0f
								&& area(vertices[i], vertices[j], vertices[p]) >= 0.0f
								&& area(vertices[j], vertices[k], vertices[p]) >= 0.0f) {
							empty = false;
							break;
						}
					if (empty) {
						bestA = a;
						bestI = i;
						break;
					}
				}
				i = j;
				j = k;
			}

			// Save concave part
			Vector[] ear = new Vector[3];
			for (int i = 0; i < 3; ++i)
				ear[i] = vertices[(bestI + i) % count];
			components.add(ear);

			// Remove vertex
			{
				int i = (bestI + 2) % count;
				if (i > 0)
					for (; i < count; ++i)
						vertices[i - 1] = vertices[i];
			}
			--count;
		}
        
        // Compute triangles area, used for sampling
        areas = new float[components.size()];
        for (int i = 0; i < components.size(); ++i)
            areas[i] = area(components.get(i));
	}

	// Computes signed area of polygon, positive if counter-clockwise.
	private static float area(Vector[] vertices) {
		float sum = 0.0f;
		int i = vertices.length - 1;
		for (int j = 0; j < vertices.length; ++j) {
			Vector a = vertices[i];
			Vector b = vertices[j];
			sum += a.x * b.y - a.y * b.x;
			i = j;
		}
		return 0.5f * sum;
	}

	// Reverse vertices order, in-place
	private static void reverse(Vector[] vertices) {
		int i = 0;
		int j = vertices.length - 1;
		Vector t;
		while (i < j) {
			t = vertices[i];
			vertices[i] = vertices[j];
			vertices[j] = t;
			++i;
			--j;
		}
	}

	// Return double signed area
	private static float area(Vector a, Vector b, Vector c) {
		float abx = b.x - a.x;
		float aby = b.y - a.y;
		float acx = c.x - a.x;
		float acy = c.y - a.y;
		return abx * acy - aby * acx;
	}
    
    // Return perimeter
    private static float perimeter(Vector[] vertices) {
        float perimeter = 0.0f;
        Vector previous = vertices[vertices.length - 1];
        for (int i = 1; i < vertices.length; ++i) {
            Vector current = vertices[i];
            perimeter += current.sub(previous).getLength();
            previous = current;
        }
        return perimeter;
    }

	/** @return (List of Vector): unmodifiable vertex list, not null */
	public List<Vector> getPoints() {
		return Collections.unmodifiableList(points);
	}


	/// Polygon extends Shape

    @Override
    public float getArea() {
        return area;
    }

    @Override
    public float getPerimeter() {
        return perimeter;
    }

    @Override
    public Vector sample() {
        
        // Sample uniform score, proportional to area
        float offset = RandomGenerator.getInstance().nextFloat() * area;
        
        // Find associated component (assumed to be a triangle)
        int index = 0;
        while (offset > areas[index]) {
            offset -= areas[index];
            ++index;
        }
        
        // Sample uniformly on quadrilateral, then cut in half
        float u = RandomGenerator.getInstance().nextFloat();
        float v = RandomGenerator.getInstance().nextFloat();
        if (u + v >= 1.0f) {
            u = 1.0f - u;
            v = 1.0f - v;
        }
        
        // Compute actual location
        Vector[] triangle = components.get(index);
        Vector a = triangle[0];
        Vector b = triangle[1];
        Vector c = triangle[2];
        return new Vector(
            a.x + (b.x - a.x) * u + (c.x - a.x) * v,
            a.y + (b.y - a.y) * u + (c.y - a.y) * v
        );
    }

    @Override
    public Path2D toPath() {
        // TODO is it possible to cache this? need to check if SwingWindow modifies it...
        Path2D path = new Path2D.Float();
		Vector point = points.get(0);
		path.moveTo(point.x, point.y);
		for (int i = 1; i < points.size(); ++i) {
			point = points.get(i);
			path.lineTo(point.x, point.y);
		}
		path.closePath();
		return path;
    }
    
}
