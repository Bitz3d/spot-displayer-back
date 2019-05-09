package pl.rafalab.spotdisplayer.Models;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "my_user")
public class MyUser {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotEmpty
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<MyRole> roles;


//    @OneToMany(
//            mappedBy = "form",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER
//    )
//    @Column(name = "welding_spots")
//    private Set<WeldingSot> weldingSots;

}



