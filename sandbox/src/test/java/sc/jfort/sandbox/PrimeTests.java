package sc.jfort.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PrimeTests {

    @Test(enabled = false)
    public void testPrime() {
        Assert.assertTrue(Primes.isPrimeFor(Integer.MAX_VALUE));
    }

    @Test
    public void testNonPrime() {
        Assert.assertFalse(Primes.isPrimeFor(Integer.MAX_VALUE - 2));
    }

    @Test
    public void testPrimeFast() {
        Assert.assertTrue(Primes.isPrimeForFast(Integer.MAX_VALUE));
    }

    @Test(enabled = false)
    public void testPrimeLong() {
        long n = Integer.MAX_VALUE;
        Assert.assertTrue(Primes.isPrimeFor(n));
    }
}
