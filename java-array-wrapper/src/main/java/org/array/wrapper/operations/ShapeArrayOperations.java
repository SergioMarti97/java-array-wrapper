package org.array.wrapper.operations;

import org.array.wrapper.Array2d;
import org.array.wrapper.transforms.Array2dTransformer;
import org.joml.Vector2f;

import java.util.List;

public class ShapeArrayOperations<T, ArrayType extends Array2d<T>> {

    protected ArrayType array;

    public ShapeArrayOperations(ArrayType array) {
        this.array = array;
    }

    protected void set(final int x, final int y, T v) {
        array.set(x, y, v);
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
