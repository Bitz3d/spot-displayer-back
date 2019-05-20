package pl.rafalab.spotdisplayer.Utils;

public class Constants {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "rafal.lab";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";
    public static final String SEARCHING_LINES = "robtarget lsp";
    public static final String WELDING_SPOT_DATA_EXTRACTION_PATTERN = "(lsp\\d*)_(f\\d\\d):=((\\[{2})(-*\\d+(\\.\\d+)*)\\,(-*\\d+(\\.\\d+)*)\\,(-*\\d+(\\.\\d+)*))";
    public static final int MAX_FILE_SIZE = 5242880;
}
