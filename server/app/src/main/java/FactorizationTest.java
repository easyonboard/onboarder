//import org.junit.Assert;
//import org.junit.Test;
//
//public class FactorizationTest {
//
//    private static Factorization factorization = new Factorization();
//
//    @Test
//    public void whenPassTwoReturnTwo() {
//
//        Assert.assertEquals( "2*", factorization.factorize(2));
//    }
//
//
//    @Test
//    public void whenPassThreeReturnThree() {
//
//        Assert.assertEquals( "3*", factorization.factorize(3));
//    }
//
//    @Test
//    public void whenPassFourReturnTwoTimesTwo() {
//
//        Assert.assertEquals( "2*2*", factorization.factorize(4));
//    }
//
//    @Test
//    public void whenPassFiveReturnFive() {
//
//        Assert.assertEquals( "5*", factorization.factorize(5));
//    }
//
//    @Test
//    public void factorizeSix() {
//
//        Assert.assertEquals( "2*3*", factorization.factorize(6));
//    }
//
//    @Test
//    public void factorizeSixtyFour() {
//
//        Assert.assertEquals( "2^6", factorization.factorize(64));
//    }
//
//  @Test
//    public void factorizeEightyFour() {
//
//        Assert.assertEquals( "2^2*3^1*7^1;", factorization.factorize(84));
//    }
//    @Test
//    public void factorize378() {
//
//        Assert.assertEquals( "2*3*3*3*7", factorization.factorize(378));
//    }
//
//    @Test
//    public void factorize510510() {
//
//        Assert.assertEquals( "2*3*5*7*11*13*17", factorization.factorize(510510));
//    }
//    @Test
//    public void factorize10383661() {
//
//        Assert.assertEquals( "149*227*307", factorization.factorize(10383661));
//    }
//
//    @Test
//    public void testIsPrime(){
//
//       Factorization factorization2=new Factorization();
//        Assert.assertTrue(factorization2.isPrime(2));
//        Assert.assertTrue(factorization2.isPrime(3));
//        Assert.assertFalse(factorization2.isPrime(4));
//        Assert.assertTrue(factorization2.isPrime(5));
//        Assert.assertTrue(factorization2.isPrime(2));
//
//    }
//
//
//}
