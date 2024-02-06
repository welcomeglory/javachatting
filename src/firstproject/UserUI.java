package firstproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class UserUI {
    private BufferedReader br;
    public UserUI() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public int menu() {
        System.out.println("1. ȸ�����");
        System.out.println("2. ȸ����Ϻ���");
        System.out.println("3. ȸ������ �����ϱ�");
        System.out.println("4. ȸ������ ����");
        System.out.println("5. ����");
        int menuId = -1;
        try {
            String line = br.readLine();
            menuId = Integer.parseInt(line); // ���ڿ��� ������ ��ȯ
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return menuId;
    }

    public String inputEmail() {
        System.out.println("������ ȸ���� email�� �Է��ϼ���.");
        String email = "";
        try {
            email = br.readLine();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return email;
    }

    // �̸��Ͽ� �ش��ϴ� ���� ������ ����
    public User inputUser(String email) {
        try {
            System.out.println(email + "ȸ���� ������ �����մϴ�.");
            System.out.println("�̸��� �Է��ϼ���.");
            String name = br.readLine();
            System.out.println("������ �Է��ϼ���.");
            String strBirthYear = br.readLine();
            int birthYear = Integer.parseInt(strBirthYear);

            User user = new User(email, name, birthYear);
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public User regUser() {
        try {
            System.out.println("email�� �Է��ϼ���.");
            String email = br.readLine();
            System.out.println("�̸��� �Է��ϼ���.");
            String name = br.readLine();
            System.out.println("������ �Է��ϼ���.");
            String strBirthYear = br.readLine();
            int birthYear = Integer.parseInt(strBirthYear);

            User user = new User(email, name, birthYear);
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
            //nn
        }
    }

    public void printUserList1(Iterator<User> iter) {
        System.out.println("email       �̸�      ����");
        System.out.println("========================");
        while (iter.hasNext()) {
            User user = iter.next();
            System.out.print(user.getEmail());
            System.out.print("        ");
            System.out.print(user.getName());
            System.out.print("        ");
            System.out.print(user.getBirthYear());
            System.out.println();
        }
    }
}