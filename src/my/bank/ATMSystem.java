package my.bank;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.SortedMap;

public class ATMSystem {
    public static void main(String[] args) {
        ArrayList<Account> accounts=new ArrayList<>();
        Scanner sc =new Scanner(System.in);

        while (true) {
            System.out.println("=========ATM SYSTEM============");
            System.out.println("1.帳戶登陸");
            System.out.println("2.帳戶開戶");
            System.out.println("請選擇操作");
            String command =sc.next();


            switch (command){
                case "1":
                    //用戶登陸
                    login(accounts,sc);
                    break;
                case "2":
                    //帳戶開戶
                    register(accounts,sc);
                    break;
                default:
                    System.out.println("輸入的命令不存在");
            }
        }


    }

    /**
     * 登陸功能
     * @param accounts 全部帳戶對象集合
     * @param sc 掃描器
     */
    private static void login(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("==========登入===========");
        if(accounts.size()==0){
            System.out.println("系統無帳戶資料，請先建立帳戶");
            return;
        }

        while (true) {
            System.out.println("請輸入卡號：");
            String carId=sc.next();
            Account acc= getAccountByCardId(carId,accounts);
            if(acc!=null){

                while (true) {
                    System.out.println("請輸入登入密碼");
                    String password=sc.next();
                    if(acc.getPassWord().equals(password)){
                        System.out.println(acc.getUserName()+"登入成功，您的卡號是"+acc.getCardId());

                        showUsercommand(acc,sc,accounts);
                        return;
                    }else{
                        System.out.println("密碼輸入錯誤");
                    }
                }

//                break;
            }else{
                System.out.println("卡號輸入錯誤");
            }
        }
    }

    /**
     * 展示用戶登入畫面
     * @param acc 帳戶訊息
     * @param sc 掃描器
     */
    private static void showUsercommand(Account acc, Scanner sc,ArrayList<Account> accounts) {


        while (true) {
            System.out.println("========用戶操作頁=========");
            System.out.println("1.查詢帳戶");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.轉帳");
            System.out.println("5.修改密碼");
            System.out.println("6.退出");
            System.out.println("7.註銷帳戶");
            System.out.println("請選擇");
            String command=sc.next();
            switch (command){
                case "1":
                    showaccount(acc);
                    break;
                case "2":
                    depositMoney(acc,sc);
                    break;
                case "3":
                    drawMoney(acc,sc);
                    break;
                case "4":
                    tarnsfermoney(acc,accounts,sc);
                    break;
                case "5":
                    updatepassword(acc,sc);
                    return;
                case "6":
                    System.out.println("退出成功，歡迎下次光臨");
                    return;
                case "7":
                    if(deleteacc(accounts,acc,sc)){
                        return;
                    }else {
                        break;
                    }

                default:
                    System.out.println("輸入錯誤");
            }
        }

    }

    /**
     * 刪除帳戶
     * @param accounts 帳戶集合
     * @param acc 當前帳戶
     * @param sc 掃描器
     */
    private static boolean deleteacc(ArrayList<Account> accounts,Account acc, Scanner sc) {
        System.out.println("======刪除帳戶=======");
        System.out.println("您確認刪除帳戶(y/n)");
        String choice=sc.next();
        switch (choice){
            case "y":
                if (acc.getMoney()>0){
                    System.out.println("帳戶內還有錢沒有取出");
                }else {
                    accounts.remove(acc);
                    System.out.println("帳戶已刪除");
                    return true;
                }
                break;
            default:
                System.out.println("取消刪除帳戶");
        }
        return  false;
    }

    /**
     * 修改密碼
     * @param acc 當前帳戶
     * @param sc 掃描器
     */
    private static void updatepassword(Account acc, Scanner sc) {
        System.out.println("========修改密碼=========");
        while (true) {
            System.out.println("請輸入當前密碼");
            String password=sc.next();
            if(acc.getPassWord().equals(password)){
                while (true) {
                    System.out.println("請輸入修改的密碼");
                    String newpassword=sc.next();
                    System.out.println("請確認輸入修改的密碼");
                    String checkpassword=sc.next();
                    if(newpassword.equals(checkpassword)){
                        acc.setPassWord(newpassword);
                        System.out.println("密碼修改完成");
                        return;
                    }else {
                        System.out.println("兩次密碼輸入不一致");
                    }
                }

            }else {
                System.out.println("密碼輸入錯誤");
            }
        }
    }

