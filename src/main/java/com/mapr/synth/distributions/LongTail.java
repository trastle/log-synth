package com.mapr.synth.distributions;

import com.google.common.collect.Lists;
import org.apache.mahout.math.random.Sampler;

import java.util.List;

/**
 * Samples from a set of things based on a long-tailed distribution.  This converts
 * the ChineseRestaurant distribution from a distribution over integers into a distribution
 * over more plausible looking things like words.
 */
public abstract class LongTail<T> implements Sampler<T> {
    private ChineseRestaurant base;
    private List<T> things = Lists.newArrayList();

    protected LongTail(double alpha, double discount) {
        base = new ChineseRestaurant(alpha, discount);
    }

    public T sample() {
        int n = base.sample();
        while (n >= things.size()) {
            things.add(createThing());
        }
        return things.get(n);
    }

    public ChineseRestaurant getBaseDistribution() {
        return base;
    }

    protected abstract T createThing();

    public void setThing(int i, T thing) {
        // extend thing list to desired length
        // should almost always extend by exactly 1 object
        while (things.size() <= i) {
            // insert null place holders
            things.add(null);
        }
        things.set(i, thing);
    }

    public void setSeed(long seed) {
        base.setSeed(seed);
    }
}
