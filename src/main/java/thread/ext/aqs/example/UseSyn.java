package thread.ext.aqs.example;

public class UseSyn implements GoodsService{
    private GoodsInfo goodsInfo;

    public UseSyn(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public synchronized GoodsInfo getNum() {
        try {
            Thread.currentThread().sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.goodsInfo;
    }

    @Override
    public synchronized void setNum(int number) {
        try {
            Thread.currentThread().sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        goodsInfo.changeNumber(number);
    }
}
