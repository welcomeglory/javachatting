package firstproject;

import java.util.Iterator;
import java.util.List;

public
interface UserService {
    // ȸ�������� ����Ѵ�.
    public void addUser(User user);
    // ȸ�������� �����Ѵ�. user.getEmail()�� �ش��ϴ� ȸ�������� �����Ѵ�.
    public boolean updateUser(User user);
    // ȸ�������� �����Ѵ�.
    public boolean deleteUser(String email);
    // ��� ȸ�������� ��ȯ�Ѵ�.
    public Iterator<User> getUsers();
    // �̸��Ͽ� �ش��ϴ� ȸ�������� ���� ��� 0���� ū ���� ��ȯ
    public boolean exists(String email);
}
