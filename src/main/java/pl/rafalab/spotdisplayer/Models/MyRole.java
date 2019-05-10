package pl.rafalab.spotdisplayer.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class MyRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String role;

}
