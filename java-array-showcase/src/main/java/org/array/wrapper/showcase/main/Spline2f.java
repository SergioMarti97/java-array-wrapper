package org.array.wrapper.showcase.main;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spline2f {

    protected List<Vector2f> p;

    public Spline2f(Vector2f... p) {
        this.p = new ArrayList<>(Arrays.asList(p));
    }
    
    public Vector2f getSplinePoint(float t, boolean isLoop) {
        int p0, p1, p2, p3;

        if (!isLoop) {
            p1 = (int)t + 1;
            p2 = p1 + 1;
            p3 = p2 + 1;
            p0 = p1 - 1;
        } else {
            p1 = (int) t;
            p2 = (p1 + 1) % p.size();
            p3 = (p2 + 1) % p.size();
            p0 = p1 > 1 ? p1 - 1 : p.size() - 1;
        }

        t -= (int)t;

        float tt = t * t;
        float ttt = tt * t;

        float q1 = -ttt + 2.0f * tt - t;
        float q2 = 3.0f * ttt - 5.0f * tt + 2.0f;
        float q3 = -3.0f * ttt + 4.0f * tt + t;
        float q4 = ttt - tt;

        float tx = this.p.get(p0).x * q1 + this.p.get(p1).x * q2 + this.p.get(p2).x * q3 + this.p.get(p3).x * q4;
        float ty = this.p.get(p0).y * q1 + this.p.get(p1).y * q2 + this.p.get(p2).y * q3 + this.p.get(p3).y * q4;

        tx *= 0.5f;
        ty *= 0.5f;

        return new Vector2f(tx, ty);
    }

    public List<Vector2f> getP() {
        return p;
    }

    public void setP(List<Vector2f> p) {
        this.p = p;
    }

}
