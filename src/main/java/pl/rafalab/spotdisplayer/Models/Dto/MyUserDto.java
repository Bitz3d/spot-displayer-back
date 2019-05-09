package pl.rafalab.spotdisplayer.Models.Dto;

import lombok.Data;
import pl.rafalab.spotdisplayer.Models.MyRole;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class MyUserDto {

    @NotEmpty
    @Column(unique = true)
    private String username;
    @NotEmpty
    @Size(min = 5)
    private String password;

    private Set<MyRole> roles;
}
