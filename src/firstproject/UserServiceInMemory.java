package firstproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// �޸𸮻� User ������ �����ϴ� Ŭ����
public class UserServiceInMemory implements UserService {
    private List<User> users; // User ������ �����ϴ� ����Ʈ

    // �⺻ ������: ���ο� ArrayList�� �ʱ�ȭ
    public UserServiceInMemory() {
        this.users = new ArrayList<>();
    }

    // ����Ʈ�� �޴� ������
    public UserServiceInMemory(List<User> users) {
        this.users = users;
    }

    // ����� �߰� �޼���
    @Override
    public void addUser(User user) {
        users.add(user); // ����Ʈ�� ����� �߰�
    }

    // ����� ���� ������Ʈ �޼���
    @Override
    public boolean updateUser(User user) {
        // �̸��Ϸ� ����� ���� �� �� ���� �߰�
        boolean deleteFlag = deleteUser(user.getEmail()); // ���� ���� ���� Ȯ��
        if (deleteFlag) { // ���� ���� ��
            users.add(user); // ���ο� ���� �߰�
            return true; // ������Ʈ ���� ��ȯ
        } else {
            return false; // ���� ���� �� ������Ʈ ���� ��ȯ
        }
    }

    // �̸��Ϸ� ����� ���� ���� Ȯ�� �޼���
    @Override
    public boolean exists(String email) {
        return findIndex(email) >= 0; // ����� �ε����� 0 �̻��̸� �������� ��ȯ
    }

    // �̸��Ϸ� ����� �ε��� ã��
    private int findIndex(String email) {
        int findIndex = -1; // ã�� ����� �ε��� �ʱⰪ ����
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) { // �̸��Ϸ� ����� Ȯ��
                findIndex = i; // �ش� ����� �ε��� �Ҵ�
                break; // �ݺ��� ����
            }
        }
        return findIndex; // ����� �ε��� ��ȯ
    }

    // ����� ���� �޼���
    @Override
    public boolean deleteUser(String email) {
        int findIndex = findIndex(email); // �̸��Ͽ� �ش��ϴ� ����� �ε��� ã��
        if (findIndex > -1) { // ����ڰ� �����ϴ� ���
            users.remove(findIndex); // ����� ����
            return true; // ���� ���� ��ȯ
        } else {
            return false; // ���� ���� ��ȯ
        }
    }

    // ����� ����Ʈ�� �ݺ��ϴ� ���ͷ����� ��ȯ �޼���
    @Override
    public Iterator<User> getUsers() {
        return users.iterator(); // ����Ʈ�� ���ͷ����� ��ȯ
    }
}