package main;

public final class PercentConstraints {
    public static final PercentConstraints FULL = new PercentConstraints(0,0,1,1);

    private final float x;
    private final float y;
    private final float w;
    private final float h;

    public PercentConstraints(float x, float y, float w, float h) {
        this.x = clamp(x);
        this.y = clamp(y);
        this.w = clamp(w);
        this.h = clamp(h);
    }

    private float clamp(float v) {
        return Math.max(Math.min(v, 1f), 0f);
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float w() {
        return w;
    }

    public float h() {
        return h;
    }

    @Override
    public String toString() {
        return "PercentConstraints{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                '}';
    }
}
