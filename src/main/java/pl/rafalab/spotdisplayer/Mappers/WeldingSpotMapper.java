package pl.rafalab.spotdisplayer.Mappers;

import org.springframework.stereotype.Component;
import pl.rafalab.spotdisplayer.Commons.Mapper;
import pl.rafalab.spotdisplayer.Models.Dtos.WeldingSpotsDto;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;

@Component
public class WeldingSpotMapper implements Mapper<WeldingSpot, WeldingSpotsDto> {
    @Override
    public WeldingSpotsDto map(WeldingSpot from) {
        return WeldingSpotsDto
                .builder()
                .modelName(from.getModelName())
                .spotName(from.getSpotName())
                .pointX(from.getPointX())
                .pointY(from.getPointY())
                .pointZ(from.getPointZ())
                .build();
    }

    @Override
    public WeldingSpot reverseMap(WeldingSpotsDto to) {
        return WeldingSpot.builder()
                .spotName(to.getSpotName())
                .modelName(to.getModelName())
                .pointZ(to.getPointZ())
                .pointY(to.getPointY())
                .pointX(to.getPointX())
                .build();
    }
}
