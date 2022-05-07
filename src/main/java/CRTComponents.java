import java.math.BigInteger;

public class CRTComponents {

    private BigInteger dp;
    private BigInteger dq;
    private BigInteger qInverse;

    public CRTComponents(BigInteger dp, BigInteger dq, BigInteger qInverse) {
        this.dp = dp;
        this.dq = dq;
        this.qInverse = qInverse;
    }

    public BigInteger getDp() {
        return dp;
    }

    public BigInteger getDq() {
        return dq;
    }

    public BigInteger getqInverse() {
        return qInverse;
    }


}
