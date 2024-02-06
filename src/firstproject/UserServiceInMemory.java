package firstproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// 메모리상에 User 정보를 관리하는 클래스
public class UserServiceInMemory implements UserService {
    private List<User> users; // User 정보를 저장하는 리스트

    // 기본 생성자: 새로운 ArrayList로 초기화
    public UserServiceInMemory() {
        this.users = new ArrayList<>();
    }

    // 리스트를 받는 생성자
    public UserServiceInMemory(List<User> users) {
        this.users = users;
    }

    // 사용자 추가 메서드
    @Override
    public void addUser(User user) {
        users.add(user); // 리스트에 사용자 추가
    }

    // 사용자 정보 업데이트 메서드
    @Override
    public boolean updateUser(User user) {
        // 이메일로 사용자 삭제 후 새 정보 추가
        boolean deleteFlag = deleteUser(user.getEmail()); // 삭제 성공 여부 확인
        if (deleteFlag) { // 삭제 성공 시
            users.add(user); // 새로운 정보 추가
            return true; // 업데이트 성공 반환
        } else {
            return false; // 삭제 실패 시 업데이트 실패 반환
        }
    }

    // 이메일로 사용자 존재 여부 확인 메서드
    @Override
    public boolean exists(String email) {
        return findIndex(email) >= 0; // 사용자 인덱스가 0 이상이면 존재함을 반환
    }

    // 이메일로 사용자 인덱스 찾기
    private int findIndex(String email) {
        int findIndex = -1; // 찾는 사용자 인덱스 초기값 설정
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) { // 이메일로 사용자 확인
                findIndex = i; // 해당 사용자 인덱스 할당
                break; // 반복문 종료
            }
        }
        return findIndex; // 사용자 인덱스 반환
    }

    // 사용자 삭제 메서드
    @Override
    public boolean deleteUser(String email) {
        int findIndex = findIndex(email); // 이메일에 해당하는 사용자 인덱스 찾기
        if (findIndex > -1) { // 사용자가 존재하는 경우
            users.remove(findIndex); // 사용자 삭제
            return true; // 삭제 성공 반환
        } else {
            return false; // 삭제 실패 반환
        }
    }

    // 사용자 리스트를 반복하는 이터레이터 반환 메서드
    @Override
    public Iterator<User> getUsers() {
        return users.iterator(); // 리스트의 이터레이터 반환
    }
}