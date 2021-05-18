package it.polito.ezshop.data;
import static java.util.stream.Collectors.*;
import java.util.List;

public class LoginManager {
    
    private static LoginManager instance;

    private it.polito.ezshop.model.User loggedUser;

    private LoginManager() {
        loggedUser = null;
    }

    public static LoginManager getInstance() {
        
        if (instance == null) {
            instance = new LoginManager();
        }

        return instance;
    }

    public boolean isUserLogged() {
        return loggedUser != null;
    }

    public boolean isUserLogged(it.polito.ezshop.model.User user) {
        return user != null && isUserLogged() && loggedUser.equals(user);
    }

    public it.polito.ezshop.model.User getLoggedUser() {
        return loggedUser;
    }

    public boolean tryLogin(String username, String password) {
        
        List<it.polito.ezshop.model.User> requestedUser = DataManager.getInstance()
            .getUsers()
            .stream()
            .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
            .collect(toList());

        if (requestedUser.size() != 1) return false;

        loggedUser = requestedUser.get(0);
        return true;
    }
    
    public boolean tryLogout() {

        if (loggedUser == null) return false;

        loggedUser = null;
        return true;
    }

}