    /**
     * 轉帳功能
     * @param acc 當前帳戶
     * @param accounts 帳戶集合
     * @param sc 掃描器
     */
    private static void tarnsfermoney(Account acc, ArrayList<Account> accounts, Scanner sc) {
        System.out.println("=======轉帳======");
        if(accounts.size()<2){
            System.out.println("當前帳戶數量小於2，無法轉帳");
            return;
        }
        if(acc.getMoney()<0){
            System.out.println("帳戶金額小於100，無法轉帳");
            return;
        }
        while (true) {
            System.out.println("請輸入對方卡號");
            String cardId=sc.next();
            if(cardId.equals(acc.getCardId())){
                System.out.println("不能給自己轉帳");
                continue;
            }
            Account otheracc =getAccountByCardId(cardId,accounts);
            if(otheracc==null){
                System.out.println("您輸入的卡號不存在");
            }else{
                String othername=otheracc.getUserName();
                String tip="*"+othername.substring(1);
                System.out.println("請輸入轉帳對象["+tip+"]姓氏");
                String firstName=sc.next();
                if(othername.startsWith(firstName)){
                    while (true) {
                        System.out.println("請輸入轉帳金額");
                        Double money=sc.nextDouble();
                        if(money>acc.getMoney()){
                            System.out.println("您的餘額不足，最多可以轉帳"+acc.getMoney());
                        }else{
                            acc.setMoney(acc.getMoney()-money);
                            otheracc.setMoney(otheracc.getMoney()+money);
                            System.out.println("轉帳成功");
                            showaccount(acc);
                            return;
                        }
                    }
                }else{
                    System.out.println("輸入姓氏錯誤");
                }
            }
        }
    }

    /**
     * 取款功能
     * @param acc 帳戶
     * @param sc 掃描器
     */
    private static void drawMoney(Account acc, Scanner sc) {
        System.out.println("======取款======");

        if(acc.getMoney()<100){
            System.out.println("帳戶金額低於100，不能取錢");
            return;
        }

        while (true) {
            System.out.println("請輸入要取款的金額");
            double money=sc.nextDouble();
            if(money>acc.getQuotamoney()){
                System.out.println("取款金額超過提款限額，每次最多限額為"+acc.getQuotamoney());
            }else{
                if(money>acc.getMoney()){
                    System.out.println("取款金額超過帳戶餘額："+acc.getMoney());
                }else{
                    acc.setMoney(acc.getMoney()-money);
                    System.out.println("取款"+money+"成功");
                    showaccount(acc);
                    return;
                }

            }
        }

    }

    /**
     * 存款功能
     * @param acc 帳戶
     * @param sc 掃描器
     */
    private static void depositMoney(Account acc, Scanner sc) {
        System.out.println("======存款======");
        System.out.println("請輸入要存款的金額");
        double deposotMoney=sc.nextDouble();
        acc.setMoney(acc.getMoney()+deposotMoney);
        System.out.println("存款成功");
        showaccount(acc);
    }

    /**
     * 顯示帳戶信息
     * @param acc 帳戶
     */
    private static void showaccount(Account acc) {
        System.out.println("===========當前帳戶信息如下=============");
        System.out.println("卡號："+acc.getCardId());
        System.out.println("姓名："+acc.getUserName());
        System.out.println("餘額："+acc.getMoney());
        System.out.println("限額："+acc.getQuotamoney());
    }

    /**
     * 用戶開戶功能實現
     * @param accounts 接收的帳戶集合
     */
    private static void register(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("==========開戶============");
        Account account=new Account();

        System.out.println("請輸入用戶名稱");
        String userName=sc.next();
        account.setUserName(userName);

        while (true) {
            System.out.println("請輸入帳戶密碼");
            String password=sc.next();
            System.out.println("請再次輸入帳戶密碼");
            String checkPassword=sc.next();
            if(password.equals(checkPassword)){
                account.setPassWord(password);

                break;
            }else {
                System.out.println("兩次密碼輸入不一致，請重新確認");
            }
        }

        System.out.println("請輸入當次限額");
        double quotamoney=sc.nextDouble();
        account.setQuotamoney(quotamoney);

        String cardId=getrandomCardId(accounts);
        account.setCardId(cardId);

        accounts.add(account);
        System.out.println("帳戶"+userName+"創建成功，卡號為"+cardId);

    }

    /**
     * 生成隨機卡號
     * @return 隨機卡號
     */
    private static String getrandomCardId(ArrayList<Account> accounts){
        Random rd=new Random();
        while (true) {
            String carId="";
            for (int i = 0; i <8 ; i++) {
                carId+=rd.nextInt(10);
            }
            Account acc= getAccountByCardId(carId,accounts);
            if(acc==null){
                return  carId;
            }
        }
    }

    /**
     * 根據卡號回傳帳戶對象
     * @param carId 卡號
     * @param accounts 帳戶集合
     * @return 帳戶對象
     */
    private static Account getAccountByCardId(String carId,ArrayList<Account> accounts) {
        for (int i = 0; i <accounts.size() ; i++) {
            Account acc=accounts.get(i);
            if(acc.getCardId().equals(carId)){
                return  acc;
            }
        }
        return  null;
    }
}
