package pl.rafalab.spotdisplayer.Commons;

public interface Mapper<F, T> {
    T map(F from);

    F reverseMap(T to);
}