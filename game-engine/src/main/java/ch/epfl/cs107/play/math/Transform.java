package ch.epfl.cs107.play.math;

import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 * Represents an immutable 2D affine transformation.
 */
public final class Transform implements Serializable {
    
	private static final long serialVersionUID = 1;
    /** The identity transform **/
    public static final Transform I = new Transform(1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    
    /** X scale */
    public final float m00;
    
    /** X shear */
    public final float m01;
    
    /** X translation */
    public final float m02;
    
    /** Y shear */
    public final float m10;
    
    /** Y scale */
    public final float m11;
    
    /** Y translation */
    public final float m12;

    /**
     * Creates a new transform.
     * @param m00 (float): X scale
     * @param m01 (float): X shear
     * @param m02 (float): X translation
     * @param m10 (float): Y shear
     * @param m11 (float): Y scale
     * @param m12 (float): Y translate
     */
    public Transform(float m00, float m01, float m02, float m10, float m11, float m12) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
    }
    
    // TODO is rigid/scale/translation/...?
    
    /** @return (Vector): X-axis, not null */
    public Vector getX() {
        return new Vector(m00, m10);
    }
    
    /** @return (Vector): Y-axis, not null */
    public Vector getY() {
        return new Vector(m01, m11);
    }
    
    /** @return (Vector): translation vector, not null */
    public Vector getOrigin() {
        return new Vector(m02, m12);
    }
    
    /** @return (float): angle, in radians */
    public float getAngle() {
        return (float)Math.atan2(m01, m00);
    }
    
    /**
     * Transforms point.
     * @param x (float): abcissa
     * @param y (float): ordinate
     * @return (Vector): transformed point, not null
     */
    public Vector onPoint(float x, float y) {
        return new Vector(
            x * m00 + y * m01 + m02,
            x * m10 + y * m11 + m12
        );
    }
    
    /**
     * Transforms point.
     * @param p (Vector): point, not null
     * @return (Vector): transformed point, not null
     */
    public Vector onPoint(Vector p) {
        return onPoint(p.x, p.y);
    }
    
    /**
     * Transforms vector.
     * @param x (float): abcissa
     * @param y (float): ordinate
     * @return (Vector): transformed vector, not null
     */
    public Vector onVector(float x, float y) {
        return new Vector(
            x * m00 + y * m01,
            x * m10 + y * m11
        );
    }
    
    /**
     * Transforms vector.
     * @param v (Vector): point, not null
     * @return (Vector): transformed vector, not null
     */
    public Vector onVector(Vector v) {
        return onVector(v.x, v.y);
    }
    
    /**
     * Appends another transform (applied after this transform).
     * @param t (Transform): transform, not null
     * @return (Transform): extended transform, not null
     */
    public Transform transformed(Transform t) {
        return new Transform(
            t.m00 * m00 + t.m01 * m10, t.m00 * m01 + t.m01 * m11, t.m00 * m02 + t.m01 * m12 + t.m02,
            t.m10 * m00 + t.m11 * m10, t.m10 * m01 + t.m11 * m11, t.m10 * m02 + t.m11 * m12 + t.m12
        );
    }
    
    /**
     * Appends translation (applied after this transform).
     * @param dx (float): X translation
     * @param dy (float): Y translation
     * @return (Transform): extended transform, not null
     */
    public Transform translated(float dx, float dy) {
        return new Transform(
            m00, m01, m02 + dx,
            m10, m11, m12 + dy
        );
    }
    
    /**
     * Appends translation (applied after this transform).
     * @param d (Vector): translation, not null
     * @return (Transform): extended transform, not null
     */
    public Transform translated(Vector d) {
        return translated(d.x, d.y);
    }
    
    /**
     * Appends scale (applied after this transform).
     * @param sx (float) X scale
     * @param sy (float) Y scale
     * @return (Transform): extended transform, not null
     */
    public Transform scaled(float sx, float sy) {
        return new Transform(
            m00 * sx, m01 * sx, m02 * sx,
            m10 * sy, m11 * sy, m12 * sy
        );
    }
    
    /**
     * Appends scale (applied after this transform).
     * @param s (float): scale
     * @return (Transform): extended transform, not null
     */
    public Transform scaled(float s) {
        return scaled(s, s);
    }
    
    // TODO scale in specific direction, according to vector?
    // TODO scale using specific center of transformation?
    
    /**
     * Appends rotation around origin (applied after this transform).
     * @param a (float): angle, in radians
     * @return (Transform): extended transform, not null
     */
    public Transform rotated(float a) {
        float c = (float)Math.cos(a);
        float s = (float)Math.sin(a);
        return new Transform(
            c * m00 - s * m10, c * m01 - s * m11, c * m02 - s * m12,
            s * m00 + c * m10, s * m01 + c * m11, s * m02 + c * m12
        );
    }
    
    /**
     * Appends rotation around specified point (applied after this transform).
     * @param a (float): angle, in radians
     * @param center (Vector): rotation axis, not null
     * @return (Transform): extended transform, not null
     */
    public Transform rotated(float a, Vector center) {
        return
            translated(-center.x, -center.y).
            rotated(a).
            translated(center);
    }
    
    // TODO 90, 180, 270 degrees rotation?
    
    // TODO flip h/v, mirror
    
    /** @return (Transform): transform inverse, not null */
    public Transform inverted() {
        float det = 1.0f / (m00 * m11 - m01 * m10);
        float a = m11 * det;
        float b = -m01 * det;
        float c = -m10 * det;
        float d = m00 * det;
        return new Transform(
            a, b, -(a * m02 + b * m12),
            c, d, -(c * m02 + d * m12)
        );
    }

    @Override
    public int hashCode() {
        return
            Float.hashCode(m00) ^ Float.hashCode(m01) ^ Float.hashCode(m02) ^ 
            Float.hashCode(m10) ^ Float.hashCode(m11) ^ Float.hashCode(m12);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Transform))
            return false;
        Transform other = (Transform)object;
        return
            m00 == other.m00 && m01 == other.m01 && m02 == other.m02 &&
            m10 == other.m10 && m11 == other.m11 && m12 == other.m12;
    }
    
    @Override
    public String toString() {
        return String.format("[%f, %f, %f, %f, %f, %f]", m00, m01, m02, m10, m11, m12);
    }
    
    /** @return (AffineTransform): AWT affine transform equivalent, not null */
    public AffineTransform getAffineTransform() {
        return new AffineTransform(
            m00, m10,
            m01, m11,
            m02, m12 
        );
    }
    
}
