package thread.ext.safe.deadlockdp;

import collection.base.map.User;

public class PayCompany {
    private static class TransferThread extends Thread{
        private String name;
        private UserAccount from;
        private UserAccount to;
        private int amount;
        private ITransfer transfer;

        public TransferThread(String name, UserAccount from, UserAccount to, int amount, ITransfer transfer) {
            this.name = name;
            this.from = from;
            this.to = to;
            this.amount = amount;
            this.transfer = transfer;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            try{
                transfer.transfer(from,to,amount);
            }catch (Exception e){

            }
        }
    }

    public static void main(String[] args) {
        PayCompany payCompany = new PayCompany();
        UserAccount zhangsan = new UserAccount("张三",20000);
        UserAccount lisi = new UserAccount("李四",10000);
        ITransfer transfer = new TrasnferAccount();
        transfer = new Solve1();
        transfer = new Solve2();
        TransferThread t1 = new TransferThread("张三", zhangsan, lisi, 2000, transfer);
        TransferThread t2 = new TransferThread("李四", lisi, zhangsan, 4000, transfer);
        t1.start();
        t2.start();
    }
}
