package tn.esprit.affariety.utils;

import tn.esprit.affariety.models.User;

public class Session {

        private User user ;
        private static Session Instance ;

    private Session(User user) {
        this.user = user;
    }
    public static Session StartSession(User user) {
        if (Instance == null) {
            Instance = new Session(user);
        }
        return Instance;
    }
    public User getUser() {
        return user;
    }
    public static Session  getSession(){
        return Instance;
    }
    public static void clearSession(){

        Instance = null;

    }


}
