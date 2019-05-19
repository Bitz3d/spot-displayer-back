package pl.rafalab.spotdisplayer.TesetUtils;

import pl.rafalab.spotdisplayer.Models.MyRole;

import java.util.HashSet;
import java.util.Set;

public class TestUtils {

    public static Set<MyRole> getUserRoles() {
        Set<MyRole> myRoles = new HashSet<>();
        myRoles.add(new MyRole(1, "ROLE_ADMIN"));
        myRoles.add(new MyRole(2, "ROLE_USER"));
        return myRoles;
    }
}
