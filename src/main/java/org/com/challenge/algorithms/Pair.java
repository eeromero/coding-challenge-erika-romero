package org.com.challenge.algorithms;

import com.google.common.base.Objects;

public class Pair {
    private int left;
    private int right;

    public Pair(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return left == pair.left &&
                right == pair.right;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(left, right);
    }
}
