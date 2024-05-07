package org.array.wrapper.operations;

import org.array.wrapper.Array2d;
import org.array.wrapper.transforms.Array2dTransformer;
import org.joml.Vector2f;

import java.util.Arrays;
import java.util.List;

public class ShapeArray2dOperations<T, ArrayType extends Array2d<T>> {

    protected ArrayType array;

    public ShapeArray2dOperations(ArrayType array) {
        this.array = array;
    }

    // Getter & Setter for Array

    public ArrayType getArray() {
        return array;
    }

    public void setArray(ArrayType array) {
        this.array = array;
    }

    public int getWidth() {
        return array.getWidth();
    }

    public int getHeight() {
        return array.getHeight();
    }

    // Methods

    // Override this method if it is required (alpha processing for instance)
    protected void set(final int x, final int y, T v) {
        if (array.isIndexValid(x, y)) {
            array.set(x, y, v);
        }
    }

    public void fill(T v) {
        for (int i = 0; i < array.size(); i++) {
            array.set(i, v);
        }
    }

    public void line(int x1, int y1, int x2, int y2, T v) {
        int x, y, dx, dy, dx1, dy1, px, py, xe, ye;
        dx = x2 - x1; dy = y2 - y1;

        if (dx == 0) // Line is vertical
        {
            if (y2 < y1) {
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }
            for (y = y1; y <= y2; y++) {
                set(x1, y, v);
            }
            return;
        }

        if (dy == 0) // Line is horizontal
        {
            if (x2 < x1) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
            }
            for (x = x1; x <= x2; x++) {
                set(x, y1, v);
            }
            return;
        }

