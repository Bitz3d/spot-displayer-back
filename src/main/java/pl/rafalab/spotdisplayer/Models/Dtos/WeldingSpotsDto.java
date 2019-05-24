package pl.rafalab.spotdisplayer.Models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeldingSpotsDto {

    private String spotName;
    private String modelName;
    private double pointX;
    private double pointY;
    private double pointZ;

}
