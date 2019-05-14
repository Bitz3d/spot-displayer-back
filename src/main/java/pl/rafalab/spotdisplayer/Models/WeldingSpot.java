package pl.rafalab.spotdisplayer.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "welding_spot")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeldingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "spot_name")
    private String spotName;

    @NotEmpty
    @Column(name = "model_name")
    private String modelName;

    @NotNull
    @Column(name = "point_x")
    private double pointX;

    @NotNull
    @Column(name = "point_y")
    private double pointY;

    @NotNull
    @Column(name = "point_z")
    private double pointZ;

    @ManyToOne()
    @JoinColumn(name = "my_user_id")
    private MyUser myUser;
}
