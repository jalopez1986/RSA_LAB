import java.math.BigInteger;
import java.security.SecureRandom;


public class ClassicRSA {
    private final static SecureRandom random = new SecureRandom();
    private final static BigInteger one = new BigInteger("1");


    private BigInteger n;

    private BigInteger d;
    private BigInteger p;
    private BigInteger q;
    private BigInteger e;

    public ClassicRSA(BigInteger p, BigInteger q, BigInteger e){
        this.p = p;
        this.q = q;
        this.e = e; //this.e = new BigInteger("65537");

        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));
        this.n = p.multiply(q);
        this.d = this.e.modInverse(phi);

        System.out.println("Public key: n: " + n.toString() + " e: " + e.toString());
        System.out.println("Private key: n: " + n.toString() + " d: " + d.toString());
    }

    public BigInteger encrypt(BigInteger message){
        return message.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger encrypted){
        return encrypted.modPow(d, n);
    }

    public BigInteger encryptByCRTP(BigInteger message, CRTComponents crtComponents){
        BigInteger m1 = message.modPow(crtComponents.getDp(),p);
        BigInteger m2 = message.modPow(crtComponents.getDq(),q);
        BigInteger h = crtComponents.getqInverse().multiply(m1.subtract(m2)).mod(p);
        return m2.add(h.multiply(q));
    }

    public BigInteger decryptByCRT(BigInteger message, BigInteger decryptionExponent){

        BigInteger dpe = decryptionExponent.mod(p.subtract(BigInteger.ONE));
        BigInteger dqe = decryptionExponent.mod(q.subtract(BigInteger.ONE));
        BigInteger qInverse = inverse(q,p);

        BigInteger m1 = message.modPow(dpe,p);
        BigInteger m2 = message.modPow(dqe,q);
        BigInteger h = qInverse.multiply(m1.subtract(m2)).mod(p);
        return m2.add(h.multiply(q));
    }





    public BigInteger getN(){
        return this.n;
    }

    public String getE(){
        return this.e.toString() + this.n.toString();
    }

    @Override
    public String toString(){
        return 	"Public:\t" + this.e +
                "Private:\t" + this.d +
                "Modulus:\t" + this.n;
    }

    public CRTComponents generateCRTComponents() {
        BigInteger dp = d.mod(p.subtract(BigInteger.ONE));
        BigInteger dq = d.mod(q.subtract(BigInteger.ONE));
        BigInteger qInverse = inverse(q, p);

        return new CRTComponents(dp, dq, qInverse);
    }

    //calculate multiplicative inverse of a%n using the extended euclidean GCD algorithm
    public static BigInteger inverse (BigInteger a, BigInteger N){
        BigInteger [] ans = extendedEuclid(a,N);

        if (ans[1].compareTo(BigInteger.ZERO) == 1)
            return ans[1];
        else return ans[1].add(N);
    }

    //Calculate d = gcd(a,N) = ax+yN
    public static BigInteger [] extendedEuclid (BigInteger a, BigInteger N){
        BigInteger [] ans = new BigInteger[3];
        BigInteger ax, yN;

        if (N.equals(BigInteger.ZERO)) {
            ans[0] = a;
            ans[1] = BigInteger.ONE;
            ans[2] = BigInteger.ZERO;
            return ans;
        }

        ans = extendedEuclid (N, a.mod (N));
        ax = ans[1];
        yN = ans[2];
        ans[1] = yN;
        BigInteger temp = a.divide(N);
        temp = yN.multiply(temp);
        ans[2] = ax.subtract(temp);
        return ans;
    }
}
