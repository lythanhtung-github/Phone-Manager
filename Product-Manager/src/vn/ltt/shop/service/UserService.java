package vn.ltt.shop.service;

import vn.ltt.shop.utils.CSVUtils;
import vn.ltt.shop.utils.TypeSort;
import vn.ltt.shop.model.Role;
import vn.ltt.shop.model.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {
    public final static String DATA_USER_PATH = "data\\users.csv";
    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        List<String> lines = CSVUtils.read(DATA_USER_PATH);
        for (String line : lines) {
            users.add(User.parseUser(line));
        }
        return users;
    }

    @Override
    public User login(String userName, String passWord, Role role) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getUserName().equals(userName)
                    && user.getPassWord().equals(passWord)
                    && user.getRole() == role) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User passwordRetrieval(String userName, String email, String phone, Role role) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getUserName().equals(userName)
                    && user.getEmail().equals(email)
                    && user.getPhone().equals(phone)
                    && user.getRole() == role) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(User newUser) {
        newUser.setCreatedAt(Instant.now());
        List<User> users = findAll();
        users.add(newUser);
        CSVUtils.write(DATA_USER_PATH, users);
    }

    @Override
    public void update(User newUser) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getId() == newUser.getId()) {
                String passWord = newUser.getPassWord();
                String fullName = newUser.getFullName();
                String email = newUser.getEmail();
                String phone = newUser.getPhone();
                String address = newUser.getAddress();
                Role role = newUser.getRole();
                user.setPassWord(passWord);
                user.setFullName(fullName);
                user.setEmail(email);
                user.setPhone(phone);
                user.setAddress(address);
                user.setRole(role);
                user.setUpdatedAt(Instant.now());
                CSVUtils.write(DATA_USER_PATH, users);
                break;
            }
        }
    }

    @Override
    public void deleteById(long id) {
        List<User> users = findAll();
        for (int i = 0; i < users.size(); i++) {
            if ((users.get(i)).getId() == id) {
                users.remove(users.get(i));
            }
        }
        CSVUtils.write(DATA_USER_PATH, users);
    }

    @Override
    public boolean existsByUserName(String userName) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getUserName().equals(userName))
                return true;
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getEmail().equals(email))
                return true;
        }
        return false;
    }

    @Override
    public boolean existsByPhone(String phone) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getPhone().equals(phone))
                return true;
        }
        return false;
    }

    @Override
    public User findById(long id) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    @Override
    public boolean existById(long id) {
        return findById(id) != null;
    }

    @Override
    public List<User> sortById(TypeSort type) {
        List<User> users = findAll();
        if (type == TypeSort.ASC) {
            users.sort((o1, o2) -> {
                double result = o1.getId() - o2.getId();
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            users.sort((o1, o2) -> {
                double result = o1.getId() - o2.getId();
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return users;
    }

    @Override
    public List<User> sortByUserName(TypeSort type) {
        List<User> users = findAll();
        if (type == TypeSort.ASC) {
            users.sort((o1, o2) -> {
                int result = o1.getUserName().compareTo(o2.getUserName());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            users.sort((o1, o2) -> {
                int result = o1.getUserName().compareTo(o2.getUserName());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return users;
    }

    @Override
    public List<User> sortByFullName(TypeSort type) {
        List<User> users = findAll();
        if (type == TypeSort.ASC) {
            users.sort((o1, o2) -> {
                int result = o1.getFullName().compareTo(o2.getFullName());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            users.sort((o1, o2) -> {
                int result = o1.getFullName().compareTo(o2.getFullName());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return users;
    }

    @Override
    public List<User> sortByEmail(TypeSort type) {
        List<User> users = findAll();
        if (type == TypeSort.ASC) {
            users.sort((o1, o2) -> {
                int result = o1.getEmail().compareTo(o2.getEmail());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            users.sort((o1, o2) -> {
                int result = o1.getEmail().compareTo(o2.getEmail());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return users;
    }

    @Override
    public List<User> sortByPhone(TypeSort type) {
        List<User> users = findAll();
        if (type == TypeSort.ASC) {
            users.sort((o1, o2) -> {
                int result = o1.getPhone().compareTo(o2.getPhone());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            users.sort((o1, o2) -> {
                int result = o1.getPhone().compareTo(o2.getPhone());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return users;
    }

    @Override
    public List<User> sortByRole(TypeSort type) {
        List<User> users = findAll();
        if (type == TypeSort.ASC) {
            users.sort((o1, o2) -> {
                int result = o1.getRole().compareTo(o2.getRole());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            users.sort((o1, o2) -> {
                int result = o1.getRole().compareTo(o2.getRole());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return users;
    }

    @Override
    public List<User> sortByAddress(TypeSort type) {
        List<User> users = findAll();
        if (type == TypeSort.ASC) {
            users.sort((o1, o2) -> {
                int result = o1.getAddress().compareTo(o2.getAddress());
                if (result == 0) return 0;
                return result > 0 ? 1 : -1;
            });
        }
        if (type == TypeSort.DESC) {
            users.sort((o1, o2) -> {
                int result = o1.getAddress().compareTo(o2.getAddress());
                if (result == 0) return 0;
                return result > 0 ? -1 : 1;
            });
        }
        return users;
    }

    @Override
    public List<User> findByUserName(String value) {
        List<User> users = findAll();
        List<User> usersFind = new ArrayList<>();
        for (User item : users) {
            if ((item.getUserName().toUpperCase()).contains(value.toUpperCase())) {
                usersFind.add(item);
            }
        }
        if (usersFind.isEmpty()) {
            return null;
        }
        return usersFind;
    }

    @Override
    public List<User> findByFullName(String value) {
        List<User> users = findAll();
        List<User> usersFind = new ArrayList<>();
        for (User item : users) {
            if ((item.getFullName().toUpperCase()).contains(value.toUpperCase())) {
                usersFind.add(item);
            }
        }
        if (usersFind.isEmpty()) {
            return null;
        }
        return usersFind;
    }

    @Override
    public List<User> findByEmail(String value) {
        List<User> users = findAll();
        List<User> usersFind = new ArrayList<>();
        for (User item : users) {
            if ((item.getEmail().toUpperCase()).contains(value.toUpperCase())) {
                usersFind.add(item);
            }
        }
        if (usersFind.isEmpty()) {
            return null;
        }
        return usersFind;
    }

    @Override
    public List<User> findByPhone(String value) {
        List<User> users = findAll();
        List<User> usersFind = new ArrayList<>();
        for (User item : users) {
            if ((item.getPhone().toUpperCase()).contains(value.toUpperCase())) {
                usersFind.add(item);
            }
        }
        if (usersFind.isEmpty()) {
            return null;
        }
        return usersFind;
    }

    @Override
    public List<User> findByAddress(String value) {
        List<User> users = findAll();
        List<User> usersFind = new ArrayList<>();
        for (User item : users) {
            if ((item.getAddress().toUpperCase()).contains(value.toUpperCase())) {
                usersFind.add(item);
            }
        }
        if (usersFind.isEmpty()) {
            return null;
        }
        return usersFind;
    }

    @Override
    public String findNameById(long id) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getId() == id) {
                return user.getFullName();
            }
        }
        return null;
    }
}