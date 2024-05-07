package org.array.wrapper.d2.transforms;

import org.array.wrapper.d2.Array2d;
import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public abstract class Array2dTransformer<T> {

    protected Matrix3f mat;

    public Array2dTransformer() {
        mat = new Matrix3f();
        clearTransforms();
    }

    public void clearTransforms() {
        mat.identity();
    }

    public void translate(float ox, float oy) {
        Matrix3f translate = new Matrix3f();
        translate.identity();
        translate.m20 = ox;
        translate.m21 = oy;
        mat.mul(translate);
    }

    public void rotate(float theta) {
        mat.rotate((float) Math.toRadians(theta), new Vector3f(0, 0, 1));
    }

    public void scale(float sx, float sy) {
        mat.scale(sx, sy, 1.0f);
    }

    public void shear(float sx, float sy) {
        Matrix3f s = new Matrix3f();
        s.m00 = 1.0f; s.m10 = sx;   s.m20 = 0.0f;
        s.m01 = sy;   s.m11 = 1.0f; s.m21 = 0.0f;
        s.m02 = 0.0f; s.m12 = 0.0f;	s.m22 = 1.0f;
        mat.mul(s);
    }

    public float forwardX(float x, float y) {
        float outX = x * mat.m00 + y * mat.m10 + mat.m20;
        float z = x * mat.m02 + y * mat.m12 + mat.m22;
        if (z != 0) {
            outX /= z;
        }
        return outX;
    }

    public float forwardY(float x, float y) {
        float outY = x * mat.m01 + y * mat.m11 + mat.m21;
        float z = x * mat.m02 + y * mat.m12 + mat.m22;
        if (z != 0) {
            outY /= z;
        }
        return outY;
    }

    public Vector2f forward(float inX, float inY) {
        float x = forwardX(inX, inY);
        float y = forwardY(inX, inY);
        return new Vector2f(x, y);
    }

    public void forward(Vector2f v, float inX, float inY) {
        v.x = forwardX(inX, inY);
        v.y = forwardY(inX, inY);
    }

    public float backwardX(float x, float y) {
        float outX = x * mat.m00 + y * mat.m10 + mat.m20;
        float z = x * mat.m02 + y * mat.m12 + mat.m22;
        if (z != 0) {
            outX /= z;
        }
        return outX;
    }

    public float backwardY(float x, float y) {
        float outY = x * mat.m01 + y * mat.m11 + mat.m21;
        float z = x * mat.m02 + y * mat.m12 + mat.m22;
        if (z != 0) {
            outY /= z;
        }
        return outY;
    }

    public Vector2f backward(float inX, float inY) {
        float x = backwardX(inX, inY);
        float y = backwardY(inX, inY);
        return new Vector2f(x, y);
    }

    public void backward(Vector2f v, float inX, float inY) {
        v.x = backwardX(inX, inY);
        v.y = backwardY(inX, inY);
    }

    public void transform(Array2d<T> in, Array2d<T> out) {
        Vector2f p = forward(0.0f, 0.0f);
        Vector2f s = new Vector2f(p); // forward(0.0f, 0.0f);
        Vector2f e = new Vector2f(p); // forward(0.0f, 0.0f);

        forward(p, (float) in.getWidth(), (float) in.getHeight());
        s.x = Math.min(s.x, p.x); s.y = Math.min(s.y, p.y);
        e.x = Math.max(e.x, p.x); e.y = Math.max(e.y, p.y);

        forward(p,0.0f, (float) in.getHeight());
        s.x = Math.min(s.x, p.x); s.y = Math.min(s.y, p.y);
        e.x = Math.max(e.x, p.x); e.y = Math.max(e.y, p.y);

        forward(p, (float) in.getWidth(), 0.0f);
        s.x = Math.min(s.x, p.x); s.y = Math.min(s.y, p.y);
        e.x = Math.max(e.x, p.x); e.y = Math.max(e.y, p.y);

        mat.invert();

        if (e.x < s.x) {
            float temp = e.x;
            e.x = s.x;
            s.x = temp;
        }

        if (e.y < s.y) {
            float temp = e.y;
            e.y = s.y;
            s.y = temp;
        }

        s.x = Math.max(0.0f, s.x);
        s.y = Math.max(0.0f, s.y);
        e.x = Math.min(out.getWidth(), e.x);
        e.y = Math.min(out.getHeight(), e.y);

        Array2d<T> result = instanceNewArray((int) (e.x), (int) (e.y));
        for (float i = s.x; i < e.x; i++) {
            for (float j = s.y; j < e.y; j++) {

                Vector2f output = backward(i, j);
                if ( output.x >= 0 && output.x < in.getWidth() && output.y >= 0 && output.y < in.getHeight() ) {

                    int finalX = (int) (output.x + 0.5f);
                    int finalY = (int) (output.y + 0.5f);
                    if ( finalX < 0 ) {
                        finalX = 0;
                    }
                    if ( finalY < 0 ) {
                        finalY = 0;
                    }
                    if ( finalX >= in.getWidth() ) {
                        finalX = in.getWidth() - 1;
                    }
                    if ( finalY >= in.getHeight() ) {
                        finalY = in.getHeight() - 1;
                    }

                    T v = in.getValue(finalX, finalY);

                    int index = ((int) (i) + (int) (j) * out.getWidth());
                    if ( index < 0 ){
                        index = 0;
                    }
                    if ( index >= result.size() ){
                        index = result.size() - 1;
                    }
                    out.set((int) (i + 0.5f), (int) (j + 0.5f), v);

                }

            }
        }
    }

    protected abstract Array2d<T> instanceNewArray(final int width, final int height);

    // Getters

    public Matrix3f getMat() {
        return mat;
    }

    public void setMat(Matrix3f mat) {
        this.mat = mat;
    }

}