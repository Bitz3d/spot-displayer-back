package pl.rafalab.spotdisplayer.model.Dto;

import lombok.Data;
import pl.rafalab.spotdisplayer.model.MyRole;
import java.util.HashSet;
import java.util.Set;

@Data
public class MyUserDto {
    private String username;
    private String password;
    private Set<MyRole> roles;
}
