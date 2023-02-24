package my.bank;

public class Account {
    private  String cardId;
    private String userName;
    private String passWord;
    private double money;
    private  double quotamoney;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getQuotamoney() {
        return quotamoney;
    }

    public void setQuotamoney(double quotamoney) {
        this.quotamoney = quotamoney;
    }
}
