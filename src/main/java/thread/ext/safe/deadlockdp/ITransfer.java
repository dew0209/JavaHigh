package thread.ext.safe.deadlockdp;

public interface ITransfer {
    public void transfer(UserAccount from,UserAccount to,int amount) throws Exception;
}