        // Line is Funk-aye
        dx1 = Math.abs(dx); dy1 = Math.abs(dy);
        px = 2 * dy1 - dx1;	py = 2 * dx1 - dy1;
        if (dy1 <= dx1)
        {
            if (dx >= 0)
            {
                x = x1; y = y1; xe = x2;
            }
            else
            {
                x = x2; y = y2; xe = x1;
            }

            set(x, y, v);

            while (x < xe) {
                x = x + 1;
                if (px<0)
                    px = px + 2 * dy1;
                else
                {
                    if ((dx<0 && dy<0) || (dx>0 && dy>0)) y = y + 1; else y = y - 1;
                    px = px + 2 * (dy1 - dx1);
                }
                set(x, y, v);
            }
        }
        else
        {
            if (dy >= 0)
            {
                x = x1; y = y1; ye = y2;
            }
            else
            {
                x = x2; y = y2; ye = y1;
            }

            set(x, y, v);

            while (y<ye) {
                y = y + 1;
                if (py <= 0)
                    py = py + 2 * dx1;
                else
                {
                    if ((dx<0 && dy<0) || (dx>0 && dy>0)) x = x + 1; else x = x - 1;
                    py = py + 2 * (dx1 - dy1);
                }
                set(x, y, v);
            }
        }
    }

    public void strokeRectangle(int offX, int offY, int width, int height, T v) {
        // Don't render code
        if ( offX < -width ) {
            return;
        }
        if ( offY < -height ) {
            return;
        }
        if ( offX >= array.getWidth() ) {
            return;
        }
        if ( offY >= array.getHeight() ) {
            return;
        }

        int newX = 0;
        int newY = 0;
        int newWidth = width;
        int newHeight = height;

        // Clipping Code
        if ( offX < 0 ) {
            newX -= offX;
        }
        if ( offY < 0 ) {
            newY -= offY;
        }
        if ( newWidth + offX >= array.getWidth() ) {
            newWidth -= (newWidth + offX - array.getWidth());
        }
        if ( newHeight + offY >= array.getHeight() ) {
            newHeight -= (newHeight + offY - array.getHeight());
        }

        for ( int y = newY; y <= newHeight; y++ ) {
            set(offX, y + offY, v);
            set(offX + width, y + offY, v);
        }
        for ( int x = newX; x <= newWidth; x++ ) {
            set(x + offX, offY, v);
            set(x + offX, offY + height, v);
        }
    }

    public void fillRectangle(int offX, int offY, int width, int height, T v) {
        // Don't render code
        if ( offX < -width ) {
            return;
        }
        if ( offY < -height ) {
            return;
        }
        if ( offX >= array.getWidth() ) {
            return;
        }
        if ( offY >= array.getHeight() ) {
            return;
        }

        int newX = 0;
        int newY = 0;
        int newWidth = width;
        int newHeight = height;

        // Clipping Code
        if ( offX < 0 ) {
            newX -= offX;
        }
        if ( offY < 0 ) {
            newY -= offY;
        }
        if ( newWidth + offX >= array.getWidth() ) {
            newWidth -= (newWidth + offX - array.getWidth());
        }
        if ( newHeight + offY >= array.getHeight() ) {
            newHeight -= (newHeight + offY - array.getHeight());
        }

        for ( int y = newY; y < newHeight; y++ ) {
            for ( int x = newX; x < newWidth; x++ ) {
                set(x + offX, y + offY, v);
            }
        }
    }

    public void strokeRect(int x, int y, int w, int h, T v) {
        line(x, y, x + w, y, v);
        line(x + w, y, x + w, y + h, v);
        line(x + w, y + h, x, y + h, v);
        line(x, y + h, x, y, v);
    }

    public void fillRect(int x, int y, int w, int h, T v) {
        int x2 = x + w;
        int y2 = y + h;

        if (x < 0) x = 0;
        if (x >= array.getWidth()) x = array.getWidth();
        if (y < 0) y = 0;
        if (y >= array.getHeight()) y = array.getHeight();

        if (x2 < 0) x2 = 0;
        if (x2 >= array.getWidth()) x2 = array.getWidth();
        if (y2 < 0) y2 = 0;
        if (y2 >= array.getHeight()) y2 = array.getHeight();

        for (int i = x; i < x2; i++) {
            for (int j = y; j < y2; j++) {
                set(i, j, v);
            }
        }
    }

    public void strokeCircle(int x, int y, int radius, T v) {
        int x0 = 0;
        int y0 = radius;
        int d = 3 - 2 * radius;
        if ( radius == 0) {
            return;
        }

        while (y0 >= x0) // only formulate 1/8 of circle
        {
            set(x + x0, y - y0, v);
            set(x + y0, y - x0, v);
            set(x + y0, y + x0, v);
            set(x + x0, y + y0, v);
            set(x - x0, y + y0, v);
            set(x - y0, y + x0, v);
            set(x - y0, y - x0, v);
            set(x - x0, y - y0, v);
            if (d < 0) d += 4 * x0++ + 6;
            else d += 4 * (x0++ - y0--) + 10;
        }
    }

    private void lineForFillCircle(int sx, int ex, int ny, T v) {
        for (int i = sx; i <= ex; i++) {
            set(i, ny, v);
        }
    }

    public void fillCircle(int x, int y, int radius, T v) {
        int x0 = 0;
        int y0 = radius;
        int d = 3 - 2 * radius;
        if ( radius == 0 ) {
            return;
        }

        while (y0 >= x0) {
            // Modified to draw scan-lines instead of edges
            lineForFillCircle(x - x0, x + x0, y - y0, v);
            lineForFillCircle(x - y0, x + y0, y - x0, v);
            lineForFillCircle(x - x0, x + x0, y + y0, v);
            lineForFillCircle(x - y0, x + y0, y + x0, v);
            if (d < 0) d += 4 * x0++ + 6;
            else d += 4 * (x0++ - y0--) + 10;
        }
    }

    public void strokeTriangle(int x1, int y1, int x2, int y2, int x3, int y3, T v) {
        line(x1, y1, x2, y2, v);
        line(x2, y2, x3, y3, v);
        line(x3, y3, x1, y1, v);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, T v) {
        if (y2 < y1) {
            int tempInteger = y1;
            y1 = y2;
            y2 = tempInteger;

            tempInteger = x1;
            x1 = x2;
            x2 = tempInteger;
        }

        if (y3 < y1) {
            int tempInteger = y1;
            y1 = y3;
            y3 = tempInteger;

            tempInteger = x1;
            x1 = x3;
            x3 = tempInteger;
        }

        if (y3 < y2) {
            int tempInteger = y2;
            y2 = y3;
            y3 = tempInteger;

            tempInteger = x2;
            x2 = x3;
            x3 = tempInteger;
        }

        int dy1 = y2 - y1;
        int dx1 = x2 - x1;

        int dy2 = y3 - y1;
        int dx2 = x3 - x1;

        float dax_step = 0, dbx_step = 0;

        if ( dy1 != 0 ) {
            dax_step = dx1 / (float)Math.abs(dy1);
        }
        if ( dy2 != 0 ) {
            dbx_step = dx2 / (float)Math.abs(dy2);
        }

        if ( dy1 != 0 ) {
            for ( int i = y1; i <= y2; i++ )
            {
                int ax = (int)(x1 + (float)(i - y1) * dax_step);
                int bx = (int)(x1 + (float)(i - y1) * dbx_step);

                if ( ax > bx ) {
                    int tempInteger = ax;
                    ax = bx;
                    bx = tempInteger;
                }

                for (int j = ax; j < bx; j++) {
                    int index = i * array.getWidth() + j;
                    if (index < array.size()) {
                        set(j, i, v);
                    }
                }
            }
        }

        dy1 = y3 - y2;
        dx1 = x3 - x2;

        if ( dy1 != 0 ) {
            dax_step = dx1 / (float)Math.abs(dy1);
        }
        if ( dy2 != 0 ) {
            dbx_step = dx2 / (float)Math.abs(dy2);
        }

        if ( dy1 != 0 )
        {
            for (int i = y2; i <= y3; i++)
            {
                int ax = (int)(x2 + (float)(i - y2) * dax_step);
                int bx = (int)(x1 + (float)(i - y1) * dbx_step);

                if (ax > bx) {
                    int tempInteger = ax;
                    ax = bx;
                    bx = tempInteger;
                }

                for (int j = ax; j < bx; j++) {
                    int index = i * array.getWidth() + j;
                    if (index < array.size()) {
                        set(j, i, v);
                    }
                }
            }
        }
    }

    protected void setPixelForTexturedTriangle(int y, int x, float tex_u, float tex_v, float tex_w, ArrayType texture) {
        tex_w = tex_w == 0.0F ? 1.0F : tex_w;
        T color = texture.getSample(tex_u / tex_w, tex_v / tex_w);

        try {
            this.set(x, y, color);
        } catch (ArrayIndexOutOfBoundsException var10) {
            String errorMessage = "X: " + x + " Y: " + y + " outside of " + array.getWidth() + "x" + array.getHeight();
            System.out.println("Set pixel Error: " + errorMessage + var10.getMessage());
        }

    }

    public void fillTexturedTriangle(
            int x1, int y1, float u1, float v1, float w1,
            int x2, int y2, float u2, float v2, float w2,
            int x3, int y3, float u3, float v3, float w3,
            ArrayType texture) {
        int dy1;
        float tempFloat;
        if (y2 < y1) {
            dy1 = y1;
            y1 = y2;
            y2 = dy1;
            dy1 = x1;
            x1 = x2;
            x2 = dy1;
            tempFloat = u1;
            u1 = u2;
            u2 = tempFloat;
            tempFloat = v1;
            v1 = v2;
            v2 = tempFloat;
            tempFloat = w1;
            w1 = w2;
            w2 = tempFloat;
        }

        if (y3 < y1) {
            dy1 = y1;
            y1 = y3;
            y3 = dy1;
            dy1 = x1;
            x1 = x3;
            x3 = dy1;
            tempFloat = u1;
            u1 = u3;
            u3 = tempFloat;
            tempFloat = v1;
            v1 = v3;
            v3 = tempFloat;
            tempFloat = w1;
            w1 = w3;
            w3 = tempFloat;
        }

        if (y3 < y2) {
            dy1 = y2;
            y2 = y3;
            y3 = dy1;
            dy1 = x2;
            x2 = x3;
            x3 = dy1;
            tempFloat = u2;
            u2 = u3;
            u3 = tempFloat;
            tempFloat = v2;
            v2 = v3;
            v3 = tempFloat;
            tempFloat = w2;
            w2 = w3;
            w3 = tempFloat;
        }

        dy1 = y2 - y1;
        int dx1 = x2 - x1;
        float dv1 = v2 - v1;
        float du1 = u2 - u1;
        float dw1 = w2 - w1;
        int dy2 = y3 - y1;
        int dx2 = x3 - x1;
        float dv2 = v3 - v1;
        float du2 = u3 - u1;
        float dw2 = w3 - w1;
        float dax_step = 0.0F;
        float dbx_step = 0.0F;
        float du1_step = 0.0F;
        float dv1_step = 0.0F;
        float du2_step = 0.0F;
        float dv2_step = 0.0F;
        float dw1_step = 0.0F;
        float dw2_step = 0.0F;
        if (dy1 != 0) {
            dax_step = (float)dx1 / (float)Math.abs(dy1);
        }

        if (dy2 != 0) {
            dbx_step = (float)dx2 / (float)Math.abs(dy2);
        }

        if (dy1 != 0) {
            du1_step = du1 / (float)Math.abs(dy1);
        }

        if (dy1 != 0) {
            dv1_step = dv1 / (float)Math.abs(dy1);
        }

        if (dy1 != 0) {
            dw1_step = dw1 / (float)Math.abs(dy1);
        }

        if (dy2 != 0) {
            du2_step = du2 / (float)Math.abs(dy2);
        }

        if (dy2 != 0) {
            dv2_step = dv2 / (float)Math.abs(dy2);
        }

        if (dy2 != 0) {
            dw2_step = dw2 / (float)Math.abs(dy2);
        }

        float tex_u;
        float tex_v;
        float tex_w;
        int i;
        int ax;
        int bx;
        float tex_su;
        float tex_sv;
        float tex_sw;
        float tex_eu;
        float tex_ev;
        float tex_ew;
        int tempInteger;
        float t;
        int j;
        float tstep;
        if (dy1 != 0) {
            for(i = y1; i <= y2; ++i) {
                ax = (int)((float)x1 + (float)(i - y1) * dax_step);
                bx = (int)((float)x1 + (float)(i - y1) * dbx_step);
                tex_su = u1 + (float)(i - y1) * du1_step;
                tex_sv = v1 + (float)(i - y1) * dv1_step;
                tex_sw = w1 + (float)(i - y1) * dw1_step;
                tex_eu = u1 + (float)(i - y1) * du2_step;
                tex_ev = v1 + (float)(i - y1) * dv2_step;
                tex_ew = w1 + (float)(i - y1) * dw2_step;
                if (ax > bx) {
                    tempInteger = ax;
                    ax = bx;
                    bx = tempInteger;
                    t = tex_su;
                    tex_su = tex_eu;
                    tex_eu = t;
                    t = tex_sv;
                    tex_sv = tex_ev;
                    tex_ev = t;
                    t = tex_sw;
                    tex_sw = tex_ew;
                    tex_ew = t;
                }

                tstep = 1.0F / (float)(bx - ax);
                t = 0.0F;

                for(j = ax; j < bx; ++j) {
                    tex_u = (1.0F - t) * tex_su + t * tex_eu;
                    tex_v = (1.0F - t) * tex_sv + t * tex_ev;
                    tex_w = (1.0F - t) * tex_sw + t * tex_ew;
                    this.setPixelForTexturedTriangle(i, j, tex_u, tex_v, tex_w, texture);
                    t += tstep;
                }
            }
        }

        dy1 = y3 - y2;
        dx1 = x3 - x2;
        dv1 = v3 - v2;
        du1 = u3 - u2;
        dw1 = w3 - w2;
        if (dy1 != 0) {
            dax_step = (float)dx1 / (float)Math.abs(dy1);
        }

        if (dy2 != 0) {
            dbx_step = (float)dx2 / (float)Math.abs(dy2);
        }

        du1_step = 0.0F;
        dv1_step = 0.0F;
        if (dy1 != 0) {
            du1_step = du1 / (float)Math.abs(dy1);
        }

        if (dy1 != 0) {
            dv1_step = dv1 / (float)Math.abs(dy1);
        }

        if (dy1 != 0) {
            dw1_step = dw1 / (float)Math.abs(dy1);
        }

        if (dy1 != 0) {
            for(i = y2; i <= y3; ++i) {
                ax = (int)((float)x2 + (float)(i - y2) * dax_step);
                bx = (int)((float)x1 + (float)(i - y1) * dbx_step);
                tex_su = u2 + (float)(i - y2) * du1_step;
                tex_sv = v2 + (float)(i - y2) * dv1_step;
                tex_sw = w2 + (float)(i - y2) * dw1_step;
                tex_eu = u1 + (float)(i - y1) * du2_step;
                tex_ev = v1 + (float)(i - y1) * dv2_step;
                tex_ew = w1 + (float)(i - y1) * dw2_step;
                if (ax > bx) {
                    tempInteger = ax;
                    ax = bx;
                    bx = tempInteger;
                    t = tex_su;
                    tex_su = tex_eu;
                    tex_eu = t;
                    t = tex_sv;
                    tex_sv = tex_ev;
                    tex_ev = t;
                    t = tex_sw;
                    tex_sw = tex_ew;
                    tex_ew = t;
                }

                tstep = 1.0F / (float)(bx - ax);
                t = 0.0F;

                for(j = ax; j < bx; ++j) {
                    tex_u = (1.0F - t) * tex_su + t * tex_eu;
                    tex_v = (1.0F - t) * tex_sv + t * tex_ev;
                    tex_w = (1.0F - t) * tex_sw + t * tex_ew;
                    this.setPixelForTexturedTriangle(i, j, tex_u, tex_v, tex_w, texture);
                    t += tstep;
                }
            }
        }
    }
    
    public void fillTexturedParallelogram(
            int x1, int y1, float u1, float v1, float w1,
            int x2, int y2, float u2, float v2, float w2,
            int x3, int y3, float u3, float v3, float w3,
            int x4, int y4, float u4, float v4, float w4,
            ArrayType texture, T triangleStroke
    ) {
        fillTexturedTriangle(
                x1,  y1,  u1,  v1,  w1, 
                x2,  y2,  u2,  v2,  w2, 
                x3,  y3,  u3,  v3,  w3, 
                texture);
        fillTexturedTriangle(
                x1,  y1,  u1,  v1,  w1,
                x3,  y3,  u3,  v3,  w3,
                x4,  y4,  u4,  v4,  w4,
                texture);
        if (triangleStroke != null) {
            strokeTriangle(x1,  y1, x2,  y2, x3,  y3, triangleStroke);
            strokeTriangle(x1,  y1, x3,  y3, x4,  y4, triangleStroke);
        }
    }

    public void fillWarpedDecal(ArrayType texture, Vector2f[] pos, T triangleStroke) {
        float[] w = new float[4];
        Arrays.fill(w, 1.0f);
        Vector2f[] uv = new Vector2f[] {
                new Vector2f(0.0f, 0.0f),
                new Vector2f(0.0f, 1.0f),
                new Vector2f(1.0f, 1.0f),
                new Vector2f(1.0f, 0.0f)
        };
        Vector2f[] posdi = new Vector2f[] {
                new Vector2f(),
                new Vector2f(),
                new Vector2f(),
                new Vector2f()
        };
        Vector2f center = new Vector2f();

        float rd = (pos[2].x - pos[0].x) * (pos[3].y - pos[1].y) - (pos[3].x - pos[1].x) * (pos[2].y - pos[0].y);

        if (rd != 0) {
            rd = 1.0f / rd;

            float rn = ((pos[3].x - pos[1].x) * (pos[0].y - pos[1].y) - (pos[3].y - pos[1].y) * (pos[0].x - pos[1].x)) * rd;
            float sn = ((pos[2].x - pos[0].x) * (pos[0].y - pos[1].y) - (pos[2].y - pos[0].y) * (pos[0].x - pos[1].x)) * rd;

            if ( !(rn < 0.f || rn > 1.f || sn < 0.f || sn > 1.f) ) {
                //center = pos[0] + rn * (pos[2] - pos[0]);
                center.x = (pos[0].x + rn * (pos[2].x - pos[0].x));
                center.y = (pos[0].y + rn * (pos[2].y - pos[0].y));
            }

            float[] d = new float[4];
            for (int i = 0; i < 4; i++)	{
                //d[i] = (pos[i] - center).mag();
                // Para cada valor de d, se calcula la magnitud del vector resultante de pos[i] - centro
                float vecX = pos[i].x - center.x;
                float vecY = pos[i].y - center.y;
                d[i] = (float)Math.sqrt(vecX * vecX + vecY * vecY);
            }

            for (int i = 0; i < 4; i++) {
                //float up = d[i] + d[(i + 2) & 3];
                //float down = d[(i + 2) & 3];
                //down = (down == 0)? 1.0f : down;
                float q = (d[i] == 0.0f) ? 1.0f : (d[i] + d[(i + 2) & 3]) / (d[(i + 2) & 3]);

                //di.uv[i] *= q;
                uv[i].mul(q);

                //di.w[i] *= q;
                w[i] *= q;

                posdi[i].x = (pos[i].x);
                posdi[i].y = (pos[i].y);
            }
            //di.mode = nDecalMode;
            //vLayers[nTargetLayer].vecDecalInstance.push_back(di);

            fillTexturedParallelogram(
                    (int) posdi[0].x, (int) posdi[0].y, uv[0].x, uv[0].y, w[0],
                    (int) posdi[1].x, (int) posdi[1].y, uv[1].x, uv[1].y, w[1],
                    (int) posdi[2].x, (int) posdi[2].y, uv[2].x, uv[2].y, w[2],
                    (int) posdi[3].x, (int) posdi[3].y, uv[3].x, uv[3].y, w[3],
                    texture, triangleStroke);
        }
    }

    public void strokePointList(List<Vector2f> points, T v, boolean autoClose) {
        for ( int i = 0; i < points.size() - 1; i++ ) {
            line(
                    (int)points.get(i).x, (int)points.get(i).y,
                    (int)points.get(i + 1).x, (int)points.get(i + 1).y,
                    v);
        }
        if (autoClose) {
            line(
                    (int)points.get(points.size() - 1).x, (int)points.get(points.size() - 1).y,
                    (int)points.get(0).x, (int)points.get(0).y,
                    v);
        }
    }

    public void writeArray2d(ArrayType array, int offX, int offY) {
        if (array == null) {
            return;
        }

        // Don't render code
        if (offX < - this.array.getWidth()) {
            return;
        }
        if (offY < - this.array.getHeight()) {
            return;
        }
        if (offX >= this.array.getWidth()) {
            return;
        }
        if (offY >= this.array.getHeight()) {
            return;
        }

        int newX = 0;
        int newY = 0;
        int newWidth = array.getWidth();
        int newHeight = array.getHeight();

        // Clipping Code
        if (offX < 0) {
            newX -= offX;
        }
        if (offY < 0) {
            newY -= offY;
        }
        if (newWidth + offX >= this.array.getWidth()) {
            newWidth -= (newWidth + offX - this.array.getWidth());
        }
        if (newHeight + offY >= this.array.getHeight()) {
            newHeight -= (newHeight + offY - this.array.getHeight());
        }

        for (int y = newY; y < newHeight; y++) {
            for (int x = newX; x < newWidth; x++) {
                //p[(x + offX) + render.getW() * (y + offY)] = image.getPixel(x, y);
                // render.setPixel(x + offX, y + offY, image.getPixel(x, y));
                this.array.set(x + offX, y + offY, array.getValue(x, y));
            }
        }
    }

    public void writeArray2d(ArrayType array, Array2dTransformer<T> transformer) {
        transformer.transform(array, this.array);
    }

}
