public final class PercentConstraints {
    private final float x;
    private final float y;
    private final float w;
    private final float h;

    public PercentConstraints(float x, float y, float w, float h) {
        this.x = Math.max(Math.min(x, w), 0);
        this.y = Math.max(Math.min(y, h), 0);
        this.w = w;
        this.h = h;
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

//    @Override
//    public boolean equals(Object obj) {
//        if (obj == this) return true;
//        if (obj == null || obj.getClass() != this.getClass()) return false;
//        var that = (PercentConstraints) obj;
//        return Float.floatToIntBits(this.x) == Float.floatToIntBits(that.x) &&
//                Float.floatToIntBits(this.y) == Float.floatToIntBits(that.y) &&
//                Float.floatToIntBits(this.w) == Float.floatToIntBits(that.w) &&
//                Float.floatToIntBits(this.h) == Float.floatToIntBits(that.h);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(x, y, w, h);
//    }
//
//    @Override
//    public String toString() {
//        return "PercentConstraints[" +
//                "x=" + x + ", " +
//                "y=" + y + ", " +
//                "w=" + w + ", " +
//                "h=" + h + ']';
//    }


}
